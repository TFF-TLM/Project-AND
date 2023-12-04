package be.technifuture.tff.model

import be.technifuture.tff.model.enums.BonusType

class Bonus (
    var bonusType : BonusType = BonusType.Croquette,
    var nombreItem : Int = 0,
    var urlImage : String = "") {
}