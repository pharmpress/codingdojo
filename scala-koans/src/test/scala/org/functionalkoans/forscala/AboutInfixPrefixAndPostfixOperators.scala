package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}

import language.postfixOps
import org.scalatest.Matchers

class AboutInfixPrefixAndPostfixOperators extends FunSuite with Matchers with KoanMatcher {

  test("""Simple: Infix Operators are available if an object
           |  has a method that takes one parameter.""".stripMargin) {

    val g: Int = 3
    (g + 4) should be(__) // + is an infix operator
     g.+(4) should be(__) // same result but not using the infix operator
  }

  test("""Infix Operators do NOT work if an object
           |  has a method that takes two parameters.""".stripMargin) {
    val g: String = "Check out the big brains on Brad!"
    g indexOf 'o' should be(__) //indexOf(Char) can be used as an infix operator
    //g indexOf 'o', 4 should be (6) //indexOf(Char, Int) cannot be used an infix operator
    g.indexOf('o', 7) should be(__) //indexOf(Char, Int) must use standard java/scala calls
  }

  test("""Postfix operators work if an object
           |  has a method that takes no parameters.""".stripMargin) {
    val g: Int = 31
    (g toHexString) should be(__) //toHexString takes no params therefore can be called
    //as a postfix operator. Hint: The answer is 1f
  }


  test("""Prefix operators work if an object
           |  has a method name that starts with unary_ .""".stripMargin) {
    val g: Int = 31
    (-g) should be(__)
  }

  test("""Here we create our own prefix operator for our own class.
          |   The only identifiers that can be used as prefix operators
          |   are +, -, !, and ~""".stripMargin) {

    class Stereo {
      def unary_+ = "on"

      def unary_- = "off"
    }

    val stereo = new Stereo
    (+stereo) should be(__)
    (-stereo) should be(__)
  }
}