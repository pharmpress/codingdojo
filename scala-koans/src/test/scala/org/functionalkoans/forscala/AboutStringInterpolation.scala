package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}
import org.scalatest.Matchers

class AboutStringInterpolation extends FunSuite with Matchers with KoanMatcher {

  test(
    """String can be placed in not only in a format method call
       |but inside a string if you precede the string with an s
       |you can then refer to any value or variable within scope
       |This is called string interpolation.""".stripMargin) {
    val helloMessage = "Hello World"
    s"Application $helloMessage" should be (__)
  }

  test(
    """String interpolation can include expressions which can
      |include numbers and strings""".stripMargin) {
    val j = 190
    val k = "mint julips"
    s"${j -100} bottles of $k on the wall" should be (__)
  }

  test(
    """If your numbers need special formatting, you can use an
      |`f` prefix before the string instead of an `s`. Once an `f`
      |is placed in front of the string you can apply any printf format rule
      |using a % and the format rule after the variable reference.""".stripMargin) {
    val j = 190.4f  // f is to force the number as a float (per java)
    val k = "sparkling water"
    f"${j - 100}%2.2f bottles of $k%s on the wall" should be (__)
  }

}