package com.pharmpress.dojo.currency

class AmountNumeric(currencyConversion: ConverterType) extends Numeric[Amount] {
  private implicit val currencyConversion0 = currencyConversion

  override def plus(x: Amount, y: Amount): Amount = x + y

  override def minus(x: Amount, y: Amount): Amount = x - y

  override def times(x: Amount, y: Amount): Amount = x * y

  override def negate(x: Amount): Amount = -x

  override def fromInt(x: Int): Amount = x(currencyConversion.base)

  override def toInt(x: Amount): Int = x.value.toInt

  override def toLong(x: Amount): Long = x.value.toLong

  override def toFloat(x: Amount): Float = x.value.toFloat

  override def toDouble(x: Amount): Double = x.value.toDouble

  override def compare(x: Amount, y: Amount): Int = x compare y
}