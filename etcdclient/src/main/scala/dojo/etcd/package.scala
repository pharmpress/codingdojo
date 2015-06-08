package dojo

import _root_.play.api.libs.functional.syntax._
import _root_.play.api.libs.json.Format
import _root_.play.api.libs.json.Json
import _root_.play.api.libs.json.Reads
import _root_.play.api.libs.json.Writes
import _root_.play.api.libs.json._

package object etcd {

  case class NodeResponse(key: String,
                          dir: Option[Boolean],
                          value: Option[String],
                          nodes: Option[List[NodeResponse]])

  case class EtcdResponse(action: String,
                          node: NodeResponse,
                          prevNode: Option[NodeResponse])


  val nodeResponseReads: Reads[NodeResponse] = (
    (__ \ "key").read[String] and
      (__ \ "dir").readNullable[Boolean] and
      (__ \ "value").readNullable[String] and
      (__ \ "nodes").lazyReadNullable(Reads.list(nodeResponseReads))
    )(NodeResponse)

  val nodeResponseWrites: Writes[NodeResponse] = (
    (__ \ "key").write[String] and
      (__ \ "dir").writeNullable[Boolean] and
      (__ \ "value").writeNullable[String] and
      (__ \ "nodes").lazyWriteNullable(Writes.list(nodeResponseWrites))
    )(unlift(NodeResponse.unapply))

  implicit val nodeResponseFormat = Format(nodeResponseReads, nodeResponseWrites)

  implicit val etcdResponseFormat = Json.format[EtcdResponse]
}
