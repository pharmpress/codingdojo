package examples

import scala.runtime.ScalaRunTime

class PersonB(var name: String, var age: Int) extends Product with Serializable {

  override def productElement(n: Int): Any = n match {
    case 0 => name
    case 1 => age
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }

  override def productArity: Int = 2

  override def productPrefix = PersonB.toString()

  override def canEqual(that: Any): Boolean = that.isInstanceOf[PersonB]

  override def equals(other: Any) = this.eq(other.asInstanceOf[AnyRef]) || (other match {
    case that @ PersonB(otherName, otherAge) if this.name == otherName && this.age == otherAge =>
      that.canEqual(this)
    case _ => false
  })

  override def hashCode = ScalaRunTime._hashCode(this)

  override def toString = ScalaRunTime._toString(this)

  def copy(name: String = this.name, age: Int = this.age) = new PersonB(name, age)

}

object PersonB extends ((String, Int) => PersonB) with Serializable {
  override def toString(): String = "PersonA"

  def apply(name: String, age: Int) = new PersonB(name, age)

  def unapply(arg: PersonB): Option[(String, Int)] = Option(arg).map { other => other.name -> other.age }

}
