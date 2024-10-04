package `in`.globalspace.`in`.mdmsuiteapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val workRequest = OneTimeWorkRequestBuilder<PackageCheckBackgroundWorker>()
            .build()
        context?.let { WorkManager.getInstance(it).enqueue(workRequest) }
        context?.let { setupAlarm(it) }

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setupAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
                context, 0, Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10 * 1000L, // Next 10-second interval
                pendingIntent
        )
    }
}