package com.pharmpress.dojo.currency

import org.scalatest.FunSuite
import com.stackmob.newman._
import com.stackmob.newman.dsl._

import scala.concurrent._
import scala.concurrent.duration._
import java.net.URL

import net.liftweb.json.JsonAST.{JDouble, JField, JObject}
import net.liftweb.json.{DefaultFormats, JsonParser}
import java.util.{ Currency => JavaCurrency }

class RestTest extends FunSuite{
  test("Get rates from an fixer"){
    println(fromJsonFixer("""{"base":"USD","date":"2016-12-08","rates":{"GBP":0.78977}}"""))
  }

  def getRatesFromFixer: String = {
    implicit val httpClient = new ApacheHttpClient
    Await.result(GET(new URL("http://api.fixer.io/latest?base=USD&symbols=USD,GBP")).apply, 1.second).bodyString
  }

  def fromJsonFixer(jsonString: String): ConverterType = {
    val json = JsonParser.parse(jsonString)
    implicit val formats = DefaultFormats
    val base = JavaCurrency.getInstance((json \ "base").extract[String])
    val rates = for( JObject(child) <-  json \ "rates" ; JField(currencyName, JDouble(rate)) <- child ) yield {
      (JavaCurrency.getInstance(currencyName): Currency, rate: AmountType)
    }
    CurrencyConverter(base, rates:_ *)
  }
}
