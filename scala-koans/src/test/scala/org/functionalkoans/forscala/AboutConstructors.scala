package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}

class AboutConstructors extends FunSuite with Matchers with KoanMatcher  {

  class AboutConstructorWithAuxiliaryConstructor(val name: String) {
    // invoke auxiliary constructor
    def this() {
      // what happens if you comment out the following line?
      this ("defaultname")
    }
  }

  test("Primary constructor specified with a parameter requires that parameter to be passed in") {
    val aboutMe = new AboutConstructorWithAuxiliaryConstructor()
    aboutMe.name should be (__)
  }

  class AboutClassWithNoClassParameter

  test("Class with no class parameters is called with no arguments") {
    // add parameter to make this fail
    val aboutMe = new AboutClassWithNoClassParameter

  }
}
