package com.tanamo.chatapp.adaptors


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tanamo.chatapp.ui.Flash
import java.net.HttpURLConnection
import java.net.URL


class FireM : FirebaseMessagingService() {

    var bt: Bitmap? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {


        if (remoteMessage!!.data.size > 0) {

        }

        if (remoteMessage.notification != null) {


        }
        val message = remoteMessage.data["message"]

        val imageUri = remoteMessage.data["image"]

        val act = remoteMessage.data["Activity"]

        bt = getBitmap(imageUri!!)


        sendNotification(message!!, bt!!, act!!)


    }

    private fun sendNotification(messageBody: String, image: Bitmap, str: String) {
        val intent = Intent(this, Flash::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("Activity", str)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val ur = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notificationBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(image)
                .setContentTitle(messageBody)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
                .setAutoCancel(true)
                .setLights(Color.BLUE, 1, 1)
                .setSound(ur)
                .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        notificationManager.notify(0, notificationBuilder.build())
    }

    fun getBitmap(imageUrl: String): Bitmap? {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bt = BitmapFactory.decodeStream(input)
            return bt

        } catch (e: Exception) {
            e.printStackTrace()
            return null

        }

    }


}


