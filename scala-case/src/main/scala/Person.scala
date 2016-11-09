
package examples4 {

  import scala.runtime.{ScalaRunTime, Statics}

  case class Person extends Object with Product with Serializable {
     private[this] val name: String = _
     def name(): String = Person.this.name
     private[this] val age: Int = _
     def age(): Int = Person.this.age
     def copy(name: String, age: Int): examples1.Person = new examples1.Person(name, age)
     def copy$default$1(): String = Person.this.name()
     def copy$default$2(): Int = Person.this.age()

    override  def productPrefix(): String = "Person"

     def productArity(): Int = 2

    def productElement(x$1: Int): Object = {
      case  val x1: Int = x$1
      (x1: Int) match {
        case 0 => Person.this.name()
        case 1 => scala.Int.box(Person.this.age())
        case _ => throw new IndexOutOfBoundsException(scala.Int.box(x$1).toString())
      }
    }
    override  def productIterator(): Iterator = ScalaRunTime.typedProductIterator(Person.this)

    def canEqual(x$1: Object): Boolean = x$1.isInstanceOf[examples1.Person]

    override  def hashCode(): Int = {
       var acc: Int = -889275714
      acc = Statics.mix(acc, Statics.anyHash(Person.this.name()))
      acc = Statics.mix(acc, Person.this.age())
      Statics.finalizeHash(acc, 2)
    }

    override  def toString(): String = ScalaRunTime._toString(Person.this)

    override  def equals(x$1: Object): Boolean = Person.this.eq(x$1).||({
  case  val x1: Object = x$1
  case5(){
    if (x1.$isInstanceOf[examples1.Person]())
      matchEnd4(true)
    else
      case6()
  }
  case6(){
    matchEnd4(false)
  }
  matchEnd4(x: Boolean){
    x
  }
}.&&({
       val Person$1: examples1.Person = x$1.$asInstanceOf[examples1.Person]()
      Person.this.name().==(Person$1.name()).&&(Person.this.age().==(Person$1.age())).&&(Person$1.canEqual(Person.this))
    }))
    def (name: String, age: Int): examples1.Person = {
      Person.this.name = name
      Person.this.age = age
      Person.super.()
      scala.Product$class./*Product$class*/$init$(Person.this)
      ()
    }
  }


   object Person extends scala.runtime.AbstractFunction2 with Serializable {
    final override  def toString(): String = "Person"
    case  def apply(name: String, age: Int): examples1.Person = new examples1.Person(name, age)
    case  def unapply(x$0: examples1.Person): Option = if (x$0.==(null))
      scala.this.None
    else
      new Some(new Tuple2(x$0.name(), scala.Int.box(x$0.age())))

     private def readResolve(): Object = examples1.this.Person

     case  def apply(v1: Object, v2: Object): Object = Person.this.apply(v1.$asInstanceOf[String](), scala.Int.unbox(v2))

     def (): examples1.Person.type = {
      Person.super.()
      ()
    }
  }
}

