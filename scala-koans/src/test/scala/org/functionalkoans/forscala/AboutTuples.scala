package org.functionalkoans.forscala

import java.util.Date

import org.scalatest.{FunSuite, Matchers}

class AboutTuples extends FunSuite with Matchers with KoanMatcher  {

  test("Tuples can be created easily") {
    val tuple = ("apple", "dog")
    tuple should be(__)
  }

  test("Tuple items may be accessed individually") {
    val tuple = ("apple", "dog")
    val fruit = tuple._1
    val animal = tuple._2

    fruit should be(__)
    animal should be(__)
  }

  test("Tuples may be of mixed type") {
    val tuple5 = ("a", 1, 2.2, new Date(), BigDecimal(5))

    tuple5._2 should be(__)
    tuple5._5 should be(__)
  }

  test("Tuples items can be swapped on a Tuple 2") {
    val tuple = ("apple", 3).swap
    tuple._1 should be(__)
    tuple._2 should be(__)
  }
}
