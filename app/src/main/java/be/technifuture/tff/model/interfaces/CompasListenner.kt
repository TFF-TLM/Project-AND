package be.technifuture.tff.model.interfaces
interface OrientationListener {
    fun onOrientationChanged(azimuth: Float, pitch: Float, roll: Float)
}