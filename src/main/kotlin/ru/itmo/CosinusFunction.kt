package ru.itmo

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow

object CosinusFunction {

    private val cache: HashMap<Int, Long> = HashMap()
    const val PRECISION = 0.00001

    private fun factorial(num: Int): Long {
        val cached = cache[num]
        if (cached !== null) {
            return cached
        }
        var result = 1L
        if (num > 1) {
            result = factorial(num - 1) * num
        }
        cache[num] = result
        return result
    }

    private fun cosNByTailor(x: Double, n: Int): Double {
        val sign = if (n % 2 == 0) 1 else -1

        return sign * x.pow(2 * n) / factorial(2 * n)
    }

    fun cos(x: Double): Double {
        if (abs(x) > PI)
            return Double.NaN
        var result = 0.0
        var current = 10.0
        var prev = 0.0
        var n = 0
        while (abs(prev - current) >= PRECISION) {
            prev = current
            current = cosNByTailor(x, n)
            result += current
            n++
        }
        return result
    }
}