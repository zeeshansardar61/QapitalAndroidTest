package com.example.qapitaltest.presenter

import android.util.Log
import com.example.qapitaltest.entity.ActivitiesResponse
import com.example.qapitaltest.entity.User
import com.example.qapitaltest.entity.UserResponse
import com.example.qapitaltest.network.ApiInterface
import io.reactivex.observers.DisposableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.example.qapitaltest.network.NetworkClient
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.annotations.NonNull


class MainPresenter(val mvi: MainViewInterface) : MainPresenterInterface {

    private val TAG = "MainPresenter"
    private val networkClient= NetworkClient().getRetrofitMethod().create(ApiInterface::class.java)
    val list = ArrayList<User>()


    override fun getActivities(from: String, to: String, isFirstTime: Boolean) {
        if(isFirstTime)
            mvi.showProgressLoader()
        getActivitiesObservable(from, to).subscribeWith(getActivityObserver())
    }

    override fun getUser(userId: Int) {
        mvi.showProgressLoader()
        getUserObservable(userId).subscribeWith(getUserObserver())
    }

    override fun getUsers() {
        mvi.showProgressLoader()
        getUsersObservable().subscribeWith(getUsersObserver())
    }

    private fun getActivitiesObservable(from : String, to: String): Observable<ActivitiesResponse> {
        return networkClient.getActivities(from, to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getUserObservable(userId: Int): Observable<UserResponse> {
        return networkClient.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getUsersObservable(): Observable<ArrayList<UserResponse>> {
        return networkClient.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getActivityObserver(): DisposableObserver<ActivitiesResponse> {
        return object : DisposableObserver<ActivitiesResponse>() {
            override fun onNext(@NonNull activitiesResponse: ActivitiesResponse) {
                Log.d(TAG, "OnNext" + activitiesResponse.activities)


                for(i in activitiesResponse.activities.indices){
                    for(j in list.indices){
                        if(activitiesResponse.activities[i].userId==list[j].userId){
                            activitiesResponse.activities[i].avatarUrl = list[j].avatarUrl
                        }
                    }
                }

                mvi.displayActivities(activitiesResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                mvi.displayError("Error fetching data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }

    private fun getUserObserver(): DisposableObserver<UserResponse> {
        return object : DisposableObserver<UserResponse>() {
            override fun onNext(@NonNull usersResponse: UserResponse) {
                Log.d(TAG, "OnNext" + usersResponse)
                mvi.getUser(usersResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                mvi.displayError("Error fetching data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }

    private fun getUsersObserver(): DisposableObserver<ArrayList<UserResponse>> {
        return object : DisposableObserver<ArrayList<UserResponse>>() {
            override fun onNext(@NonNull mList: ArrayList<UserResponse>) {
                list.clear()
                for (i in mList.indices){
                    list.add(User(mList[i].userId, mList[i].displayName, mList[i].avatarUrl))
                }
                mvi.getUsers()
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                mvi.displayError("Error fetching data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    }
}