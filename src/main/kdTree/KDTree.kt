package main.kdTree

/** KDTree - Example:
 *  fun showNearest(kd: KdTree, p: Point) {
 *       println("Point            : ${p.asList()}")
 *       val (nn, ssq, nv) = kd.nearest(p)
 *       println("Nearest neighbor : ${nn?.asList()}")
 *       println("Distance         : ${Math.sqrt(ssq)}")
 *       println("Nodes visited    : $nv")
 *   }
 *
 *   fun main(args: Array<String>) {
 *       val points = mutableListOf(
 *       doubleArrayOf(2.0, 3.0),
 *       doubleArrayOf(5.0, 4.0),
 *       doubleArrayOf(9.0, 6.0),
 *       doubleArrayOf(4.0, 7.0),
 *       doubleArrayOf(8.0, 1.0),
 *       doubleArrayOf(7.0, 2.0))
 *
 *       var hr = HyperRect(doubleArrayOf(0.0, 0.0), doubleArrayOf(10.0, 10.0))
 *       var kd = KdTree(points, hr)
 *       showNearest(kd, doubleArrayOf(9.0, 2.0))
 *
 *   Sample output:
 *   Point            : [9.0, 2.0]
 *   Nearest neighbor : [8.0, 1.0]
 *   Distance         : 1.4142135623730951
 *   Nodes visited    : 3
 */

typealias Point = DoubleArray

fun Point.sqd(p: Point) = this.zip(p) { a, b -> (a - b) * (a - b) }.sum()

class HyperRect (val min: Point, val max: Point) {
    fun copy() = HyperRect(min.copyOf(), max.copyOf())
}

data class NearestNeighbor(val nearest: Point?, val distSqd: Double, val nodesVisited: Int)

class KdNode(
    val domElt: Point,
    val split: Int,
    var left:  KdNode?,
    var right: KdNode?
)

class KdTree(pts: MutableList<Point>, val bounds: HyperRect) {
    val n: KdNode?

    init {
        fun nk2(exset: MutableList<Point>, split: Int): KdNode? {
            if (exset.size == 0) return null
            val exset2 = exset.sortedBy { it[split] }
            for (i in 0 until exset.size) exset[i] = exset2[i]
            var m = exset.size / 2
            val d = exset[m]
            while (m + 1 < exset.size && exset[m + 1][split] == d[split]) m++
            var s2 = split + 1
            if (s2 == d.size) s2 = 0
            return KdNode(
                d,
                split,
                nk2(exset.subList(0, m), s2),
                nk2(exset.subList(m + 1, exset.size), s2)
            )
        }
        this.n = nk2(pts, 0)
    }

    fun nearest(p: Point) = nn(n, p, bounds, Double.POSITIVE_INFINITY)

    private fun nn(
        kd: KdNode?,
        target: Point,
        hr: HyperRect,
        maxDistSqd: Double
    ): NearestNeighbor {
        if (kd == null) return NearestNeighbor(null, Double.POSITIVE_INFINITY, 0)
        var nodesVisited = 1
        val s = kd.split
        val pivot = kd.domElt
        val leftHr = hr.copy()
        val rightHr = hr.copy()
        leftHr.max[s] = pivot[s]
        rightHr.min[s] = pivot[s]
        val targetInLeft = target[s] <= pivot[s]
        val nearerKd = if (targetInLeft) kd.left else kd.right
        val nearerHr = if (targetInLeft) leftHr else rightHr
        val furtherKd = if (targetInLeft) kd.right else kd.left
        val furtherHr = if (targetInLeft) rightHr else leftHr
        var (nearest, distSqd, nv) = nn(nearerKd, target, nearerHr, maxDistSqd)
        nodesVisited += nv
        var maxDistSqd2 = if (distSqd < maxDistSqd) distSqd else maxDistSqd
        var d = pivot[s] - target[s]
        d *= d
        if (d > maxDistSqd2) return NearestNeighbor(nearest, distSqd, nodesVisited)
        d = pivot.sqd(target)
        if (d < distSqd) {
            nearest = pivot
            distSqd = d
            maxDistSqd2 = distSqd
        }
        val temp = nn(furtherKd, target, furtherHr, maxDistSqd2)
        nodesVisited += temp.nodesVisited
        if (temp.distSqd < distSqd) {
            nearest = temp.nearest
            distSqd = temp.distSqd
        }
        return NearestNeighbor(nearest, distSqd, nodesVisited)
    }
}