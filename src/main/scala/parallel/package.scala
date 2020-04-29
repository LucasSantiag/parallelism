package object parallel {
  final val threshold: Int = 5

  trait Task[A] {
    def join: A
  }

  def task(c: => A): Task[A]

  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
    val tB: Task[B] = task { taskB }
    val tA: A = taskA
    (tA, tB.join)
  }

  implicit def getJoin[T](x: Task[T]): T = x.join
}
