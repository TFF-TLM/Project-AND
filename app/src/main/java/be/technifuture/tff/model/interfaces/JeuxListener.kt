package be.technifuture.tff.model.interfaces


interface JeuxListener {

    fun onInterestOpenned(id: String)
    fun onChatOpenned(id: String)
    fun onClosePopUp()
}