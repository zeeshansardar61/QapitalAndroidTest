package com.example.qapitaltest.entity

import java.util.ArrayList

class ActivitiesResponse {

    val oldest: String = ""
    val activities = ArrayList<ActivityModel>()

    class ActivityModel {
        val message = ""
        val amount = 0.0
        val userId = 0
        val timestamp = ""
        var avatarUrl = ""
    }

}