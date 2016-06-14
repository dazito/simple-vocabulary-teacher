package binders

import play.api.i18n.Lang
import play.api.mvc.PathBindable

/**
 * Created by daz on 14/06/2016.
 */
/**
 * Placing all binders in one object in order to simplify importing them into the router
 */
object PathBinders {

  // Declaring the PathBindable as implicit object so as to be resolved implicitly by the router
  implicit object LangPathBindable extends PathBindable[Lang] {
    // Implementing the bind method to read a query fragment as a type
    override def bind(key: String, value: String): Either[String, Lang] = {
      // Encoding the result of a binding as Either[String, Lang], which means that the
      // result of a binding is either an error message or the successfully read Lang value

      // Checking if there is a language for the input value and otherwise return an error message
      Lang.get(value).toRight(s"Language $value is not recognized")
    }

    // Implementing the unbind method in order to write a type as path fragment
    override def unbind(key: String, value: Lang): String = value.code
  }

}
