package be.technifuture.tff.model

class Settings (
    var isFirstLaunch : Boolean = true,
    var LocalisationGps : GpsCoordinates) {
}

var mySetting : Settings = Settings(
    true,
    GpsCoordinates(5.4896, 50.6077)
)
