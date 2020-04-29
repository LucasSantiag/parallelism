package jvm

object Atomicity extends App {
  private val x = new AnyRef {}
  private var v = 0L
  private var z = 0L

  def sumWithAtomicity(): Long = {
    z += 1
    z
  }

  def sumWithoutAtomicity(): Long = x.synchronized {
    v += 1
    v
  }

  def threadWithoutAtomicity(): Thread = {
    val t = new Thread {
      override def run(): Unit = {
        val lst = for (_ <- 0 until 10) yield sumWithoutAtomicity()
        println(lst)
      }
    }
    t.start()
    t
  }

  def threadWithAtomicity(): Thread = {
    val t = new Thread {
      override def run(): Unit = {
        val lst = for (_ <- 0 until 10) yield sumWithAtomicity()
        println(lst)
      }
    }
    t.start()
    t
  }

  threadWithAtomicity()
  threadWithAtomicity()

  threadWithoutAtomicity()
  threadWithoutAtomicity()
}