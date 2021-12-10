package com.example.qapitaltest

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class ActivityApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }


}