package dojo.timer.acceptance

import cucumber.api.{DataTable, PendingException}
import cucumber.api.scala.{EN, ScalaDsl}
import dojo.timer.{Ticker, DojoTimer}

class DojoTimerSteps extends ScalaDsl with EN{

  class FakeTicker extends Ticker{
    var currentMillis = 0L
    def tick(mills: Long) = currentMillis += mills
    def millis = currentMillis
  }

  var ticker: FakeTicker = _
  var timer: DojoTimer = _

  Given("""^a new timer$"""){ () =>
    ticker = new FakeTicker()
    timer = new DojoTimer(ticker)
  }

  Then("""^the timer should display: (\d+:\d+:\d+)$"""){ (arg:String) =>
    assert(timer.display == arg)
  }

  When("""^the start button is pushed$"""){ () =>
    timer.start
  }
  When("""^(\d+) minutes have passed$"""){ (arg0:Int) =>
    ticker.tick(arg0 * 60 * 1000)
  }
  When("""^the stop button is pushed$"""){ () =>
    timer.stop
  }
  When("""^the pause button is pushed$"""){ () =>
    timer.stop
  }
  When("""^(\d+) minute have passed$"""){ (arg0:Int) =>
    ticker.tick(arg0 * 60 * 1000)
  }
  Given("""^a new timer countdown$"""){ () =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  When("""^the time is set to: (\d+):(\d+):(\d+)$"""){ (arg0:Int, arg1:Int, arg2:Int) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  When("""^participants are:$"""){ (arg0:DataTable) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Then("""^the timer should display: john (\d+):(\d+):(\d+)$"""){ (arg0:Int, arg1:Int, arg2:Int) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Then("""^the timer should display: jane (\d+):(\d+):(\d+)$"""){ (arg0:Int, arg1:Int, arg2:Int) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }

}
