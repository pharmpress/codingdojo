package com.pharmpress.dojo

import com.pharmpress.dojo.currency.Converter.ConverterType
import java.util.{Currency => JavaCurrency}
import scala.language.implicitConversions

package object currency {

  implicit class DoubleAmountWrapper(value: Double)(implicit currencyConversion: ConverterType) {
    def apply(currency: Currency): Amount = new Amount(value, currency)
  }

  implicit class IntAmountWrapper(value: Int)(implicit currencyConversion: ConverterType) {
    def apply(currency: Currency): Amount = new Amount(value, currency)
  }

  implicit def javaCurrencyToCurrency(javaCurrency: JavaCurrency): Currency = {
    Currency.values.find(_.native.getCurrencyCode == javaCurrency.getCurrencyCode).getOrElse(throw new IllegalArgumentException(s"${javaCurrency.getCurrencyCode} is not a valid currency code"))
  }
}
