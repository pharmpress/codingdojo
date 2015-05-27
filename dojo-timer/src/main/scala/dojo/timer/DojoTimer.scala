package dojo.timer


import org.joda.time.Duration
import org.joda.time.format.{PeriodFormatterBuilder, PeriodFormatter}

class Ticker{
  var millis = 0L
  var ticking = false
  def tick(mills: Long) = if (ticking) millis += mills
}

class DojoTimer(ticker: Ticker) {

  def start = ticker.ticking = true
  def reset = ticker.millis = 0L
  def stop = ticker.ticking = false

  def display:String = {
    val duration = new Duration(ticker.millis) // in milliseconds
    val formatter = new PeriodFormatterBuilder()
        .printZeroAlways().minimumPrintedDigits(2)
      .appendHours()
      .appendSeparator(":")
      .appendMinutes()
      .appendSeparator(":")
      .appendSeconds()
      .toFormatter()
    formatter.print(duration.toPeriod())
  }
}
