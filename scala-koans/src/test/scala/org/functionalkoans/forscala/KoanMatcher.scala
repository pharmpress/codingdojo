package org.functionalkoans.forscala

import org.scalatest.exceptions.TestPendingException
import org.scalatest.matchers.Matcher

trait KoanMatcher {

  def  __ : Matcher[Any] = {
    throw new TestPendingException
  }

  protected class ___ extends Exception {
    override def toString = "___"
  }
}
