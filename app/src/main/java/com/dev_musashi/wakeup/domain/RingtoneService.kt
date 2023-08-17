package com.dev_musashi.wakeup.domain

interface RingtoneService {

    fun getRingtoneList() : List<Pair<String,String>>

    fun ringSelectedRingtone(uri: String)

    fun onDestroy()

}