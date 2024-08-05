package dev.pandoras.lib.minecraft.event.imp

import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.event.BaseRespondEvent
import dev.pandoras.lib.minecraft.event.EventData
import dev.pandoras.lib.minecraft.event.SenderData
import dev.pandoras.lib.minecraft.identifier.EventIdentifier
import dev.pandoras.lib.minecraft.identifier.MessageIdentifier
import dev.pandoras.lib.minecraft.identifier.PandoraChannelIdentifier

class JsonRespondEvent(
    channelIdentifier: PandoraChannelIdentifier,
    eventIdentifier: EventIdentifier,
    messageIdentifier: MessageIdentifier,
    fromSender: SenderData,
    toSender: SenderData, eventData: EventData
) : BaseRespondEvent<JsonElement>(
    channelIdentifier,
    eventIdentifier,
    messageIdentifier,
    fromSender,
    toSender,
    eventData
) {
    override fun getResponse(): JsonElement {
        return getResponseJson()
    }

    companion object {

        fun fromJson(json: JsonElement): JsonRespondEvent {
            require(json is JsonObject) { "JsonRespondEvent must be a JsonObject" }
            val channelIdentifier = PandoraChannelIdentifier.fromJson(json.get("channelIdentifier"))
            val eventIdentifier = EventIdentifier.fromJson(json.get("eventIdentifier"))
            val messageIdentifier = MessageIdentifier.fromJson(json.get("messageIdentifier"))
            val fromSender = SenderData.fromJson(json.get("fromSender"))
            val toSender = SenderData.fromJson(json.get("toSender"))
            val eventData = EventData.fromJson(json.get("eventData"))
            return JsonRespondEvent(
                channelIdentifier,
                eventIdentifier,
                messageIdentifier,
                fromSender,
                toSender,
                eventData
            )
        }
    }
}