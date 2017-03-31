package com.pharmpress.scalaencoder

import org.scalatest.FunSuite

class ScalaEncoderTest extends FunSuite {
  test("Encode"){
    val actual = ScalaEncoder(Employee("name", 1, manager = false))
    assert(actual.toString === """Employee(name="name", number=1, manager=false)""")
  }
}
