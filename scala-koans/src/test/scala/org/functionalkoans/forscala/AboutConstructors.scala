package org.functionalkoans.forscala

import org.functionalkoans.forscala.support.KoanFunSuite
import org.scalatest.Matchers

class AboutConstructors extends KoanFunSuite with Matchers {

  class AboutConstructorWithAuxiliaryConstructor(val name: String) {
    // invoke auxiliary constructor
    def this() {
      // what happens if you comment out the following line?
      this ("defaultname")
    }
  }

  koan("Primary constructor specified with a parameter requires that parameter to be passed in") {
    val aboutMe = new AboutConstructorWithAuxiliaryConstructor()
    aboutMe.name should be (__)
  }

  class AboutClassWithNoClassParameter

  koan("Class with no class parameters is called with no arguments") {
    // add parameter to make this fail
    val aboutMe = new AboutClassWithNoClassParameter

  }
}
