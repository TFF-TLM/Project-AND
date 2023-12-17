package be.technifuture.tff.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin

data class ObjectData(val localX: Float, val localY: Float) {}
data class LocalCoordinates(val x: Double, val y: Double)


class RadarView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val objects = mutableListOf<ObjectData>()

    private val radarPaint = Paint().apply {
        color = Color.rgb(52, 156, 212)
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val objectPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    private val scanPaint = Paint().apply {
        color = Color.BLUE
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

        for (obj in objects) {
            val x = centerX + obj.localX
            val y = centerY + obj.localY
            canvas?.drawCircle(x, y, 10f, objectPaint)
        }

        val startX = centerX + scanLength * cos(scanAngle)
        val startY = centerY + scanLength * sin(scanAngle)
        canvas?.drawLine(centerX, centerY, startX.toFloat(), startY.toFloat(), scanPaint)

        scanAngle += Math.toRadians(1.0)
        if (scanAngle >= 2 * Math.PI) {
            scanAngle = 0.0
        }

        invalidate()
    }

    fun updateObjects(newObjects: MutableList<ObjectData>) {
        objects.clear()
        objects.addAll(newObjects)
        invalidate()
    }
}
