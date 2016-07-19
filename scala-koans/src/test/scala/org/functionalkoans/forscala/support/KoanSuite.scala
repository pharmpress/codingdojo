package org.functionalkoans.forscala.support

import org.scalatest.events.{Event, TestFailed, TestIgnored, TestPending}
import org.scalatest.exceptions.TestPendingException
import org.scalatest.matchers.Matcher
import org.scalatest._

trait KoanSuite extends FunSuite with Matchers {

  def koan(name : String)(fun: => Unit) { test(name.stripMargin('|'))(fun) }

  def meditate() = pending

  def  __ : Matcher[Any] = {
    throw new TestPendingException
  }

  protected class ___ extends Exception {
    override def toString = "___"
  }

  private class ReportToTheMaster(other: Reporter, stopper: Stopper) extends Reporter {
    @volatile var failed = false
    def failure(suiteName: String, testName: String) {
      failed = true
      val message = Seq(
        "*****************************************",
        "*****************************************",
        "",
        "",
        "",
        "Please meditate on koan \"%s\" of suite \"%s\"" format(testName, suiteName),
        "",
        "",
        "",
        "*****************************************",
        "*****************************************"
      )
      message.foreach { println }
      stopper.requestStop
    }

    def apply(event: Event) {
      event match {
        case e: TestIgnored => failure(e.suiteName, e.testName)
        case e: TestFailed => failure(e.suiteName, e.testName)
        case e: TestPending => failure(e.suiteName, e.testName)
        case _ => other(event)
      }

    }
  }

  protected override def runTest(testName: String, args: Args): Status = {
    super.runTest(testName, args.copy(reporter = new ReportToTheMaster(args.reporter, args.stopper)))
  }

}
