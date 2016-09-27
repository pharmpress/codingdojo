package org.functionalkoans.forscala.support

import org.scalatest.{ Args, FailedStatus, Status, Suite }

trait KoanSuite extends Suite with KoanMatchers {

  def meditate = pending

  abstract override def runTest(testName: String, args: Args): Status = {
    if (!args.stopper.stopRequested) {
      super.runTest(testName, args)
    } else {
      FailedStatus
    }
  }
}
