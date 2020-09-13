package com.tomislav.novacic.weathernovacic.utils

import android.app.AlertDialog
import android.content.Context
import com.tomislav.novacic.weathernovacic.R


class DialogHelper {

    fun showYesNoDialog(
        context: Context,
        title: String,
        msg: String,
        positiveBtnText: String,
        positiveBtnRunnable: Runnable?,
        negativeBtnText: String,
        negativeBtnRunnable: Runnable?
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
        if (positiveBtnRunnable != null) {
            dialog.setPositiveButton(positiveBtnText) { _, _ ->
                positiveBtnRunnable.run()
            }
        }
        if (negativeBtnRunnable != null) {
            dialog.setNegativeButton(negativeBtnText) { _, _ ->
                negativeBtnRunnable.run()
            }
        }
        dialog.show()
    }

    fun showConfirmDialog(
        context: Context,
        title: String,
        msg: String,
        positiveBtnRunnable: Runnable?,
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
        if (positiveBtnRunnable != null) {
            dialog.setPositiveButton(context.getString(R.string.dialog_confirm_btn)) { _, _ ->
                positiveBtnRunnable.run()
            }
        }
        dialog.show()
    }
}