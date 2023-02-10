package io.scala.views

import io.scala.Lexicon
import io.scala.Page.*
import io.scala.domaines.{Presentation, Speaker, Talk}
import io.scala.modules.{ClassyButton, Line, SpeakerCard, SpeakerModal, Title}
import io.scala.views.Speakers.speaker
import io.scala.views.View

import com.raquo.laminar.api.L.{*, given}

case object Speakers extends View {
  private val selectedSpeaker: Var[Option[Speaker]] = Var(None)

  val speaker = Speaker(
    name = "John Doe",
    photo = None,
    presentation = Presentation.Keynote,
    job = "Data Engineer",
    company = "Scala.IO",
    socials = List.empty,
    talk = Talk(
      name = "Scala is a good language",
      description = """Scala is considered an incredible language
          |because it is a highly expressive and concise
          |programming language that combines functional
          |and object-oriented programming paradigms. It
          |has built-in support for concurrency, making it
          |easier to write parallel and asynchronous code,
          |and it is fully interoperable with Java. These
          |features, along with its powerful type system
          |and functional programming features, make Scala
          |a popular choice for building large-scale, complex
          |systems in a variety of domains.""".stripMargin.replace("\n", " ")
    )
  )

  override def body: HtmlElement = div(
    Title("Speakers"),
    p(
      Lexicon.Speakers.catchPhrase,
      className := "speakers__catch-phrase"
    ),
    ClassyButton(Lexicon.Speakers.callToAction),
    Line(padding = 55),
    div(
      SpeakerCard(speaker),
      onClick.mapTo(Some(speaker)) --> selectedSpeaker.writer
    ),
    child <-- selectedSpeaker.signal.map {
      case None          => emptyNode
      case Some(speaker) => SpeakerModal(speaker, selectedSpeaker)
    },
    className := "container"
  )
}
