package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.Message

class MessageCensor(forbiddenKeywords: Set[String]) {

  //  private val filterRuleJavero: Message => Message = message => {
  //
  //    var newMessage = ""
  //    val splitMessage = message.text.split(" ")
  //
  //    for (word <- message.text.split(" ")) {
  //      if (!forbiddenKeywords.contains(word)) {
  //        if (splitMessage.indexOf(word) != 0) {
  //          newMessage = newMessage + " "
  //        }
  //        newMessage = newMessage + word
  //      }
  //    }
  //    Message(newMessage)
  //  }
  //

//      private val filterRuleSomeFunctional: Message => Message = message => {
//
//       Message(message.text.split(" ").map(word => {
//         if (!forbiddenKeywords.contains(word)) {
//           word
//         } else {
//           ""
//         }
//         }).mkString(" ").trim.replaceAll("  ", " "))
//      }

  private val filterRule: Message => Message = message => {

    Message(
      message.text.split(" ").filterNot(
        word => forbiddenKeywords.contains(word)
      ).mkString(" ")
    )
  }

  def filterMessages(messages: Seq[Message]): Seq[Message] = messages.map(filterRule)
}

