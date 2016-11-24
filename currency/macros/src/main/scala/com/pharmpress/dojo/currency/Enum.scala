package com.pharmpress.dojo.currency

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

abstract class Enum[E] {
  def values: Seq[E] = macro Enum.caseObjectsSeqImpl[E]
}

object Enum {
  def caseObjectsSeqImpl[A: c.WeakTypeTag](c: blackbox.Context): c.Expr[Seq[A]] = {
    import c.universe._
    val typeSymbol = weakTypeOf[A].typeSymbol.asClass
    require(typeSymbol.isSealed)
    val sourceModuleRef = (sym: Symbol) => {
      Ident(sym.asInstanceOf[scala.reflect.internal.Symbols#Symbol].sourceModule.asInstanceOf[Symbol])
    }
    val subclasses = typeSymbol.knownDirectSubclasses.toList
    require(subclasses.forall(_.isModuleClass))
    c.Expr[Seq[A]] {
      Apply(
        Ident(weakTypeOf[Seq[A]].typeSymbol.companion),
        subclasses.map{sourceModuleRef}
      )
    }
  }
}