package com.jay.mywatchlist.util

import okhttp3.Dns
import org.xbill.DNS.Lookup
import org.xbill.DNS.SimpleResolver
import org.xbill.DNS.Type
import java.net.InetAddress
import java.net.UnknownHostException

class GoogleDns : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return try {
            val resolver = SimpleResolver("8.8.8.8") // Force Google's DNS
            val lookup = Lookup(hostname, Type.A) // A-record lookup
            lookup.setResolver(resolver)
            lookup.run()
            lookup.answers?.map { InetAddress.getByAddress(it.rdataToWireCanonical()) } ?: throw UnknownHostException("No IP found")
        } catch (e: Exception) {
            throw UnknownHostException("Failed to resolve $hostname using Google DNS")
        }
    }
}


