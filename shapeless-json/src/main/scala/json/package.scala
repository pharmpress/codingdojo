/**
The Play (2.3) json combinator library is arguably the best in the scala world. However it doesnt
      work with case classes with greater than 22 fields.
      The following gist leverages the shapeless 'Automatic Typeclass Derivation' facility to work around this
      limitation. Simply stick it in a common location in your code base, and use like so:
      Note: ** Requires Play 2.3 and shapeless 2.0.0


        import SWrites._
        import SReads._

        case class Foo(value: String)
        case class Bar(value1: Int, foo: Foo) //Didnt want to type out 23 fields, but you get the idea


          implicit val writes: Writes[Foo] = SWrites.auto.derive[Foo]
          implicit val reads: Reads[Foo] = SReads.auto.derive[Foo]

          implicit val writes: Writes[Bar] = SWrites.auto.derive[Bar]
          implicit val reads: Reads[Bar] = SReads.auto.derive[Bar]
      Additionally, you may get boilerplate free Format typeclasses:
      import SFormats.auto._
      case class Foo(value: String)
      case class Bar(value1: Int, foo: Foo)
      def someFunc(value: T)(implicit val format: Format[T]) = ...

  **/

import play.api.libs.json._


import shapeless.{ `::` => :#:, _ }


package object json {

  object SWrites extends LabelledProductTypeClassCompanion[Writes]{
    object typeClass extends LabelledProductTypeClass[Writes]{
      def emptyProduct: Writes[HNil] = Writes(_ => Json.obj())

      def product[F, T <: HList](name: String, FHead: Writes[F], FTail: Writes[T]) = Writes[F :#: T] {
        case head :#: tail =>
          val h = FHead.writes(head)
          val t = FTail.writes(tail)

          (h, t) match {
            case (JsNull, t: JsObject)     => t
            case (h: JsValue, t: JsObject) => JsObject(Seq(name -> h)) ++ t
            case _                         => Json.obj()
          }
      }
      def project[F, G](instance: => Writes[G], to: F => G, from: G => F) = Writes[F](f => instance.writes(to(f)))
    }
  }

  object SReads extends LabelledProductTypeClassCompanion[Reads]{
    object typeClass extends LabelledProductTypeClass[Reads]{
      def emptyProduct: Reads[HNil] = Reads(_ => JsSuccess(HNil))

      def product[F, T <: HList](name: String, FHead: Reads[F], FTail: Reads[T]) = Reads[F :#: T] {
        case obj @ JsObject(fields) =>
          obj \ name match {
            case JsDefined(node) =>
              for {
                head <- FHead.reads(node)
                tail <- FTail.reads(obj - name)
              } yield head :: tail
            case _ => JsError(s"Json attribute $name is undefined")
          }
        case _ => JsError("Json object required")
      }

      def project[F, G](instance: => Reads[G], to: F => G, from: G => F) = Reads[F](instance.map(from).reads)
    }
  }

  object SFormats extends LabelledProductTypeClassCompanion[Format]{
    object typeClass extends LabelledProductTypeClass[Format]{
      def emptyProduct: Format[HNil] = Format(
        SReads.typeClass.emptyProduct,
        SWrites.typeClass.emptyProduct
      )

      def product[F, T <: HList](name: String, FHead: Format[F], FTail: Format[T]) = Format[F :#: T] (
        SReads.typeClass.product[F, T](name, FHead, FTail),
        SWrites.typeClass.product[F, T](name, FHead, FTail)
      )

      def project[F, G](instance: => Format[G], to: F => G, from: G => F) = Format[F](
        SReads.typeClass.project(instance, to, from),
        SWrites.typeClass.project(instance, to, from)
      )
    }
  }

}
