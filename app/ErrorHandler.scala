import javax.inject._
import play.api.http.DefaultHttpErrorHandler
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.routing.Router
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Created by daz on 14/06/2016.
 */
class ErrorHandler @Inject() (env: Environment, config: Configuration, sourceMapper: OptionalSourceMapper, router: Provider[Router])
    extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override protected def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future {
      NotFound("Could not find " + request)
    }
  }

  override protected def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    Future {
      Forbidden("You do not belong here")
    }
  }
}
