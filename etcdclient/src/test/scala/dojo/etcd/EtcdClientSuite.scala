package dojo.etcd

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}
import play.api.libs.json.Json

class EtcdClientSuite extends FunSuite with BeforeAndAfterAll with MockFactory{
  //System.setProperty("com.sun.net.httpserver.HttpServerProvider","org.eclipse.jetty.http.spi.JettyHttpServerProvider");

  val portNumber = 8888
  val server = HttpServer.create(new InetSocketAddress(portNumber), 0)
  val executor = Executors.newCachedThreadPool()
  val stopTime = 200

  test("get"){
    val client = new EtcdClientImpl(s"http://127.0.0.1:$portNumber/v2/keys")
    val actual = client.get("/domains/elasticsearch/servers/srv1")
    assert(actual.node.value.get == "host1:9091")
  }

  test("ls"){
    val client = new EtcdClientImpl(s"http://127.0.0.1:$portNumber/v2/keys")
    val actual = client.get("/domains/elasticsearch/servers")
    assert(actual.node.nodes.get.size == 2)
  }

  override def beforeAll = {
    val responseLsString = Json.stringify(Json.toJson(EtcdResponse(
      action = "get",
      node = NodeResponse(
        key = "/domains/elasticsearch/servers",
        dir = Some(true),
        value = None,
        nodes = Some(List(
          NodeResponse(
            key = "/domains/elasticsearch/servers/srv2",
            dir = None,
            value = Some("host2:9092"),
            nodes = None),
          NodeResponse(
            key = "/domains/elasticsearch/servers/srv1",
            dir = None,
            value = Some("host1:9091"),
            nodes = None)
        ))),
      prevNode = None)))

    val responseGetString = Json.stringify(Json.toJson(EtcdResponse(
      action = "get",
      node = NodeResponse(
        key = "/domains/elasticsearch/servers/srv1",
        dir = None,
        value = Some("host1:9091"),
        nodes = None),
      prevNode = None)))

    server.createContext("/v2/keys/domains/elasticsearch/servers", new HttpHandlerForTest(200, responseLsString))
    server.createContext("/v2/keys/domains/elasticsearch/servers/srv1", new HttpHandlerForTest(200, responseGetString))
    server.setExecutor(executor) // creates a default executor
    server.start()
  }

   override def afterAll ={
     server.stop(stopTime)
     if(!executor.isShutdown){
       executor.shutdownNow()
     }
  }


  class HttpHandlerForTest(responseCode: Int, content: String = "", sleepTime: Long = 0) extends HttpHandler {
    override def handle(httpExchange: HttpExchange): Unit = {
      httpExchange.sendResponseHeaders(responseCode, content.length())
      val os = httpExchange.getResponseBody
      try{
        if(sleepTime > 0){
          Thread.sleep(sleepTime)
        }
        if(content.nonEmpty){
          os.write(content.getBytes)
        }
      } finally {
        os.close()
      }
    }
  }
}
