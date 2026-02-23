package com.example.hwapp

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MyApp : Application (){
    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
    }
}