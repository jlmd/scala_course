package com.letgo.scala_course.application

import scala.concurrent.ExecutionContext

import org.joda.time.DateTime

import com.letgo.scala_course.domain._

class SuggestPersonToContactWhenSomeoneSayANameUseCase(slackClient: SlackClient)(implicit ec: ExecutionContext) {

  def suggestSpecificPersonToContactWhenSomeoneSayAName(userName: String, personToContact: String, from: DateTime,
                                                        channelId: ChannelId): Unit = {
    slackClient.fetchLatestChannelMessages(channelId, Some(from)).map { authoredMessages =>
      authoredMessages.foreach { authoredMessage =>
        if (authoredMessage.message.containsText(userName)) {
          slackClient.addMessage(channelId, Message(MessageText(personToContact), None))
        }
      }
    }
  }
}
