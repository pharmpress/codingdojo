package com.pharmpress.dojo.currency

class CurrencyConverter(val base: Currency, val converter: ConverterMethod)

object CurrencyConverter {
  def apply(base: Currency, rates: (Currency, AmountType)*): ConverterType = {
    new CurrencyConverter(
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
