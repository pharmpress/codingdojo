package org.functionalkoans.forscala

import org.scalatest.{FunSuite, Matchers}

class AboutForExpressions extends FunSuite with Matchers with KoanMatcher  {

  test("For loops can be simple") {
    val someNumbers = Range(0, 10)
    var sum = 0
    for (i <- someNumbers)
      sum += i

    sum should equal(__)
  }

  test("For loops can contain additional logic") {
    val someNumbers = Range(0, 10)
    var sum = 0
    // sum only the even numbers
    for (i <- someNumbers)
      if (i % 2 == 0) sum += i

    sum should equal(__)
  }
  
  test("For expressions can nest, with later generators varying more rapidly than earlier ones") {
    val xValues = Range(1, 5)
    val yValues = Range(1, 3)
    val coordinates = for {
      x <- xValues
      y <- yValues} yield (x, y)
    coordinates(4) should be(__, __)
  }
}
