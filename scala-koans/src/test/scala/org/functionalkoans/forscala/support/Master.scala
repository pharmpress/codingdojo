package org.functionalkoans.forscala.support

import org.scalatest.Stopper
import language.reflectiveCalls

object Master extends Stopper {
  @volatile private var studentNeedsToMeditate = false

  override def stopRequested: Boolean = studentNeedsToMeditate

  override def requestStop(): Unit = studentNeedsToMeditate = true

}

