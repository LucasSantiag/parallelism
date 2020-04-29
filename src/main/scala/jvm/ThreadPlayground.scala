package jvm

object ThreadPlayground extends App {
  val t = new HelloThread
  val s = new HelloThread

  t.start()
  s.start()
  t.join()
  s.join()
}

sealed class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello")
    println("World!")
  }
}
