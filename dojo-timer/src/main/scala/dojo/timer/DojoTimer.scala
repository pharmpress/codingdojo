package dojo.timer


import org.joda.time.Duration
import org.joda.time.format.{PeriodFormatterBuilder, PeriodFormatter}

trait Ticker{
  def millis: Long
}

//TODO Integration test
object SystemTicker extends Ticker{
  def millis = System.currentTimeMillis()
}

class DojoTimer(ticker: Ticker) {
  var ticking = false
  var millis = 0L
  var startMillis = 0L

  def start = {
    ticking = true
    startMillis = ticker.millis
  }

// TODO Test case with reset
//  def reset = millis = 0L

  def stop = {
    ticking = false
    millis += ticker.millis - startMillis
  }

  def display:String = {
    val duration = new Duration(if(ticking) millis + (ticker.millis - startMillis) else millis)
    val formatter = new PeriodFormatterBuilder()
      .printZeroAlways().minimumPrintedDigits(2)
      .appendHours()
      .appendSeparator(":")
      .appendMinutes()
      .appendSeparator(":")
      .appendSeconds()
      .toFormatter
    formatter.print(duration.toPeriod)
  }
}
