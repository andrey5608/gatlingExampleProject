import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GatlingTest extends Simulation {
  private val baseUrl = "https://postman-echo.com"
  private val contentType = "text/plain"
  private val endpoint = "/get?foo1=bar1&foo2=bar2"
  private val usersCount = 10


  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .inferHtmlResources()
    .acceptHeader("*/*")
    .contentTypeHeader(contentType)
    .userAgentHeader("curl/7.54.0")
    .disableWarmUp
    .disableCaching

  val headers_0 = Map("Expect" -> "100-continue")

  val scn: ScenarioBuilder = scenario("RecordedSimulation")
    .repeat(10)
    {
      exec(http("request_0")
      .get(endpoint)
      .headers(headers_0)
      .check(status.is(200)))
    }

  setUp(scn.inject(atOnceUsers(usersCount))).protocols(httpProtocol)
}