package dojo.bowling


import cucumber.api.{PendingException, DataTable}
import cucumber.api.scala.{EN, ScalaDsl}
import org. junit.Assert._
import scala.collection.JavaConversions.asScalaBuffer

class BowlingSteps extends ScalaDsl with EN {
  var game: Game = _
  Given("""^a new bowling game$"""){ () =>
    game = new Game
  }

  When("""^all of my balls are landing in the gutter$"""){ () =>
    for (i <- 0 until 20)
      game.roll(0)
  }
  Then("""^my total score should be (\d+)$"""){ (arg0:Int) =>
    assertEquals(arg0, game.score)
  }
  When("""^all of my rolls are strikes$"""){ () =>
    for (i <- 0 until 12)
      game.roll(10)
  }
  When("""^I roll (\d+) and (\d+)$"""){ (arg0:Int, arg1:Int) =>
    game.roll(arg0)
    game.roll(arg1)
  }
  When("""^I roll (\d+) and (\d+), (\d+) times$"""){ (arg0:Int, arg1:Int, arg2:Int) =>
    for (i <- 0 until arg2) {
      game.roll(arg0)
      game.roll(arg1)
    }
  }
  When("""^I roll the following series:$"""){ (arg0:DataTable) =>
    arg0.transpose().asList[Integer](classOf[Integer]).foreach{ game.roll(_) }
  }

  When("""^I roll the following formatted series:$"""){ (arg0:DataTable) =>
    arg0.transpose().asList[String](classOf[String]).foreach{ case guff =>
      val arr = guff.split("\\s")
      game.roll(Integer.parseInt(arr(0)))
      game.roll(Integer.parseInt(arr(1)))
    }
  }
  Then("""^my score table should be$"""){ (arg0:DataTable) =>

  }
  When("""^I roll (\d+)$"""){ (arg0:Int) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
}
