package com.letgo.scala_course.domain

case class Message(text: MessageText, actions: Option[Seq[MessageAction]]) {

  def containsText(textToCheck: String): Boolean = text.rawText.contains(textToCheck)
}
