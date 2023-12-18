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
        LOG_ERROR,
        NO_CONNECTION,
        RETRIEVE_SEND,
        CANT_DROP_CAT,
        NAME_CAT_EMPTY,
        CANT_FEED,
        NO_MORE_FOOD,
        CANT_LEVEL_UP,
        ALREADY_INTERACT
    }

    fun getAlert(error: ErrorValidation){

        val builder = androidx.appcompat.app.AlertDialog.Builder(viewContext, R.style.AlertDialogCustom)
            .create()
        val view = LayoutInflater.from(viewContext).inflate(R.layout.alert_dialogue_custom,null)
        val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
        builder.setView(view)

        button.setOnClickListener {
            builder.dismiss()
        }

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
            ErrorValidation.NO_CONNECTION ->
                view.resources.getString(R.string.NO_CONNECTION)
            ErrorValidation.RETRIEVE_SEND ->
                view.resources.getString(R.string.RETRIEVE_SEND)
            ErrorValidation.CANT_DROP_CAT -> "Le chat ne peut être posé ici."
            ErrorValidation.NAME_CAT_EMPTY -> "Le nom ne peut pas être vide."
            ErrorValidation.CANT_FEED -> "Vous ne pouvez pas nourrir plus ce chat."
            ErrorValidation.NO_MORE_FOOD-> "Vous n'avez plus de croquettes."
            ErrorValidation.CANT_LEVEL_UP-> "Ce chat ne peut pas monter de niveau actuellement."
            ErrorValidation.ALREADY_INTERACT-> "Vous avez déjà récupéré les récompenses."
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}