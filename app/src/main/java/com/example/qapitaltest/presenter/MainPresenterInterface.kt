package com.example.qapitaltest.presenter

interface MainPresenterInterface {

    fun getActivities(from: String, to: String, isFirstTime: Boolean)

    fun getUser(id : Int)

    fun getUsers()

}
