package com.example.myapplication.data

import com.orhanobut.hawk.Hawk

object LocalStorage {
    const val CLIENT_ID = "CLIENT_ID"
    fun setClientId(clientId: String){
        Hawk.put(CLIENT_ID, clientId)
    }

    fun getClientId(): String? {
        return Hawk.get(CLIENT_ID)
    }
}