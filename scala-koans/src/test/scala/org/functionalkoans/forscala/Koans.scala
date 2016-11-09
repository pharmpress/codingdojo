package org.functionalkoans.forscala

import org.scalatest._
import org.scalatest.events._
import scala.language.reflectiveCalls

class Koans extends Suites(
  new AboutAsserts,
  new AboutValAndVar,
  new AboutLiteralBooleans,
  new AboutLiteralNumbers,
  new AboutLiteralStrings,
  new AboutMethods,
  new AboutClasses,
  new AboutUniformAccessPrinciple,
  new AboutConstructors,
  new AboutParentClasses,
  new AboutOptions,
  new AboutObjects,
  new AboutApply,
  new AboutTuples,
  new AboutHigherOrderFunctions,
  new AboutEmptyValues,
  new AboutLists,
  new AboutMaps,
  new AboutSets,
  new AboutFormatting,
  new AboutStringInterpolation,
  new AboutPatternMatching,
  new AboutCaseClasses,
  new AboutRange,
  new AboutPartiallyAppliedFunctions,
  new AboutPartialFunctions,
  new AboutImplicits,
  new AboutTraits,
  new AboutForExpressions,
  new AboutInfixPrefixAndPostfixOperators,
  new AboutInfixTypes,
  new AboutMutableMaps,
  new AboutMutableSets,
  new AboutSequencesAndArrays,
  new AboutIterables,
  new AboutTraversables,
  new AboutNamedAndDefaultArguments,
  new AboutTypeTags,
  new AboutPreconditions,
  new AboutExtractors,
  new AboutByNameParameter,
  new AboutRepeatedParameters,
  new AboutTypeSignatures,
  new AboutTypeVariance,
  new AboutEnumerations
) {

  private class ReportToTheMaster(other: Reporter, stopper: Stopper) extends Reporter {
    private type KoanEven = {
      val ordinal : Ordinal
      val suiteName: String
      val testName: String
    }
    private def failure(event: KoanEven) =  {
      stopper.requestStop
      InfoProvided(
        ordinal = event.ordinal,
        message = Seq(
          "*****************************************",
          "*****************************************",
          "",
          "",
          "",
          "Please meditate on koan \"%s\" of suite \"%s\"" format(event.testName, event.suiteName),
          "",
          "",
          "",
          "*****************************************",
          "*****************************************"
        ).mkString("\n", "\n", ""),
        nameInfo = Some(NameInfo("Koan", "koan", None,None))
      )
    }

    def apply(event: Event) {
      event match {
        case e: TestIgnored => other(failure(e))
        case e: TestFailed  => other(failure(e))
        case e: TestPending => other(failure(e))
        case _ =>
      }
      other(event)
    }
  }

  override def run(testName: Option[String], args: Args): Status = {
    super.run(testName, args.copy(reporter = new ReportToTheMaster(args.reporter, args.stopper)))
  }
}
