package com.pharmpress.dojo.currency

import java.text.NumberFormat
import scala.language.implicitConversions

object Amount {
  implicit def amountNumeric(implicit currencyConversion: ConverterType) = new AmountNumeric(currencyConversion)
}

case class Amount(value: AmountType, currency: Currency)(implicit currencyConversion: ConverterType) extends Ordered[Amount] {
  def to(targetCurrency: Currency): Amount = if (targetCurrency == currency) {
    this
  } else {
    Amount(value * currencyConversion.converter(currency, targetCurrency), targetCurrency)
  }

  def +(amount: Amount): Amount = performOperation(amount) { _ + _ }

  def -(amount: Amount): Amount = performOperation(amount) { _ - _ }

  def *(amount: Amount): Amount = performOperation(amount) { _ * _ }

  def /(amount: Amount): Amount = performOperation(amount) { _ / _ }

  def unary_- : Amount = copy(value = -value)

  def +(otherValue: AmountType): Amount = copy(value = value + otherValue)

  def -(otherValue: AmountType): Amount = copy(value = value - otherValue)

  def *(otherValue: AmountType): Amount = copy(value = value * otherValue)

  def /(otherValue: AmountType): Amount = copy(value = value / otherValue)

  private def performOperation(amount: Amount)(operation: (AmountType, AmountType) => AmountType) = {
    Amount(operation(value, (amount to currency).value), currency)
  }

  override def compare(that: Amount): Int = value compare (that to currency).value

  override def toString: String = {
    val formatter = NumberFormat.getCurrencyInstance(currencyConversion.locale)
    formatter.setCurrency(currency.native)
    formatter.format(value)
  }
}