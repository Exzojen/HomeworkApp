package com.example.hwapp

import android.os.Build

import android.os.Process
import android.util.Log

actual fun exitApplication() {
    Log.d("ExitApp", "Приложение завершается через killProcess")
    Process.killProcess(Process.myPid())
}