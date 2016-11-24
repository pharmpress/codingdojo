package com.pharmpress.dojo

package object currency {

  implicit class AmountWrapper(value: Double) {
    def apply(currency: Currency): Amount = new Amount(value, currency)
  }

}
