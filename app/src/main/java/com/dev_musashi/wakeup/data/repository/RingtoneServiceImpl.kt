package com.dev_musashi.wakeup.data.repository

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.core.net.toUri
import com.dev_musashi.wakeup.domain.RingtoneService
import javax.inject.Inject

class RingtoneServiceImpl @Inject constructor(
    context: Context
) : RingtoneService {
    private var ringtone: Ringtone? = null
    private val _context = context

    override fun getRingtoneList(): List<Pair<String,String>> {
        val list = mutableListOf<Pair<String,String>>()
        RingtoneManager(_context).cursor.run {
            while (moveToNext()) {
                val title = getString(RingtoneManager.TITLE_COLUMN_INDEX)
                val uri =
                    getString(RingtoneManager.URI_COLUMN_INDEX) +
                            "/" +
                            getString(RingtoneManager.ID_COLUMN_INDEX)
                list.add(Pair(title , uri))
            }
        }
        return list
    }

    override fun ringSelectedRingtone(uri: String) {
        ringtone = RingtoneManager.getRingtone(_context,uri.toUri())
        ringtone?.play()

    }

    override fun onDestroy() {
        ringtone?.stop()
    }

}