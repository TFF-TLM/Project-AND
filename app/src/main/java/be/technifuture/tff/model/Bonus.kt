package be.technifuture.tff.model

enum class BonusType {
    Croquette,
    Bouclier,
    Attaque
}

class Bonus (
    var bonusType : BonusType = BonusType.Croquette,
    var nombreItem : Int = 0,
    var urlImage : String = "") {

}