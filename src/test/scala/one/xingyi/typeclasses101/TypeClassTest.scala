package one.xingyi.typeclasses101

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class TypeClassTest extends AnyFlatSpec with should.Matchers {

  behavior of "TypeClass"

  it should "allow numbers to be added" in {
    import TypeClass._
    addAll(ints) shouldBe 6
    addAll(doubles) shouldBe 6
    addAll(strings) shouldBe "onetwothree"
    addAll(maps) shouldBe Map("a" -> 1, "b" -> 2, "c" -> 3)
    addAll(money) shouldBe Money(6)
  }

  behavior of "HigherOrderTypeClasses"

  it should "allow us to abstract over lists/vectors/etc using functors" in {
    import HigherOrderTypeClases._

    addToAll(list, 3) shouldBe List(4, 5, 6)
    addToAll(vector, 3) shouldBe Vector(4, 5, 6)
    addToAll(set, 3) shouldBe Set(4, 5, 6)
    Await.result(addToAll(future, 3), Duration.Inf) shouldBe  9
  }


  behavior of "Combined"

  it should "allow to have different applicatives and different types" in {
    import CombinedTypeClases._

    addAll(list) shouldBe List(6)
    addAll(vector) shouldBe Vector(6.0)
    addAll(set) shouldBe Set(Money(6))
    addAll(future) shouldBe Future.successful(Money(6))
  }
}
