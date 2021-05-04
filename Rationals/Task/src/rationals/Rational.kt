package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

fun String.toRational(): Rational {
    if (!(this.contains("/"))) {
        return Rational(this.toBigInteger(), 1.toBigInteger())
    }
    val position: Int = this.indexOf('/')
    val n = this.take(position).toBigInteger()
    val d = this.drop(position + 1).toBigInteger()
    return Rational(n, d)
}

infix fun BigInteger.divBy(d: BigInteger): Rational {
    return Rational(this, d)
}

infix fun Int.divBy(d: Int): Rational {
    return Rational(this.toBigInteger(), d.toBigInteger())
}

infix fun Long.divBy(d: Long): Rational {
    return Rational(this.toBigInteger(), d.toBigInteger())
}

class Rational(var n: BigInteger, var d: BigInteger) : Comparable<Rational> {
    init {
        if (d.equals(0)) throw IllegalArgumentException()
        if (n.signum() == -1 && d.signum() == -1) {
            n = n.abs()
            d = d.abs()
        }
        if (n.signum() == 1 && d.signum() == -1) {
            n = n.negate()
            d = d.abs()
        }
        val gcd = n.abs().gcd(d.abs())
        n = n / gcd
        d = d / gcd

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        return compareTo(other as Rational) == 0
    }

    override fun toString(): String {
        if (this.d.equals(1.toBigInteger())) {
            return "$n"
        } else {
            return "$n/$d"
        }
    }

    override fun compareTo(other: Rational): Int {

        return (this.n.times(other.d)).compareTo(other.n.times(this.d))
    }

    operator fun plus(other: Rational): Rational {
        val nom = (this.n * other.d) + (this.d * other.n)
        val dom = this.d * other.d
        return Rational(nom, dom)
    }

    operator fun minus(other: Rational): Rational {
        val nom = (this.n * other.d) - (this.d * other.n)
        val dom = this.d * other.d
        return Rational(nom, dom)
    }

    operator fun times(other: Rational): Rational {
        val nom = this.n * other.n
        val dom = this.d * other.d
        return Rational(nom, dom)
    }

    operator fun div(other: Rational): Rational {
        val nom = this.n * other.d
        val dom = this.d * other.n
        return Rational(nom, dom)
    }

    operator fun unaryMinus(): Rational {
        return Rational(this.n.unaryMinus(), this.d)

    }

}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}