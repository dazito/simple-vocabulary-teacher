import javax.inject.Inject

import filters.ScoreFilter
import play.api.http.HttpFilters
import play.filters.gzip.GzipFilter
import play.filters.headers.SecurityHeadersFilter

/**
 * Created by daz on 18/06/2016.
 */
class Filters @Inject() (gzip: GzipFilter, scoreFilter: ScoreFilter) extends HttpFilters {

  /**
   * Specify the filters to apply and their order
   * Play’s SecurityHeadersFilter adds a number of header-based security
   * checks and policies
   */
  val filters = Seq(gzip, SecurityHeadersFilter(), scoreFilter)

}
