package be.technifuture.tff.model

import java.util.Date

class Settings (
    var isFirstLaunch : Boolean = true,
    var longitude : Double,
    var latitude : Double) {
}

var mySetting : Settings = Settings(
    true,
    5.4896,
    50.6077)
