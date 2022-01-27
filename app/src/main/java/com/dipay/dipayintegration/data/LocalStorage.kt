package com.example.dipayintegration.data

import com.orhanobut.hawk.Hawk

object LocalStorage {
    const val CLIENT_ID = "CLIENT_ID"
    const val BASE_URL = "BASE_URL"
    fun setClientId(clientId: String) {
        Hawk.put(CLIENT_ID, clientId)
    }

    fun getClientId(): String? {
        return Hawk.get(CLIENT_ID)
    }

    var baseUrl: String
        get() = Hawk.get(BASE_URL, "http://192.168.88.159:3001")
        set(value) {
            Hawk.put(BASE_URL, value)
        }
}