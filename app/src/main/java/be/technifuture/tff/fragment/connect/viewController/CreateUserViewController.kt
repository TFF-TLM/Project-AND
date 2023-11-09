package be.technifuture.tff.fragment.connect.viewController

import android.app.AlertDialog
import android.content.Context
import android.widget.Switch
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.service.NetworkService


class CreateUserController(private val viewBinding: FragmentCreateUserBinding,
                           private val viewContext: Context
) {

    private var login: String = ""
    private var password: String = ""
    private var email: String = ""

    enum class ErrorValidation {
        LOGIN_RULES,
        LOGIN_EXIST,
        PASSWORD_RULES,
        PASSWORD_SAME,
        MAIL_RULES,
        MAIL_EXIST,
        LOG_ERROR
    }

    fun validateForm(): Boolean =
        loginIsValid() && isEmailValid() && isPasswordValid()

    private fun loginIsValid(): Boolean {
        login = viewBinding.editTextLogin.text.toString()

        // Must start by letter, Only Number and Letter for other
        // Max : 20 charactére
        val regex = "^(?=.*[A-Za-z0-9]\$)[A-Za-z][A-Za-z\\d.-]{0,19}\$".toRegex()

        if (!regex.matches(login)) {
            getAlert(ErrorValidation.LOGIN_RULES)
            // Message d'erreur utilisateur
            return false
        }

        if (!NetworkService.user.isLoginAvailable(login)) {
            getAlert(ErrorValidation.LOGIN_EXIST)
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        password = viewBinding.editTextPassword.text.toString()
        val password2 = viewBinding.editTextVerifPassword.text.toString()

        if (password != password2) {
            getAlert(ErrorValidation.PASSWORD_SAME)
            // Message d'erreur utilisateur
            return false
        }
        //Minimum eight characters, at least one uppercase letter,
        // one lowercase letter, one number and one special character, Minimum 8 charactere
        val regex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()

        if (!regex.matches(password)) {
            getAlert(ErrorValidation.PASSWORD_RULES)
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        email = viewBinding.editTextMail.text.toString()

        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

        if (!regex.matches(email)) {
            getAlert(ErrorValidation.MAIL_RULES)
            // Message d'erreur utilisateur
            return false
        }

        if (!NetworkService.user.isEmailAvailable(email)) {
            getAlert(ErrorValidation.MAIL_EXIST)
            // Message d'erreur utilisateur
            return false
        }
        return true

    }

    private fun getAlert(error: ErrorValidation){

        val alert = AlertDialog.Builder(viewContext)
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
        alert.show()
    }

}
