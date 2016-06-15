package services

import models.Vocabulary
import javax.inject.Singleton

import play.api.i18n.Lang

import scala.util.Random

@Singleton
class VocabularyService {

  private var allVocabulary = List(
    Vocabulary(Lang("en"), Lang("fr"), "hello", "bonjour"),
    Vocabulary(Lang("en"), Lang("fr"), "play", "jouer")
  )

  def addVocabulary(vocabulary: Vocabulary): Boolean = {
    if (!allVocabulary.contains(vocabulary)) {
      allVocabulary = vocabulary :: allVocabulary
      true
    } else {
      false
    }
  }

  def findRandomVocabolary(sourceLanguage: Lang, targetLanguage: Lang): Option[Vocabulary] = {
    Random.shuffle(allVocabulary.filter { v =>
      v.sourceLanguage == sourceLanguage && v.targetLanguage == targetLanguage
    }).headOption
  }

  def verify(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String): Boolean = {
    allVocabulary.contains(Vocabulary(sourceLanguage, targetLanguage, word, translation))
  }

}