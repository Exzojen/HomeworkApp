package com.example.hwapp

import platform.UIKit.UIDevice

actual fun exitApplication() {
    platform.posix.exit(0)
}