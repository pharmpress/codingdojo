package com.pharmpress.scalaencoder

import shapeless._
import shapeless.labelled.FieldType

import scala.reflect._

trait ScalaEncoder[A] {
  def encode(value: A): String
}

object ScalaEncoder {

  def createEncoder[A](func: A => String): ScalaEncoder[A] = (value: A) => func(value)

  implicit val stringEncoder: ScalaEncoder[String] = createEncoder[String](str => s""""$str"""")
  implicit val intEncoder: ScalaEncoder[Int] = createEncoder(num => num.toString)
  implicit val booleanEncoder: ScalaEncoder[Boolean] = createEncoder(bool => bool.toString)
  implicit val hnilEncoder: ScalaEncoder[HNil] = createEncoder[HNil](hnil => "")

  implicit def hlistObjectEncoder[K <: Symbol, H, T <: HList](implicit witness: Witness.Aux[K], hEncoder: Lazy[ScalaEncoder[H]], tEncoder: ScalaEncoder[T]): ScalaEncoder[FieldType[K, H] :: T] = {
    createEncoder[FieldType[K, H] :: T] {
      case h :: HNil =>
        s"${witness.value.name}=${hEncoder.value.encode(h)}"
      case h :: t =>
        s"${witness.value.name}=${hEncoder.value.encode(h)}, ${tEncoder.encode(t)}"
    }
  }

  implicit def genericObjectEncoder[A: ClassTag, H <: HList](implicit generic: LabelledGeneric.Aux[A, H], hEncoder: Lazy[ScalaEncoder[H]]): ScalaEncoder[A] =
    createEncoder { a => classTag[A].runtimeClass.getName +  "(" + hEncoder.value.encode(generic.to(a)) + ")" }

  def apply[A](obj: A)(implicit enc: ScalaEncoder[A]): String = enc.encode(obj)

}
