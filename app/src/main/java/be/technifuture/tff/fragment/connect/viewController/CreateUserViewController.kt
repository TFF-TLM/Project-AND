package be.technifuture.tff.fragment.connect.viewController

import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.AlertDialogCustom.ErrorValidation
import be.technifuture.tff.service.network.dto.Register
import be.technifuture.tff.service.network.manager.AuthDataManager


class CreateUserController(private val viewBinding: FragmentCreateUserBinding,
                           private val alert: AlertDialogCustom
) {

    private val authManager = AuthDataManager.instance

    private var login: String = ""
    private var password: String = ""
    private var email: String = ""

    private val regexMail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{1,4}\$".toRegex()

    fun validateForm(handler: (user: NewUserModel) -> Unit) {
        if (loginIsValid() && isEmailValid() && isPasswordValid()) {
            authManager.isAvailableAndValid(Register(login, email, password)) { available, error, code ->
                error?.let {errorRegister ->
                    if (code == 400){
                        errorRegister.username?.isNotEmpty()?.let {
                            alert.getAlert(ErrorValidation.LOGIN_EXIST)
                        }
                        errorRegister.email?.isNotEmpty()?.let {
                            alert.getAlert(ErrorValidation.MAIL_EXIST)
                        }
                    }
                }
                available?.let {
                    if(it) {
                        handler(NewUserModel(login, email, password))
                    }
                }
            }
        }
    }

    private fun loginIsValid(): Boolean {
        login = viewBinding.editTextLogin.text.toString()

        // Must start by letter, Only Number and Letter for other
        // Max : 20 charactére
        val regex = "^(?=.*[A-Za-z0-9]\$)[A-Za-z][A-Za-z\\d.-]{4,19}\$".toRegex()

        if (!regex.matches(login)) {
            alert.getAlert(ErrorValidation.LOGIN_RULES)
            return false
        }

        return true
    }

    private fun isPasswordValid(): Boolean {
        password = viewBinding.editTextPassword.text.toString()
        val password2 = viewBinding.editTextVerifPassword.text.toString()

        if (password != password2) {
            alert.getAlert(ErrorValidation.PASSWORD_SAME)
            return false
        }
        // Minimum eight characters, at least one uppercase letter,
        // one lowercase letter, one number and one special character, Minimum 8 character
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

        //val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{1,4}\$".toRegex()

        if (!regexMail.matches(email)) {
            alert.getAlert(ErrorValidation.MAIL_RULES)
            return false
        }

        return true

    }



}
