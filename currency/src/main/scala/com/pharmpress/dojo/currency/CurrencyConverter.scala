package com.pharmpress.dojo.currency

import java.util.Locale

class CurrencyConverter(val locale: Locale, val base: Currency, val converter: ConverterMethod)

object CurrencyConverter {
  def apply(base: Currency, rates: (Currency, AmountType)*): ConverterType = {
    new CurrencyConverter(
      locale = Locale.getDefault,
      base = base,
      converter = rates.flatMap {
        case (currency, rate) => Seq(
          (base -> currency) -> rate,
          (currency -> base) -> 1.0 / rate
        )
      }.toMap
    )
  }
}
