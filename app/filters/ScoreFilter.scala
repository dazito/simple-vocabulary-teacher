package filters

import play.api.libs.iteratee.Enumerator
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.Future

/**
 * Created by daz on 18/06/2016.
 */
class ScoreFilter extends Filter {
  /**
   * nextFilter - A filter gets the representation of the next filter in the chain, which takes a request
   * header as input and returns a Future result as an output
   * rh - The request header of the current request
   */
  override def apply(nextFilter: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {

    // Apply the request header to the next filter to get the result of the operation
    val result = nextFilter(rh)

    import play.api.libs.concurrent.Execution.Implicits._
    result.map { result =>
      if (result.header.status == 200 || result.header.status == 406) {
        val correct = result.session(rh).get("correct").getOrElse(0)
        val wrong = result.session(rh).get("wrong").getOrElse(0)
        val score = s"Your current score is: $correct correct answers and $wrong wrong answers"
        val newBody = result.body.andThen(Enumerator(score.getBytes("UTF-8")))
        result.copy(body = newBody)
      } else {
        result
      }
    }
  }
}
