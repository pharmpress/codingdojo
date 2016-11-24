package examples

import org.scalatest.FunSuite

class DebugMacrosTest extends FunSuite {
  test("hello"){
    import DebugMacros._
    hello()
  }

  test("debug"){
    import DebugMacros._

    val y = 10

    def function() {
      val p = 11
      debug(p)
      debug(p + y)
    }

    function()
  }

  test("debug1"){
    import DebugMacros._

    val y = 10

    def function() {
      val p = 11
      debug1(p)
      debug1(p + y)
    }

    function()
  }

  test("print"){
    import DebugMacros._
    printf1("hello %s!", "world")
  }
}
