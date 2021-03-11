package one.xingyi.typeclasses101

import scala.concurrent.Future

case class Money(amount: Int) {
  def +(other: Money) = Money(amount + other.amount)
}

object TypeClass {
  val ints = List(1, 2, 3)
  val doubles = List(1.0, 2.0, 3.0)
  val strings = List("one", "two", "three")
  val maps = List(Map("a" -> 1), Map("b" -> 2), Map("c" -> 3))
  val money = List(Money(1), Money(2), Money(3))

  def addInts(ts: List[Int]) = ts.foldLeft(0) { _ + _ }
  def addDoubles(ts: List[Double]) = ts.foldLeft(0.0) { _ + _ }
  def addStrings(ts: List[String]) = ts.foldLeft("") { _ + _ }
  def addMaps[K, V](ts: List[Map[K, V]]) = ts.foldLeft(Map[K, V]()) { _ ++ _ }
  def addMoney(ts: List[Money]) = ts.foldLeft(Money(0)) { _ + _ }

  def addAll[T](list: List[T]): T = ???
}

trait Functor[F[_]] {
  def map[T, T1](f: F[T], fn: T => T1): F[T1]
}

object Functor {
  implicit val functorForOption: Functor[Option] = new Functor[Option] {
    override def map[T, T1](f: Option[T], fn: T => T1): Option[T1] = f.map(fn)
  }
}

trait Monoid[T] {
  def add(t: T, t2: T): T

}
object HigherOrderTypeClases {
  val list = List(1, 2, 3)
  val vector = Vector(1, 2, 3)
  val set = Set(1, 2, 3)
  val future = Future.successful(6)

  //Currying
  def add(one: Int, two: Int) = one + two

  def curriedAdd(one: Int)(two: Int) = one + two

  def add3: Int => Int = curriedAdd(3)
  def add6: Int => Int = curriedAdd(6)


  /** For every item in F it adds t to it */
  def addToAll[F[_]: Functor, T: Monoid](f: F[T], i: T)(implicit functor: Functor[F], monoid: Monoid[T]): F[T] = functor.map[T, T](f, t => monoid.add(t, i))

}

object CombinedTypeClases {
  val list = List(1, 2, 3)
  val vector = Vector(1.0, 2.0, 3.0)
  val set = Set(Money(1), Money(2), Money(3))
  val future = Future.successful(Money(6))

  def addAll[F[_], T](f: F[T]): T = ???
}
