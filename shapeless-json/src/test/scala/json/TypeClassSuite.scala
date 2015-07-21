package json

import org.scalatest.FunSuite

import play.api.libs.json._

case class PetType(description : String)
case class Pet(name: Option[String], price: Long, petType: PetType)

class TypeClassSuite extends FunSuite{
  test("toJson"){
    implicit def OptionWrites[T](implicit fmt: Writes[T]): Writes[Option[T]] = new Writes[Option[T]] {
      def writes(o: Option[T]) = o match {
        case Some(value) => fmt.writes(value)
        case None => JsNull
      }
    }
    implicit def petTypeWrites: Writes[PetType] = SWrites.deriveInstance
    implicit def petWrites: Writes[Pet] = SWrites.deriveInstance

    val petType = PetType("cat")
    val pet = Pet(Some("whiskas"), 1000L, petType)
    val pet2 = Pet(None, 1000L, petType)
    assert("""{"name":"whiskas","price":1000,"petType":{"description":"cat"}}""" == Json.stringify(Json.toJson(pet)))
    assert("""{"price":1000,"petType":{"description":"cat"}}""" == Json.stringify(Json.toJson(pet2)))
  }
}
