package one.xingyi.typeclasses101


object TypeClass {
  val ints = List(1, 2, 3)
  val doubles = List(1.0, 2.0, 3.0)
  val strings = List("one", "two", "three")
  val maps = List(Map("a" -> 1), Map("b" -> 2), Map("c" -> 3))

  def addAll[T](list: List[T]): T = ???


}

object HigherOrderTypeClases {
  val list = List(1, 2, 3)
  val vector = Vector(1, 2, 3)
  val set = set(1, 2, 3)

  def addAll[F[_], T](f: F[T]): T = ???

}
