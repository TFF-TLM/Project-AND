package be.technifuture.tff.utils.alert

import android.app.Activity
import android.app.AlertDialog
import android.widget.EditText
import android.widget.NumberPicker
import be.technifuture.tff.R

object AlertBuilder {
    fun inputAlert(
        activity: Activity,
        title: String,
        message: String,
        handler: (result: String) -> Unit
    ) {
        val input = EditText(activity)
        AlertDialog.Builder(activity, R.style.AlertDialogCustom)
            .setTitle(title)
            .setMessage(message)
            .setView(input)
            .setPositiveButton("OK") { dialog, which ->
                handler(input.text.toString())
            }
            .setNegativeButton("Cancel") { dialog, which ->

            }
            .create()
            .show()
    }

    fun pickerNumberAlert(
        activity: Activity,
        title: String,
        message: String,
        min: Int,
        max: Int,
        handler: (result: Int) -> Unit
    ) {
        val input = NumberPicker(activity)
        input.minValue = min
        input.maxValue = max
        AlertDialog.Builder(activity, R.style.AlertDialogCustom)
            .setTitle(title)
            .setMessage(message)
            .setView(input)
            .setPositiveButton("OK") { dialog, which ->
                handler(input.value)
            }
            .setNegativeButton("Cancel") { dialog, which ->

            }
            .create()
            .show()
    }

    fun messageAlert(
        activity: Activity,
        title: String,
        message: String
    ) {
        AlertDialog.Builder(activity, R.style.AlertDialogCustom)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
