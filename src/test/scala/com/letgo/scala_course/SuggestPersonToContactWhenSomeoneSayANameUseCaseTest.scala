package com.letgo.scala_course

import scala.concurrent.duration._

import akka.actor.ActorSystem
import org.joda.time.DateTime
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.Matchers._
import org.scalatest.WordSpec

import com.letgo.scala_course.application.{SlackMessageAdderUseCase, SuggestPersonToContactWhenSomeoneSayANameUseCase}
import com.letgo.scala_course.domain.{Message, MessageText, SlackClient}
import com.letgo.scala_course.infrastructure.GilbertSlackClient
import com.letgo.scala_course.infrastructure.stub.{ChannelIdStub, MessageStub}

final class SuggestPersonToContactWhenSomeoneSayANameUseCaseTest extends WordSpec with ScalaFutures with Eventually {

  implicit val patience: PatienceConfig = PatienceConfig(timeout = 10000.milliseconds, interval = 10.milliseconds)

  "suggestPersonToContactWhenSomeoneSayAName" should {
    "suggest the person when someone say a name" in {
      implicit val actorSystem = ActorSystem()
      implicit val ec          = scala.concurrent.ExecutionContext.global

      val client: SlackClient                       = new GilbertSlackClient()
      val channelId                                 = ChannelIdStub.scalaCourse
      val suggestPersonToContactWhenSomeoneSayAName = new SuggestPersonToContactWhenSomeoneSayANameUseCase(client)
      val suggestedText = "acho guillem"

      val slackMessageAdderUseCase = new SlackMessageAdderUseCase(client)

      slackMessageAdderUseCase
        .add(channelId, MessageStub.closedChatPullRequest)
        .futureValue

      val from = DateTime.now.minusYears(10) // From the beginning of time

      client.addMessage(channelId, Message(MessageText("acho joseluis"), None))

      suggestPersonToContactWhenSomeoneSayAName.suggestSpecificPersonToContactWhenSomeoneSayAName("joseluis",
        suggestedText, from, channelId)

      eventually {
        client
          .fetchLatestChannelMessages(channelId, Some(from), None)
          .futureValue should contain(suggestedText)
      }
    }
  }
}
