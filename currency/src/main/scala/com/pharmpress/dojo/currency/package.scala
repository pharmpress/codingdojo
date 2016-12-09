package com.pharmpress.dojo

import scala.language.implicitConversions

package object currency {
  type AmountType = BigDecimal
  type ConverterMethod = PartialFunction[(Currency, Currency), AmountType]
  type ConverterType = CurrencyConverter

  implicit class DoubleAmountWrapper(value: Double)(implicit currencyConversion: ConverterType) {
    def apply(currency: Currency): Amount = new Amount(value, currency)
  }

  implicit class IntAmountWrapper(value: Int)(implicit currencyConversion: ConverterType) {
    def apply(currency: Currency): Amount = new Amount(value, currency)
  }
}
