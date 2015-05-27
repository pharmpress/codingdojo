package dojo.timer

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterEach
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DojoTimerTest  extends FunSuite with BeforeAndAfterEach {
  test("initial state"){
    val ticker = new Ticker()
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
  }

  test("start timer and 1 minutes passes"){
    val ticker = new Ticker()
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
    timer.start
    //1 minutes passes
    ticker.tick(1 * 60 * 1000)
    assert(timer.display == "00:01:00")
  }

  test("start timer and 1 minute pause"){
    val ticker = new Ticker()
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
    timer.start
    //1 minutes passes
    ticker.tick(1 * 60 * 1000)
    timer.stop
    assert(timer.display == "00:01:00")
    // Wait a minute
    ticker.tick(1 * 60 * 1000)
    timer.start
    assert(timer.display == "00:01:00")
  }
}
