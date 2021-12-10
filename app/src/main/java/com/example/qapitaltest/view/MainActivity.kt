package com.example.qapitaltest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.qapitaltest.R
import com.example.qapitaltest.adapter.AdapterActivity
import com.example.qapitaltest.entity.ActivitiesResponse
import com.example.qapitaltest.databinding.ActivityMainBinding
import com.example.qapitaltest.entity.UserResponse
import com.example.qapitaltest.helper.cancelDialog
import com.example.qapitaltest.helper.showLoader
import com.example.qapitaltest.presenter.MainViewInterface
import com.example.qapitaltest.presenter.MainPresenter
import org.joda.time.DateTime

import org.joda.time.DateTimeZone


class MainActivity : AppCompatActivity(), MainViewInterface {

    private lateinit var binding: ActivityMainBinding
    private var mainPresenter: MainPresenter? = null

    private var currentDate = DateTime.now()
    lateinit var previousDate: DateTime

    private lateinit var adapter: AdapterActivity
    private var isLimitReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupUI()
    }

    private fun getPreviousDates(dateTime: DateTime, numOfWeeks: Int): DateTime {
        return dateTime.minusWeeks(numOfWeeks)
    }

    fun setupUI() {
        mainPresenter = MainPresenter(this)
        mainPresenter?.getUsers()
        setAdapter()
    }


    private fun setAdapter() {
        adapter = AdapterActivity()
        binding.rvActivities.layoutManager = LinearLayoutManager(this)
        binding.rvActivities.adapter = adapter

        binding.rvActivities.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    //function that add new elements to my recycler view
                    if (!isLimitReached) {
                        currentDate = previousDate
                        Log.d("TAG", "onCreate: from " + formatDate(getPreviousDates(previousDate, 2)))
                        Log.d("TAG", "onCreate: to " + formatDate(currentDate))
                        previousDate = getPreviousDates(previousDate, 2)
                        mainPresenter?.getActivities(
                            formatDate(getPreviousDates(previousDate, 2)),
                            formatDate(currentDate), false
                        )

                    }
                }
            }

        })
    }

    override fun showProgressLoader() {
        showLoader()
    }

    override fun displayActivities(activitiesResponse: ActivitiesResponse) {
        if (activitiesResponse.activities.isNotEmpty()) {
            cancelDialog()
            adapter.updateList(activitiesResponse.activities)
        } else {
            if (currentDate < DateTime.parse(activitiesResponse.oldest)) {
                Log.d("TAG", "onCreate: from " + formatDate(getPreviousDates(currentDate, 2)))
                Log.d("TAG", "onCreate: to " + formatDate(currentDate))
                previousDate = getPreviousDates(previousDate, 2)
                mainPresenter?.getActivities(
                    formatDate(getPreviousDates(previousDate, 2)),
                    formatDate(currentDate), false
                )
            } else {
                isLimitReached = true
                cancelDialog()
                Toast.makeText(this, "No more records!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getUser(userResponse: UserResponse) {
        cancelDialog()
        Toast.makeText(this, userResponse.displayName, Toast.LENGTH_SHORT).show()
    }

    override fun getUsers() {
        cancelDialog()

        previousDate = getPreviousDates(currentDate, 2)

        Log.d("TAG", "onCreate: from " + formatDate(getPreviousDates(currentDate, 2)))
        Log.d("TAG", "onCreate: to " + formatDate(currentDate))

        mainPresenter?.getActivities(formatDate(previousDate), formatDate(currentDate), true)

    }

    fun formatDate(time: DateTime): String {
        return time.toString("yyyy-MM-dd'T'HH:mm:ss+00:00")
    }


    override fun displayError(s: String?) {
        cancelDialog()
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


}