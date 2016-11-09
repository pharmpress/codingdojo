package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}

class AboutUniformAccessPrinciple extends FunSuite with Matchers with KoanMatcher  {

  class CalculatesAgeUsingMethod(var currentYear: Int, birthYear: Int) {

    def age = currentYear - birthYear

    // calculated when method is called
  }

  class CalculatesAgeUsingProperty(var currentYear: Int, birthYear: Int) {
    // does age stay up to date if defined as a var instead of a val?
    val age = currentYear - birthYear
    // calculated at instantiation, returns property when called
  }

  test("Can access age as parameterless method") {
    val me = new CalculatesAgeUsingMethod(2010, 2003)
    me.age should be(__)
  }

  test("Can access age as property") {
    val me = new CalculatesAgeUsingProperty(2010, 2003)
    me.age should be(__)
  }

  test("Cannot add parameter to Method invocation") {
    val me = new CalculatesAgeUsingMethod(2010, 2003)
    // uncomment following line to see what happens if you try to access parameterless method with parens
    //me.age() should be (7)
  }
  test("What happens when I update current year using property") {
    val me = new CalculatesAgeUsingProperty(2010, 2003)


    me.currentYear = 2011
    me.age should be(__)
  }

  test("What happens when I update current year using method") {
    val me = new CalculatesAgeUsingMethod(2010, 2003)


    me.currentYear = 2011
    me.age should be(__)
  }


}
