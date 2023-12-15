package be.technifuture.tff.service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.interfaces.OrientationListener
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


class OrientationArrow() {

    public fun updateArrowRotationDemo1(source:GpsCoordinates?, target:GpsCoordinates?) : Float {
        var bearing : Float = 0F
        if(source != null && target != null){
            bearing = calculateBearing(source, target).toFloat()
        }

        return normalizeDegree(bearing)
    }

    public fun updateArrowRotation1(source:GpsCoordinates, target:GpsCoordinates, azimuth: Float): Float {
        var bearing : Float = 0F
        if(source != null && target != null){
            bearing = calculateBearing(source, target).toFloat()
        }
        val direction = bearing - azimuth
        return normalizeDegree(direction)
    }

    public fun updateArrowRotation2(source:GpsCoordinates, target:GpsCoordinates, azimuth: Float): Float {
        var origin: Location = Location("")
        var target: Location = Location("")
        origin.latitude = source.latitude
        origin.longitude = source.longitude
        target.latitude = target.latitude
        target.longitude = target.longitude
        val bearing = origin.bearingTo(target)
        val direction = bearing - azimuth
        return normalizeDegree(direction)
    }

    private fun normalizeDegree(degrees: Float): Float {
        return (degrees + 360) % 360
    }

    private fun calculateBearing(Pos1: GpsCoordinates, Pos2: GpsCoordinates,): Double {
        val dLon = Pos2.longitude - Pos1.longitude
        val y = sin(dLon) * cos(Pos2.latitude)
        val x = cos(Pos1.latitude) * sin(Pos2.latitude) - sin(Pos1.latitude) * cos(Pos2.latitude) * cos(dLon)
        var brng = atan2(y, x)
        brng = Math.toDegrees(brng)
        brng = (brng + 360) % 360
        return brng
    }
}



class OrientationManager(private val context: Context, private val listener: OrientationListener) :
    SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    init {
        registerOrientationSensor()
    }

    private fun registerOrientationSensor() {
        val rotationMatrix = FloatArray(9)
        val orientationValues = FloatArray(3)

        val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            // If the rotation vector sensor is not available, you can fallback to other sensors
            // such as accelerometer and magnetometer.
            val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            val magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

            if (accelerometerSensor != null && magnetometerSensor != null) {
                sensorManager.registerListener(
                    this,
                    accelerometerSensor,
                    SensorManager.SENSOR_DELAY_UI
                )
                sensorManager.registerListener(
                    this,
                    magnetometerSensor,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

            val orientationValues = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            val azimuth = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
            val pitch = Math.toDegrees(orientationValues[1].toDouble()).toFloat()
            val roll = Math.toDegrees(orientationValues[2].toDouble()).toFloat()

            listener.onOrientationChanged(azimuth, pitch, roll)
        } else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER || event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            // If the rotation vector sensor is not available, you can use accelerometer and magnetometer
            // data to calculate the orientation.
            val rotationMatrix = FloatArray(9)
            val inclinationMatrix = FloatArray(9)
            val accelerometerValues = FloatArray(3)
            val magnetometerValues = FloatArray(3)

            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, accelerometerValues, 0, 3)
            } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, magnetometerValues, 0, 3)
            }

            val success =
                SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelerometerValues, magnetometerValues)

            if (success) {
                val orientationValues = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientationValues)

                val azimuth = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
                val pitch = Math.toDegrees(orientationValues[1].toDouble()).toFloat()
                val roll = Math.toDegrees(orientationValues[2].toDouble()).toFloat()

                listener.onOrientationChanged(azimuth, pitch, roll)
            }
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }
}


