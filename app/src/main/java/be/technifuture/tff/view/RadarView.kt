package be.technifuture.tff.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin


data class ObjectData(val distance: Float, val angle: Double)

class RadarView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val objects = mutableListOf<ObjectData>()

    private val radarPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val objectPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val scanPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private var scanAngle = 0.0
    private val scanLength = 300f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        val maxRadius: Float = 300f
        val minRadius: Float = 100f
        val stepSize: Float = 50f

        var radius = minRadius
        while (radius <= maxRadius) {
            canvas?.drawCircle(centerX, centerY, radius, radarPaint)
            radius += stepSize
        }

        // Dessinez les objets
        for (obj in objects) {
            val distance = obj.distance
            val angle = obj.angle

            val x = centerX + distance * cos(angle)
            val y = centerY + distance * sin(angle)

            canvas?.drawCircle(x.toFloat(), y.toFloat(), 10f, objectPaint)
        }

        // Dessinez la ligne de scan rotative
        val startX = centerX + scanLength * cos(scanAngle)
        val startY = centerY + scanLength * sin(scanAngle)
        canvas?.drawLine(centerX, centerY, startX.toFloat(), startY.toFloat(), scanPaint)

        // Mettez à jour l'angle de la ligne de scan
        scanAngle += Math.toRadians(1.0) // Augmentez l'angle selon votre vitesse souhaitée
        if (scanAngle >= 2 * Math.PI) {
            scanAngle = 0.0
        }

        // Redessinez la vue
        invalidate()
    }

    fun updateObjects(newObjects: List<ObjectData>) {
        objects.clear()
        objects.addAll(newObjects)
        invalidate()
    }
}