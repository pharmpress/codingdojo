package com.pharmpress.dojo.currency



case class Amount(value: Double, currency: Currency){
  def +(amount: Amount)(implicit currencyConversion: (Currency, Currency) => Double): Amount = {
    Amount(value + amount.value * currencyConversion(amount.currency, currency) , currency)
  }
}