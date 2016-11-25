package com.pharmpress.dojo.currency

import org.scalatest.FunSuite
import scala.language.implicitConversions
import java.util.{Currency => JavaCurrency}
import com.pharmpress.dojo.currency.Currency._

class CurrencyOperationsTest extends FunSuite {
  private implicit val currencyConversion = Converter.default(Map.empty.withDefaultValue(2.0))
  private implicit val amountNumeric = new AmountNumeric(USD)

  test("Addition") {
    val dollars = 2.0(USD)
    val pounds = 2.0(GBP)
    val total = dollars + pounds + 2.0
    assert(total === 8.0(USD))
  }

  test("Check all native currency") {
    Currency.values.foreach { currency =>
      assert(currency.native !== null)
    }
  }

  test("use java currency") {
    val usd = JavaCurrency.getInstance("USD")
    val pounds = 2.0(GBP)
    val dollars = pounds to usd
    assert(dollars === 4.0(USD))
  }

  test("sum"){
    val list = Seq(2.0(USD), 2.0(GBP))
    assert(list.sum === 6.0(USD))
  }

  test("display"){
    assert(2.0(GBP).toString === "£2.00")
    assert(2.0(USD).toString === "$2.00")
    assert(2.0(EUR).toString === "2,00 €")
    assert(2.0(ITL).toString === "ITL2.00")
  }
}
