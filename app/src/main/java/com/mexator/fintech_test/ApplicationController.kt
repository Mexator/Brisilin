package com.mexator.fintech_test

import android.app.Application
import com.mexator.fintech_test.data.Repository

class ApplicationController : Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.injectAppContext(this)
    }
}