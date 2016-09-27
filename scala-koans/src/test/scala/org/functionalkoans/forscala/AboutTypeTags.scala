package org.functionalkoans.forscala

import org.functionalkoans.forscala.support.KoanFunSuite
import org.scalatest.Matchers

import scala.reflect.runtime.universe._

class Candy

class AboutTypeTags extends KoanFunSuite with Matchers {

  koan("""TypeTags can be used to determine a type used
         |   before it erased by the VM by using an implicit TypeTag argument.""") {
    def inspect[T](l: List[T])(implicit tt: TypeTag[T]) = tt.tpe.typeSymbol.name.decoded
    val list = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
    inspect(list) should be(__)
  }

  koan("""TypeTags can also be """) {
    def inspect[T : TypeTag](l: List[T]) = typeOf[T].typeSymbol.name.decoded
    val list = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
    inspect(list) should be(__)
  }

  koan("""TypeTags can be attached to classes. TypeTags have other meta-information about
         |  the type erased""") {
    class Barrel[T](implicit tt:TypeTag[T]) {
      def +(t: T) = "1 %s has been added".format(tt.tpe.typeSymbol.name.decoded) //Simple-name of the class erased
    }
    val candyBarrel = new Barrel[Candy]
    (candyBarrel + new Candy) should be(__)
  }
}