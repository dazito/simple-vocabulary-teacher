package controllers

import play.api.i18n.Lang
import play.api.mvc.Controller

class Import extends Controller {
  def importWord(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = TODO
}