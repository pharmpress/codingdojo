package com.pharmpress.dojo.currency

import java.text.NumberFormat

import com.pharmpress.dojo.currency.Converter.ConverterType

import scala.language.implicitConversions
import Amount.AmountType

object Amount{
  type AmountType = BigDecimal
}

case class Amount(value: AmountType, currency: Currency)(implicit currencyConversion: ConverterType) extends Ordered[Amount]{
  def to(targetCurrency: Currency) = if(targetCurrency == currency) {
    this
  } else {
    Amount(value * currencyConversion(currency, targetCurrency), targetCurrency)
  }

  def +(amount: Amount): Amount = performOperation(amount){ _ + _ }

  def -(amount: Amount): Amount = performOperation(amount){ _ - _ }

  def *(amount: Amount): Amount = performOperation(amount){ _ * _ }

  def /(amount: Amount): Amount = performOperation(amount){ _ / _ }

  def unary_- : Amount = copy(value = -value)

  def +(otherValue: AmountType) = copy(value = value + otherValue)

  def -(otherValue: AmountType) = copy(value = value - otherValue)

  def *(otherValue: AmountType) = copy(value = value * otherValue)

  def /(otherValue: AmountType) = copy(value = value / otherValue)

  private def performOperation(amount: Amount)(operation: (AmountType, AmountType) => AmountType) = {
    Amount(operation(value, amount.to(currency).value) , currency)
  }

  override def compare(that: Amount): Int = value compare that.to(currency).value

  override def toString: String = {
    currency.locale match {
      case Some(locale) =>
        NumberFormat.getCurrencyInstance(locale).format(value)
      case None =>
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.setCurrency(currency.native)
        formatter.format(value)
    }
  }
}