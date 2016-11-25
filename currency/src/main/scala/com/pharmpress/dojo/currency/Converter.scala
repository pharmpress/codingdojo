package com.pharmpress.dojo.currency

import Amount.AmountType

object Converter {
  type ConverterType = (Currency, Currency) => AmountType

  def default(conversionMap: Map[(Currency, Currency), AmountType]): ConverterType = {
    conversionMap(_, _)
  }
}
