package be.technifuture.tff.model

import be.technifuture.tff.model.enums.BonusType

class PanierItemBoutique (
    var bonusType : BonusType = BonusType.Croquette,
    var prix : Double = 0.0,
    var quantite: Int = 1,
    var urlImage : String = "") {
}
