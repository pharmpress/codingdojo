package org.functionalkoans.forscala.support

import org.scalatest.events.{ TestFailed, TestPending, _ }
import org.scalatest.{ Status, _ }

import scala.language.reflectiveCalls

abstract class KoansSuites(
  columnSize: Int = 50,
  showFailures: Boolean = false,
  bottomBorderPattern: String = "*",
  topBorderPattern: String = "*"
)(suitesToNest: Suite*) extends Suites(suitesToNest: _*) {

  private type TestEvent = {
    val ordinal: Ordinal
    val suiteName: String
    val testName: String
    val suiteId: String
    val suiteClassName: Option[String]
  }

  private def wordWrap(s: String, maxLength: Int): String = {
    s.replaceAll("\\s+", " ").split(" ").foldLeft(Array(""))((out, in) => {
      if ((out.last + " " + in).trim.length > maxLength) {
        out :+ in
      } else {
        out.updated(out.length - 1, out.last + " " + in)
      }
    }).mkString("\n").trim
  }

  private def topBorder = {
    if (topBorderPattern.nonEmpty) {
      topBorderPattern * (columnSize / topBorderPattern.length)
    } else {
      "*" * columnSize
    }
  }

  private def bottomBorder = {
    if (bottomBorderPattern.nonEmpty) {
      bottomBorderPattern * (columnSize / bottomBorderPattern.length)
    } else {
      "*" * columnSize
    }
  }

  private def meditationMessage(event: TestEvent) = {
    InfoProvided(
      ordinal = event.ordinal,
      message = Seq(
        topBorder,
        "",
        wordWrap(
          s"""Please meditate on koan "${event.testName.stripMargin}"
              | of suite "${event.suiteName}"""".stripMargin,
          columnSize
        ),
        "",
        bottomBorder
      ).mkString("\n", "\n", ""),
      nameInfo = Some(NameInfo(event.suiteName, event.suiteId, event.suiteClassName , Some(event.testName)))
    )
  }

  override def run(testName: Option[String], args: Args): Status = {
    super.run(testName, args.copy(reporter = new Reporter {
      def apply(event: Event) {
        event match {
          case e @ (_: TestIgnored | _: TestFailed | _: TestPending) =>
            args.stopper.requestStop()
            args.reporter(meditationMessage(e.asInstanceOf[TestEvent]))
            if (showFailures) {
              args.reporter(event)
            }
          case _ =>
            args.reporter(event)
        }
      }
    }))
  }
}
