package parallel

import math.{exp, log, abs}

object ArraySum {

  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    var i = s
    var sum: Int = 0

    while(i < t) {
      sum += power(a(i), p)
      i += 1
    }
    sum
  }

  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    if (t - s < threshold)
      sumSegment(a, p, s, t)
    else {
      val m = s + (t - s) / 2
      val (sum1, sum2) = parallel(segmentRec(a, p, s, m), segmentRec(a, p, m, t))

      sum1 + sum2
    }
  }

  def pNorm(a: Array[Int], p: Double): Int =
    power(sumSegment(a, p, 0, a.length), 1/p)

  def pNormTwoPart(a: Array[Int], p: Double): Int = {
    val m = a.length / 2
    val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))

    power(sum1 + sum2, 1/p)
  }

  def pNormRec(a: Array[Int], p: Double): Int =
    power(segmentRec(a, p, 0, a.length), 1/p)

  def power(x: Int, p: Double): Int = exp(p * log(abs(x))).toInt
}
