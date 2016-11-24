package com.pharmpress.dojo.currency

import org.scalatest.FunSuite

import scala.language.implicitConversions

import com.pharmpress.dojo.currency.Currency._

class CurrencyOperationsTest extends FunSuite {

  test("Addition") {
    implicit def currencyConversion(from: Currency, to: Currency) = 1.0

    val dollars = 2.0(USD)
    val pounds = 2.0(GBP)
    val total = dollars + pounds
    assert(total === 4.0(USD))
  }

  test("native") {
    Currency.values.foreach { currency =>
      assert(currency.native !== null)
    }
  }

}
