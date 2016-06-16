package controllers

import javax.inject.Inject

import actors.QuizzActor
import play.api.Play.current
import play.api.i18n.Lang
import play.api.mvc.{ Action, Controller, WebSocket }
import services.VocabularyService

class Quizz @Inject() (vocabularyService: VocabularyService) extends Controller {

  def quizz(sourceLanguage: Lang, targetLanguage: Lang) = Action {
    vocabularyService.findRandomVocabolary(sourceLanguage, targetLanguage).map {
      vocabulary => Ok(vocabulary.word)
    } getOrElse {
      NotFound
    }
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = Action {
    if (vocabularyService.verify(sourceLanguage, word, targetLanguage, translation)) {
      Ok
    } else
      NotAcceptable
  }

  def quizzEndpoint(sourceLang: Lang, targetLang: Lang) = WebSocket.acceptWithActor[String, String] { request =>
    out =>
      QuizzActor.props(sourceLang, targetLang, out, vocabularyService)
  }

}