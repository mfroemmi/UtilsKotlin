package main.kdTree

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object KDTreeKotlinUtils {
    // Berechnung der kleinsten Entfernung zur aktuellen Position
    fun getNearestLocation(savedPath: MutableList<PathPoint>, p: Point): Point {
        // DoubleArray Liste aus den lng- und lat-Pfadpunktanteilen
        val points: MutableList<DoubleArray> = mutableListOf()
        for (i in 0 until savedPath.size) {
            points.add(doubleArrayOf(savedPath[i].lng, savedPath[i].lat))
        }

        // Maxi- und Minimalausdehung des zu untersuchenden Gebiets (0.01° ~ +-500m)
        val hr = HyperRect(doubleArrayOf(points[0][0]-0.01F, points[0][0]-0.01F), doubleArrayOf(points[0][0]+0.01F, points[0][0]+0.01F))
        val kd = KdTree(points, hr)
        // nn=nearest neighbor, dis=distance, nv=nodes visited
        val (nn, dis, nv) = kd.nearest(p)

        return nn!!
    }

    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta))
        if(dist > 1.0) { dist = 1.0 }
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344
        return dist
    }
    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }
    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}