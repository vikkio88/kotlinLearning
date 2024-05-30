package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

class Collections2 : Exercise {
    override val title: String
        get() = "Collections 2 - Set"

    /**
     * You have a set of protocols supported by your server.
     * A user requests to use a particular protocol.
     * Complete the program to check whether the requested protocol is supported or not (isSupported must be a Boolean value).
     */
    override fun body() {
        val SUPPORTED = setOf("HTTP", "HTTPS", "FTP")
        var requested = "smtp"
        var isSupported = requested.uppercase() in SUPPORTED
        println("Support for $requested: $isSupported")

        requested = "ftp"
        isSupported = requested.uppercase() in SUPPORTED
        println("Support for $requested: $isSupported")
    }
}