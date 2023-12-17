package be.technifuture.tff.service

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.model.enums.BonusType
import be.technifuture.tff.service.network.utils.ClanBuilder
import retrofit2.Response
import java.lang.Thread.sleep

class UserService {

    private val mockUser = UserModel(
        643, "Tony", "user_test@tff.be",
        "\"https://cdn.artphotolimited.com/images/60df3a8fbd40b852ce5e0fff/300x300/big-smile-please.jpg",
        ClanBuilder.buildClan(2, "achamer"),
        10,
        150,
        50,
        10,
        /*mutableListOf(
            Bonus(BonusType.Soins, 3, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
            Bonus(BonusType.Croquette, 10, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
            Bonus(BonusType.Bouclier, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBZG_ry2wJ5uNGBqeHpupjJ2uH4daVZtW29hqd4tSZLyhvPREQlfsClKY7irR7UEqlL-4&usqp=CAU")
        )*/
    )

    fun isLoginAvailable(login: String): Boolean {
        val tempUserYetUse = mutableListOf("tony", "medhi", "laurent", "user")

        return !tempUserYetUse.contains(login.lowercase())
    }

    fun isEmailAvailable(email: String): Boolean {
        val tempUserYetUse = mutableListOf("a@a.a", "a@b.c", "b@b.b", "b@b.c")
        return !tempUserYetUse.contains(email)
    }

    fun getUserByLogin(login: String, pass: String, onCompletion: (UserModel?) -> Unit) {
        //TODO: Appel pour verifier l'user
        val mockUserLogin = mutableListOf("tony", "medhi", "laurent", "user")
        val mockMdp = "12345678"

        return if (mockUserLogin.contains(login.lowercase()) &&
            mockMdp == pass
        ) {
            onCompletion(mockUser)
        } else {
            onCompletion(null)
        }
        //TODO: Implementation API
        /*CoroutineScope(Dispatchers.IO).launch {
            val response = getUser(login, pass)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        onCompletion(response.body())
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }*/

    }

    fun getUserById(id: Int, onCompletion: (UserModel) -> Unit) {
        onCompletion(mockUser)

    }

    fun insertUser(user: NewUserModel, onComplet: (UserModel) -> Unit) {
        onComplet(mockUser)
        //TODO: Insertien d'un user à l'API
    }

    fun generateAvatar(list: MutableList<String>, onComplet: (String) -> Unit) {
        sleep(3000)
        val urlAvatar = mutableListOf(
            "https://img.getimg.ai/generated/img-t5OD7O05ksIz34xRXipoO.jpeg",
            "https://img.getimg.ai/generated/img-4nKJPBr3Xiw26BlAuiA8y.jpeg",
            "https://img.getimg.ai/generated/img-Xe7GNQfa4F2PzSTv0XQ4N.jpeg"
        )
        onComplet(urlAvatar.random())
    }

    suspend fun getUser(login: String, password: String): Response<UserModel?> =
        NetworkService.getRetrofit().create(NetworkServiceInterface::class.java)
            .getUser(login, password)

    suspend fun dataByName(name: String): Response<UserModel> =
        NetworkService.getRetrofit().create(NetworkServiceInterface::class.java).dataByName(name)

    suspend fun listOfCategory(): Response<UserModel> =
        NetworkService.getRetrofit().create(NetworkServiceInterface::class.java).listOfCategory()


    suspend fun listOfIngredient(): Response<UserModel> =
        NetworkService.getRetrofit().create(NetworkServiceInterface::class.java).listOfIngredient()
}