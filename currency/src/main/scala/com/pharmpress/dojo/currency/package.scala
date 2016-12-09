package com.pharmpress.dojo

import scala.language.implicitConversions

package object currency {
  type AmountType = BigDecimal
  type ConverterMethod = PartialFunction[(Currency, Currency), AmountType]
  type ConverterType = CurrencyConverter

  implicit class NumberAmountWrapper[A](value: A)(implicit currencyConversion: ConverterType, conv: A => Number) {
    def apply(currency: Currency): Amount = new Amount(value.doubleValue(), currency)
  }
}
