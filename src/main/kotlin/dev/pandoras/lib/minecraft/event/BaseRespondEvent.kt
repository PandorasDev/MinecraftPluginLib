package dev.pandoras.lib.minecraft.event

import com.google.common.io.ByteStreams
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.minecraft.identifier.EventIdentifier
import dev.pandoras.lib.minecraft.identifier.MessageIdentifier
import dev.pandoras.lib.minecraft.identifier.PandoraChannelIdentifier

abstract class BaseRespondEvent<R>(
    private val channelIdentifier: PandoraChannelIdentifier,
    private val eventIdentifier: EventIdentifier,
    private val messageIdentifier: MessageIdentifier,
    private val fromSender: SenderData,
    private val toSender: SenderData,
    private val eventData: EventData
): BaseEvent() {

    init {
        require(messageIdentifier.notEmpty()) {
            "MessageIdentifier must not be empty"
        }
    }

    override fun getChannelIdentifier(): PandoraChannelIdentifier {
        return channelIdentifier
    }

    override fun getEventIdentifier(): EventIdentifier {
        return eventIdentifier
    }

    override fun getMessageIdentifier(): MessageIdentifier {
        return messageIdentifier
    }

    override fun getFromSender(): SenderData {
        return fromSender
    }

    override fun getToSender(): SenderData {
        return toSender
    }

    override fun getEventData(): EventData {
        return eventData
    }

    fun getResponseJson(): JsonElement {
        return JsonElement.fromJsonString(ByteStreams.newDataInput(eventData.data).readUTF())
    }

    abstract fun getResponse(): R
}