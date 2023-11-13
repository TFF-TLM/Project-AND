package be.technifuture.tff.service

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import be.technifuture.tff.R

class AlertDialogCustom (private val viewContext: Context){

    enum class ErrorValidation {
        LOGIN_RULES,
        LOGIN_EXIST,
        PASSWORD_RULES,
        PASSWORD_SAME,
        MAIL_RULES,
        MAIL_EXIST,
        LOG_ERROR
    }

    fun getAlert(error: ErrorValidation){

        //val binding = AlertDialogueCustomBinding.inflate(LayoutInflater.from(viewContext))

        val builder = androidx.appcompat.app.AlertDialog.Builder(viewContext, R.style.AlertDialogCustom)
            .create()
        val view = LayoutInflater.from(viewContext).inflate(R.layout.alert_dialogue_custom,null)
        val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
        builder.setView(view)

        button.setOnClickListener {
            builder.dismiss()
        }

        view.findViewById<TextView>(R.id.title).text = "Error"
        view.findViewById<TextView>(R.id.desc).text = when(error){
            ErrorValidation.LOGIN_EXIST ->
                view.resources.getString(R.string.LOGIN_EXIST)
            ErrorValidation.LOGIN_RULES ->
                view.resources.getString(R.string.LOGIN_RULES)
            ErrorValidation.PASSWORD_RULES ->
                view.resources.getString(R.string.PASSWORD_RULES)
            ErrorValidation.PASSWORD_SAME ->
                view.resources.getString(R.string.PASSWORD_SAME)
            ErrorValidation.MAIL_EXIST ->
                view.resources.getString(R.string.MAIL_EXIST)
            ErrorValidation.MAIL_RULES ->
                view.resources.getString(R.string.MAIL_RULES)
            ErrorValidation.LOG_ERROR ->
                view.resources.getString(R.string.LOG_ERROR)
        }




        builder.setCanceledOnTouchOutside(false)
        builder.show()



       /* val alert = AlertDialog.Builder(viewContext)
        alert.setTitle("Error")
        alert.setMessage(
            when(error){
                ErrorValidation.LOGIN_EXIST ->
                    R.string.LOGIN_EXIST
                ErrorValidation.LOGIN_RULES ->
                    R.string.LOGIN_RULES
                ErrorValidation.PASSWORD_RULES ->
                    R.string.PASSWORD_RULES
                ErrorValidation.PASSWORD_SAME ->
                    R.string.PASSWORD_SAME
                ErrorValidation.MAIL_EXIST ->
                    R.string.MAIL_EXIST
                ErrorValidation.MAIL_RULES ->
                    R.string.MAIL_RULES
                ErrorValidation.LOG_ERROR ->
                    R.string.LOG_ERROR
            })
        alert.setNeutralButton("Ok", null)
        alert.create()
        alert.show()
        */
    }

}