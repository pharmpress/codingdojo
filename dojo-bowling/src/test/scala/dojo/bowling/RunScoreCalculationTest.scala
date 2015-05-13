package dojo.bowling

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(strict=true) //tags = Array("@spare")
class RunScoreCalculationTest
