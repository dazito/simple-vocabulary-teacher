package actors

import akka.actor.Actor.Receive
import akka.actor.{ Actor, ActorRef, Props }
import play.api.i18n.Lang
import services.VocabularyService

/**
 * Created by daz on 16/06/2016.
 */

// out is an actor reference for the output channel - websocket
class QuizzActor(out: ActorRef, sourceLang: Lang, targetLang: Lang, vocabularyService: VocabularyService) extends Actor {

  // Keep track of which word we’re currently asking a translation of
  private var word = ""

  // When starting up, send a new word to translate
  override def preStart(): Unit = sendWord()

  def receive = {
    case translation: String // If a correct translation was provided, ask for a new word
    if vocabularyService.verify(sourceLang, word, targetLang, translation) =>
      out ! "Correct"
      sendWord()

    case _ =>
      out ! "Incorrect, try again"
  }

  def sendWord() = {
    vocabularyService.findRandomVocabolary(sourceLang, targetLang).map { v =>
      out ! s"Please translate '${v.word}'"

      // Set the requested word so we know what to check against
      word = v.word
    } getOrElse {
      out ! s"I don't know any word for ${sourceLang.code} and ${targetLang.code}"
    }
  }
}

object QuizzActor {

  def props(sourceLang: Lang, targetLang: Lang, out: ActorRef, vocabularyService: VocabularyService): Props =
    Props(classOf[QuizzActor], out, sourceLang, targetLang, vocabularyService)
}