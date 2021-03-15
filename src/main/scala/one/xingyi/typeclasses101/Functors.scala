package one.xingyi.typeclasses101
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits._
trait Functor[F[_]] {
  def map[T, T1](f: F[T], fn: T => T1): F[T1]
}

object Functor {
  implicit val functorForOption: Functor[Option] = new Functor[Option] {
    override def map[T, T1](f: Option[T], fn: T => T1): Option[T1] = f.map(fn)
  }
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
    override def map[T, T1](f: Future[T], fn: T => T1): Future[T1] = f.map(fn)(executionContext)
  }

  implicit def compose[F[_], G[_]](implicit fFunctor: Functor[F], gFunctor: Functor[G]): Functor[({type L[A] = F[G[A]]})#L] =
    new Functor[({type L[A] = F[G[A]]})#L] {
      override def map[T, T1](f: F[G[T]], fn: T => T1): F[G[T1]] = fFunctor.map(f, { gt: G[T] => gFunctor.map(gt, fn) })
    }

  implicit def compose3[F[_], G[_], H[_]](implicit fFunctor: Functor[F], gFunctor: Functor[G], hFunctor: Functor[H]): Functor[({type L[A] = F[G[H[A]]]})#L] =
    new Functor[({type L[A] = F[G[H[A]]]})#L] {
      override def map[T, T1](f: F[G[H[T]]], fn: T => T1): F[G[H[T1]]] = fFunctor.map(f, { ght: G[H[T]] => gFunctor.map(ght, { ht: H[T] => hFunctor.map(ht, fn) }) })
    }
}

object FunctorDemo extends App {

  def add[F[_], T](f: F[T], t: T)(implicit functor: Functor[F], monoid: Monoid[T]): F[T] =
    functor.map(f, monoid.add(_, t))



  println(add(List(1, 2, 3), 3))

//  type ListOption[T] = List[Option[T]]
//  println(add[ListOption, Int](List(Option(1), Option(2), Option(3)), 3))
//
//  type FutureList[T] = Future[List[T]]
//  add[FutureList, Int](Future.successful(List(1, 2, 3)), 3).foreach(println)
//
//  type FutureListOption[T] = Future[List[Option[T]]]
//  add[FutureListOption, Int](Future.successful(List(Option(1), Option(2), Option(3))), 3).foreach(println)

}