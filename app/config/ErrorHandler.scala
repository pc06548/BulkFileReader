package config

import javax.inject._

import org.slf4j.LoggerFactory
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.routing.Router

import scala.concurrent._

@Singleton
class ErrorHandler @Inject() (
                               env: Environment,
                               config: Configuration,
                               sourceMapper: OptionalSourceMapper,
                               router: Provider[Router]
                               ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  override def onServerError(request : play.api.mvc.RequestHeader, exception : scala.Throwable) = {
    exception.getStackTrace.map(stackTraceElement => logger.error(stackTraceElement.toString))
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }
}