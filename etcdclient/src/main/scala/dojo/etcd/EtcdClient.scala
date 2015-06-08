package dojo.etcd

import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import play.api.libs.json.Json

trait EtcdClient {
  def get(key: String): EtcdResponse
  def ls(key: String): EtcdResponse //, recursive: Boolean = false
//  def set(key: String, value: String): EtcdResponse
//  def mkdir(key: String): EtcdResponse

}

class EtcdClientImpl(urlBase: String) extends EtcdClient {

  def get(key: String): EtcdResponse = {
    val requestConfig = RequestConfig.custom().build()
//      .setConnectTimeout(timeout.toMillis.toInt)
//      .setConnectionRequestTimeout(timeout.toMillis.toInt)
//      .setSocketTimeout(timeout.toMillis.toInt).build()
    val client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()
    closeFinally(client){ httpClient =>
      closeFinally(httpClient.execute(new HttpGet(urlBase + key)).getEntity.getContent){ inputStream =>
        Json.parse(io.Source.fromInputStream(inputStream).getLines().mkString).as[EtcdResponse]
      }
    }
  }

  def ls(key: String): EtcdResponse = get(key)

  private def closeFinally[A <: {def close(): Unit}, B](closable: A)(processClosable: (A) => B): B = {
    try{
     processClosable(closable)
    }finally {
      closable.close()
    }
  }

}

