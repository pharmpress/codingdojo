package com.pharmpress.dojo.currency

import com.pharmpress.dojo.currency.Converter.ConverterType

class AmountNumeric(defaultCurrency: Currency)(implicit currencyConversion: ConverterType) extends Numeric[Amount] {

  override def plus(x: Amount, y: Amount): Amount = x + y

  override def minus(x: Amount, y: Amount): Amount = x - y

  override def times(x: Amount, y: Amount): Amount = x * y

  override def negate(x: Amount): Amount = x.copy(value = -x.value)

  override def fromInt(x: Int): Amount = Amount(x.toDouble, defaultCurrency)

  override def toInt(x: Amount): Int = x.value.toInt

  override def toLong(x: Amount): Long = x.value.toLong

  override def toFloat(x: Amount): Float = x.value.toFloat

  override def toDouble(x: Amount): Double = x.value.toDouble

  override def compare(x: Amount, y: Amount): Int = x compare y
}