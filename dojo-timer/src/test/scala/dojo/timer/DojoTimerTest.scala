package dojo.timer

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DojoTimerTest extends FunSuite with BeforeAndAfterEach with MockFactory {

  class FakeTicker extends Ticker{
    var currentMillis = 0L
    def tick(mills: Long) = currentMillis += mills
    def millis = currentMillis
  }

  test("initial state"){
    System.currentTimeMillis()
    val ticker = new FakeTicker()
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
  }
  //integration
  test("start timer and 5 seconds passes"){
    val ticker = SystemTicker
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
    timer.start
    Thread.sleep(5050L)
    assert(timer.display == "00:00:05")
  }

  test("start timer and 1 minute passes"){
    val ticker = mock[Ticker]
    (ticker.millis _).expects().returns(0)
    (ticker.millis _).expects().returns(5000L)
    val timer = new DojoTimer(ticker)
    assert(timer.display == "00:00:00")
    timer.start
    //1 minute passes
    assert(timer.display == "00:00:05")
  }

  test("start timer and 1 minute pause"){
    val ticker = new FakeTicker()
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
