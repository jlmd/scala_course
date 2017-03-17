package com.letgo.scala_course.domain.service

import com.letgo.scala_course.domain.{Message, UserMessage, UserName}

class MessageAnalyticsService {

  def countMessagesOfUser(messages: Seq[UserMessage], userName: UserName): Int = {
    messages.count(_.userName == userName)
  }

  def groupByUserName(messages: Seq[UserMessage]): Map[UserName, Seq[Message]] = {
    val usernameMessagesMap = scala.collection.mutable.Map[UserName, Seq[Message]]()

    messages.foreach((message) => {
      val messagesSeq = usernameMessagesMap.getOrElse(message.userName, {
        Seq()
      }):+message.message
      usernameMessagesMap(message.userName) = messagesSeq
    })

    usernameMessagesMap.toMap
  }


  def groupByUserName2(messages: Seq[UserMessage]): Map[UserName, Seq[Message]] = {
    var usernameMessagesMap = Map[UserName, Seq[Message]]()

    messages.foreach((message) => {
      val messagesSeq = usernameMessagesMap.getOrElse(message.userName, {
        Seq()
      }):+message.message
      usernameMessagesMap = usernameMessagesMap + (message.userName -> messagesSeq)
    })

    usernameMessagesMap
  }
}
NetworkOnMainThreadException
