package one.xingyi.typeclasses101
import scala.concurrent.{ExecutionContext, Future}

case class Money(amount: Int) {
  def +(other: Money) = Money(amount + other.amount)
}

trait Monoid[T] {
  def zero: T
  def op(t1: T, t2: T): T
}

object Monoid {
  implicit val monoidForInt: Monoid[Int] = new Monoid[Int] {
    override def zero: Int = 0
    override def op(t1: Int, t2: Int): Int = t1 + t2
  }
  implicit val monoidForString: Monoid[String] = new Monoid[String] {
    override def zero: String = ""
    override def op(t1: String, t2: String): String = t1 + t2
  }
  implicit val monoidForDouble: Monoid[Double] = new Monoid[Double] {
    override def zero: Double = 0
    override def op(t1: Double, t2: Double): Double = t1 + t2
  }
  implicit val monoidForMoney: Monoid[Money] = new Monoid[Money] {
    override def zero: Money = Money(0)
    override def op(t1: Money, t2: Money): Money = t1 + t2
  }
  implicit def monoidForMap[K, V]: Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
    override def zero: Map[K, V] = Map()
    override def op(t1: Map[K, V], t2: Map[K, V]): Map[K, V] = t1 ++ t2
  }

}


object TypeClass {
  val ints = List(1, 2, 3)
  val doubles = List(1.0, 2.0, 3.0)
  val strings = List("one", "two", "three")
  val maps = List(Map("a" -> 1), Map("b" -> 2), Map("c" -> 3))
  val money = List(Money(1), Money(2), Money(3))

  def addAll[T](list: List[T])(implicit monoid: Monoid[T]): T = list.foldLeft(monoid.zero)(monoid.op)


}


trait Functor[F[_]] {
  def map[T, T1](f: F[T], fn: T => T1): F[T1]
}

object Functor {
  implicit val functorForList: Functor[List] = new Functor[List] {
    override def map[T, T1](f: List[T], fn: T => T1): List[T1] = f.map(fn)
  }
  implicit val functorForVector: Functor[Vector] = new Functor[Vector] {
    override def map[T, T1](f: Vector[T], fn: T => T1): Vector[T1] = f.map(fn)
  }
  implicit val functorForSet: Functor[Set] = new Functor[Set] {
    override def map[T, T1](f: Set[T], fn: T => T1): Set[T1] = f.map(fn)
  }
  implicit def functorForFuture(implicit executionContext: ExecutionContext): Functor[Future] = new Functor[Future] {
    override def map[T, T1](f: Future[T], fn: T => T1): Future[T1] = f.map(fn)
  }
}

object HigherOrderTypeClases {
  val list = List(1, 2, 3)
  val vector = Vector(1, 2, 3)
  val set = Set(1, 2, 3)
  val future = Future.successful(6)

  /** For every item in F it adds t to it */
  def addToAll[F[_]](f: F[Int], i: Int)(implicit functor: Functor[F]): F[Int] =
    functor.map[Int, Int](f, x => x + i)

}
object CombinedTypeClases {
  val list = List(1, 2, 3)
  val vector = Vector(1.0, 2.0, 3.0)
  val set = Set(Money(1), Money(2), Money(3))
  val future = Future.successful(Money(6))

  def addAll[F[_], T](f: F[T]): T = ???

}
