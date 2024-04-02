package com.example.testworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat

class MyWorker(cxt: Context, params: WorkerParameters) : Worker(cxt, params) {
    val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    // カテゴリー名（通知設定画面に表示される情報）
    val name = "通知のタイトル的情報を設定"
    // システムに登録するChannelのID
    val id = "casareal_chanel"
    // 通知の詳細情報（通知設定画面に表示される情報）
    val notifyDescription = "この通知の詳細情報を設定します"

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    companion object {
        var nid = 1;
    }


    init {
        // Channelの取得と生成
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.getNotificationChannel(id) == null
            val mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            mChannel.apply {
                description = notifyDescription
            }
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun doWork(): Result {


        val notification = NotificationCompat.Builder(applicationContext, id).apply {

            setContentText("${nid}回目のメッセージ:${simpleDateFormat.format(System.currentTimeMillis())}")
            setSmallIcon(R.drawable.ic_launcher_background)
        }

        notificationManager.notify(MyWorker.nid, notification.build())

        MyWorker.nid++

        return Result.success()
    }
}
