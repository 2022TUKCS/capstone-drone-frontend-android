package com.example.firedron.Service

import android.content.Intent
import android.util.Log
import com.example.firedron.Activity.Notification
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FMS: FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            val intent = Intent(this, Notification::class.java)
            intent.putExtra("lat", message.data["lat"])
            intent.putExtra("lng", message.data["lng"])
            intent.putExtra("time", message.data["time"])
            intent.putExtra("type", message.data["type"])
            intent.putExtra("token", message.data["token"])
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun sendRegistrationToServer(token: String) {
        // TODO: localhost:5000/
    }
}