package one.xingyi.typeclasses101

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.concurrent.Future

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

    addAll(list) shouldBe List(6)
    addAll(vector) shouldBe Vector(6)
    addAll(set) shouldBe Set(6)
    addAll(future) shouldBe Future.successful(6)
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
