package org.functionalkoans.forscala

import org.scalatest.{FunSuite, GivenWhenThen}

/**
  * Created by Ramiro on 19/07/2016.
  */
class GWTSuite extends FunSuite  {

  info("GLOBAL Hello")

  test("some test"){

    info("given something")
    info("when something")
    info("then something")
  }

}
