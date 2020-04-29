package jvm

object Deadlock extends App {

  def startThread(a: Account, b: Account, n: Int): Thread = {
    val t = new Thread {
      override def run() {
        for (_ <- 0 until n) {
          a.transfer(b, 1)
        }
      }
    }
    t.start()
    t
  }

  val a1 = new Account(500000)
  val a2 = new Account(700000)

  val t = startThread(a1, a2, 150000)
  val s = startThread(a2, a1, 150000)
  t.join()
  s.join()
}

sealed class Account(private var amount: Int = 0) {
  def transfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }
}