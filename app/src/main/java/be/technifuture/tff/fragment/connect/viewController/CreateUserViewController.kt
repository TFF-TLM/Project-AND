package be.technifuture.tff.fragment.connect.viewController

import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.NetworkService


class CreateUserController(private val viewBinding: FragmentCreateUserBinding) {

    private var login: String = ""
    private var password: String = ""
    private var email: String = ""

    enum class ErrorValidation {
        LOGIN_RULES,
        LOGIN_EXIST,
        PASSWORD_RULES,
        PASSWORD_SAME,
        MAIL_RULES,
        MAIL_EXIST
    }

    fun validateForm(){
        if(loginIsValid() && isEmailValid() && isPasswordValid()){
            val user = UserModel(login, email, password, "")
        }
    }

    private fun loginIsValid(): Boolean {
        login = viewBinding.editTextLogin.text.toString()

        // Must start by letter, Only Number and Letter for other
        // Max : 20 charactére
        val regex = "^(?=.*[A-Za-z0-9]\$)[A-Za-z][A-Za-z\\d.-]{0,19}\$".toRegex()

        if (!regex.matches(login)) {
            // Message d'erreur utilisateur
            return false
        }

        if (NetworkService.user.isLoginAvailable(login)) {
            // Message d'erreur utilisateur
            return false
        }
        return true
    }

    private fun isPasswordValid(): Boolean {
        password = viewBinding.editTextPassword.text.toString()
        val password2 = viewBinding.editTextVerifPassword.text.toString()

        if (password != password2) {
            // Message d'erreur utilisateur
            return false
        }
        //Minimum eight characters, at least one uppercase letter,
        // one lowercase letter, one number and one special character:
        val regex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()

        if (!regex.matches(password)) {
            // Message d'erreur utilisateur
            return false
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        email = viewBinding.editTextMail.text.toString()

        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

        if (!regex.matches(email)) {
            // Message d'erreur utilisateur
            return false
        }

        if (NetworkService.user.isEmailAvailable(email)) {
            // Message d'erreur utilisateur
            return false
        }
        return true

    }

}
