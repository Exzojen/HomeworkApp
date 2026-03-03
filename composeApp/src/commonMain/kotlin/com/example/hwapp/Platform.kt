package com.example.hwapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform