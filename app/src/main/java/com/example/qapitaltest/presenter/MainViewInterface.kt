package com.example.qapitaltest.presenter

import com.example.qapitaltest.entity.ActivitiesResponse
import com.example.qapitaltest.entity.UserResponse

interface MainViewInterface {

    fun showProgressLoader()
    fun displayActivities(activitiesResponse: ActivitiesResponse)
    fun getUser(userResponse: UserResponse)
    fun getUsers()
    fun displayError(s: String?)
}