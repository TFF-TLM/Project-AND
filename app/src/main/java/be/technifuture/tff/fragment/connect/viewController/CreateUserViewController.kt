package be.technifuture.tff.fragment.connect.viewController

import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.AlertDialogCustom.ErrorValidation
import be.technifuture.tff.service.NetworkService


class CreateUserController(private val viewBinding: FragmentCreateUserBinding,
                           private val alert: AlertDialogCustom
) {

    private var login: String = ""
    private var password: String = ""
    private var email: String = ""

    fun validateForm(): NewUserModel? =
        if(loginIsValid() && isEmailValid() && isPasswordValid())

            NewUserModel(login, email, password, 0, "")
        else
            null

    private fun loginIsValid(): Boolean {
        login = viewBinding.editTextLogin.text.toString()

        // Must start by letter, Only Number and Letter for other
        // Max : 20 charactére
        val regex = "^(?=.*[A-Za-z0-9]\$)[A-Za-z][A-Za-z\\d.-]{4,19}\$".toRegex()

        if (!regex.matches(login)) {
            alert.getAlert(ErrorValidation.LOGIN_RULES)
            // Message d'erreur utilisateur
            return false
        }

        if (!NetworkService.user.isLoginAvailable(login)) {
            alert.getAlert(ErrorValidation.LOGIN_EXIST)
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        password = viewBinding.editTextPassword.text.toString()
        val password2 = viewBinding.editTextVerifPassword.text.toString()

        if (password != password2) {
            alert.getAlert(ErrorValidation.PASSWORD_SAME)
            // Message d'erreur utilisateur
            return false
        }
        //Minimum eight characters, at least one uppercase letter,
        // one lowercase letter, one number and one special character, Minimum 8 charactere
        val regex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()

        if (!regex.matches(password)) {
            alert.getAlert(ErrorValidation.PASSWORD_RULES)
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        email = viewBinding.editTextMail.text.toString()

        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

        if (!regex.matches(email)) {
            alert.getAlert(ErrorValidation.MAIL_RULES)
            // Message d'erreur utilisateur
            return false
        }

        if (!NetworkService.user.isEmailAvailable(email)) {
            alert.getAlert(ErrorValidation.MAIL_EXIST)
            // Message d'erreur utilisateur
            return false
        }
        return true

    }



}
