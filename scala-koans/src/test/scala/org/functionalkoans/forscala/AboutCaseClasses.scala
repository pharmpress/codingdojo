package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}

class AboutCaseClasses extends FunSuite with Matchers with KoanMatcher  {

  // case classes are very convenient, they give you a lot for free. The following Koans will
  // help you understand some of the conveniences. Case classes are also an integral part of
  // pattern matching which will be the subject of a later

  test("Case classes have an automatic equals method that works") {
    case class Person(first: String, last: String)

    val p1 = new Person("Fred", "Jones")
    val p2 = new Person("Shaggy", "Rogers")
    val p3 = new Person("Fred", "Jones")

    (p1 == p2) should be(__)
    (p1 == p3) should be(__)

    (p1 eq p2) should be(__)
    (p1 eq p3) should be(__) // not identical, merely equal
  }

  test("Case classes have an automatic hashcode method that works") {
    case class Person(first: String, last: String)

    val p1 = new Person("Fred", "Jones")
    val p2 = new Person("Shaggy", "Rogers")
    val p3 = new Person("Fred", "Jones")

    (p1.hashCode == p2.hashCode) should be(__)
    (p1.hashCode == p3.hashCode) should be(__)
  }

  test("Case classes have a convenient way they can be created") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")
    val d2 = Dog("Rex", "Custom")
    val d3 = new Dog("Scooby", "Doberman") // the old way of creating using new

    (d1 == d3) should be(__)
    (d1 == d2) should be(__)
    (d2 == d3) should be(__)
  }

  test("Case classes have a convenient toString method defined") {
    case class Dog(name: String, breed: String)
    val d1 = Dog("Scooby", "Doberman")
    d1.toString should be(__)
  }

  test("Case classes have automatic properties") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")
    d1.name should be(__)
    d1.breed should be(__)

    // what happens if you uncomment the line below? Why?
    //d1.name = "Scooby Doo"
  }

  test("Case classes can have mutable properties") {
    case class Dog(var name: String, breed: String) // you can rename a dog, but change its breed? nah!
    val d1 = Dog("Scooby", "Doberman")

    d1.name should be(__)
    d1.breed should be(__)

    d1.name = "Scooby Doo" // but is it a good idea?

    d1.name should be(__)
    d1.breed should be(__)
  }

  test("Safer alternatives exist for altering case classes") {
    case class Dog(name: String, breed: String) // Doberman

    val d1 = Dog("Scooby", "Doberman")

    val d2 = d1.copy(name = "Scooby Doo") // copy the case class but change the name in the copy

    d1.name should be(__) // original left alone
    d1.breed should be(__)

    d2.name should be(__)
    d2.breed should be(__) // copied from the original
  }

  // case class has to be defined outside of the test for this one
  case class Person(first: String, last: String, age: Int = 0, ssn: String = "")

  test("Case classes have default and named parameters") {

    val p1 = Person("Fred", "Jones", 23, "111-22-3333")
    val p2 = Person("Samantha", "Jones") // note missing age and ssn
    val p3 = Person(last = "Jones", first = "Fred", ssn = "111-22-3333") // note the order can change, and missing age
    val p4 = p3.copy(age = 23)

    p1.first should be(__)
    p1.last should be(__)
    p1.age should be(__)
    p1.ssn should be(__)

    p2.first should be(__)
    p2.last should be(__)
    p2.age should be(__)
    p2.ssn should be(__)

    p3.first should be(__)
    p3.last should be(__)
    p3.age should be(__)
    p3.ssn should be(__)

    (p1 == p4) should be(__)
  }

  test("Case classes can be disassembled to their constituent parts as a tuple") {
    val p1 = Person("Fred", "Jones", 23, "111-22-3333")

    val parts = Person.unapply(p1).get // this seems weird, but it's critical to other features of Scala

    parts._1 should be(__)
    parts._2 should be(__)
    parts._3 should be(__)
    parts._4 should be(__)
  }
}
