package dev.fakedata.ui.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import dev.fakedata.App
import dev.fakedata.App.Companion.context
import dev.fakedata.R
import dev.fakedata.ui.main.MainActivity


/**
 * Provides utility functions to display various kinds of messages: Snackbar, AlertDialog, Statusbar notifications, etc.
 */
class Notifications {

    val CHANNEL_ID_FAKE_DATA = "channelIdFakeData"
    val CHANNEL_NAME_FAKE_DATA = App.context.getString(R.string.notification_category_name_fake_data)


    // Notification IDs
    val NOTIFICATION_ID_LINKEDIN_SERVICE = 565656

    fun displayErrorMessage(resId: Int) {
        if (App.context.currentActivity == null) {
            showNotification(
                R.string.application_error,
                resId,
                R.drawable.ic_warning,
                CHANNEL_ID_FAKE_DATA,
                CHANNEL_NAME_FAKE_DATA,
                (0..0x7fffffff).random()
            )
        } else {
            showErrorSnackbar(resId)
        }
    }

    /**
     * Displays a message, either in an AlertDialog, if an activity is showning, or as a notification if no activity is showing.
     */
    fun displayMessage(resId: Int) {
        if (App.context.currentActivity == null) {
            createNotification(
                R.string.information,
                resId,
                R.drawable.ic_info,
                CHANNEL_ID_FAKE_DATA,
                CHANNEL_NAME_FAKE_DATA
            )
        } else {
            displayModalDialog(resId)
        }
    }

    fun displayModalDialog(resId: Int) {
        displayModalDialog(context.getString(resId))
    }

    fun displayModalDialog(message: String) {
        if (App.context.currentActivity == null) {
            createNotification(
                R.string.information,
                message,
                R.drawable.ic_info,
                CHANNEL_ID_FAKE_DATA,
                CHANNEL_NAME_FAKE_DATA
            )
        } else {
            val context = App.context.currentActivity as Context

            var alertDialog: AlertDialog? = null
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(App.context.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
                .setTitle(context.getString(R.string.app_name))

            alertDialog = dialogBuilder.show()
            alertDialog.setCancelable(false)
        }
    }


    /**
     * Displays a snackbar with normal text. Use showErrorSnackbar if you want to show an error message instead.
     * @param message The message to display
     * @param duration Set to Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_LONG or Snackbar.LENGTH_SHORT
     */
    fun showInfoSnackbar(resId: Int, duration: Int) {
        val view = App.context.currentActivityRootView!!
        showSnackbar(resId, duration, false, view)
    }

    fun showErrorSnackbarForDuration(resId: Int, duration: Int, view: View) {
        showSnackbar(resId, duration, true, view)
    }

    fun showErrorSnackbar(resId: Int) {
        val view = App.context.currentActivityRootView!!
        showSnackbar(resId, Snackbar.LENGTH_INDEFINITE, true, view)
    }

    fun showErrorSnackbar(message: String) {
        val view = App.context.currentActivityRootView!!
        displaySnackbar(message, Snackbar.LENGTH_INDEFINITE, true, view)
    }

    fun showErrorSnackbar(resId: Int, view: View) {
        showSnackbar(resId, Snackbar.LENGTH_INDEFINITE, true, view)
    }

    private fun showSnackbar(resId: Int, duration: Int, isErrorMessage: Boolean, view: View?) {

        if (view == null)
            return

        displaySnackbar(App.context.getString(resId), duration, isErrorMessage, view)

    }

    fun displaySnackbar(message: String, duration: Int, isErrorMessage: Boolean, view: View?) {
        val snackbar = Snackbar.make(view!!, message, duration)
        snackbar.view.setBackgroundColor(App.context.resources.getColor(R.color.snackbar_background))

        if (duration == Snackbar.LENGTH_INDEFINITE) {
            snackbar.setAction(App.context.getString(R.string.cancel), View.OnClickListener {
                var x = 0
                x++
            })
        }

        val snackbarView = snackbar.view

        if (isErrorMessage) {
            val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
            textView.setTextColor(App.context.resources.getColor(R.color.snackbar_error_text))
        }

        snackbar.show()
    }

    fun createNotification(titleResId: Int, message: String, iconResId: Int, channeld: String, channelName: String): Notification {
        return makeNotification(titleResId, message, iconResId, channeld, channelName)
    }

    fun createNotification(titleResId: Int, textResId: Int, iconResId: Int, channeld: String, channelName: String): Notification {
        return makeNotification(titleResId, App.context.getString(textResId), iconResId, channeld, channelName)
    }


    fun makeNotification(titleResId: Int, message: String, iconResId: Int, channeld: String, channelName: String): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(channeld, channelName)
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val pendingIntent: PendingIntent = Intent(App.context, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(App.context, 0, notificationIntent, 0)
        }

        val notification: Notification = NotificationCompat.Builder(App.context, channelId)
            .setContentTitle(App.context.getString(titleResId))
            .setContentText(message)
            .setSmallIcon(iconResId)
            .setContentIntent(pendingIntent)
            .build()

        return notification
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = App.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


    fun showNotification(titleResId: Int, textResId: Int, iconResId: Int, channeld: String, channelName: String, notificationId: Int) {
        with(NotificationManagerCompat.from(App.context)) {
            val notification = createNotification(titleResId, textResId, iconResId, channeld, channelName)
            notify(notificationId, notification)
        }
    }

    fun showToast(text: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(App.context, text, Toast.LENGTH_LONG).show() })
    }
}