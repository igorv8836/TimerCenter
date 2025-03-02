package org.example.timercenter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform