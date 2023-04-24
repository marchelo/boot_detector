package de.innomos.myapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import de.innomos.myapplication.data.BootRecordRepo
import de.innomos.myapplication.data.MyDatabase
import de.innomos.myapplication.data.entities.BootRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootCompleteReceiver : BroadcastReceiver() {

    private val notificationChannelId = "boot-notification-channel"
    private val notificationId = 2234

    private val receiverScope = CoroutineScope(Dispatchers.Main)

    override fun onReceive(context: Context, intent: Intent) {
        val repo = BootRecordRepo(MyDatabase.getDatabase(context).bootRecordDao())
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                receiverScope.launch {
                    val currentTimestamp = System.currentTimeMillis()
                    repo.insert(BootRecord(currentTimestamp))

                    val records = repo.getAll()
                    val text = notificationMessage(records)
                    showNotification(context, text)
                }
            }
        }
    }

    private fun showNotification(context: Context, text: String) {
        val mNotifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            @SuppressLint("WrongConstant")
            val notificationChannel = NotificationChannel(notificationChannelId, context.getString(R.string.app_name), importance)

            mNotifyManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setChannelId(notificationChannelId)
            .setContentText(text)
            .build()

        mNotifyManager.notify(notificationId, notification)
    }

    private fun notificationMessage(records: List<BootRecord>): String {
        return if (records.isEmpty()) {
            "No boots detected"
        } else if (records.size == 1) {
            val timestamp = records.first().timestamp
            "The boot was detected with the timestamp = $timestamp"
        } else {
            val timestampDiff = records[0].timestamp - records[1].timestamp
            "“Last boots time delta = $timestampDiff"
        }
    }
}