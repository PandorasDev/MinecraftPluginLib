package dev.pandoras.lib.minecraft.event

import dev.pandoras.lib.json.IToPandoraJson
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.identifier.EventIdentifier
import dev.pandoras.lib.minecraft.identifier.MessageIdentifier
import dev.pandoras.lib.minecraft.identifier.PandoraChannelIdentifier


abstract class BaseEvent : IToPandoraJson {

    protected var eventCancelled: Boolean = false

    abstract fun getChannelIdentifier(): PandoraChannelIdentifier

    abstract fun getEventIdentifier(): EventIdentifier

    abstract fun getMessageIdentifier(): MessageIdentifier

    abstract fun getFromSender(): SenderData

    abstract fun getToSender(): SenderData

    abstract fun getEventData(): EventData

    open fun isCancelled(): Boolean {
        return eventCancelled
    }

    open fun setCancelled(cancel: Boolean) {
        eventCancelled = cancel
    }

    override fun toJson(): JsonElement {
        return toJsonObject()
    }

    override fun toJsonObject(): JsonObject {
        return JsonObject()
            .add("class", this::class.qualifiedName)
            .add("channelIdentifier", getChannelIdentifier().toJson())
            .add("eventIdentifier", getEventIdentifier().toJson())
            .add("messageIdentifier", getMessageIdentifier().toJson())
            .add("fromSender", getFromSender().toJson())
            .add("toSender", getToSender().toJson())
            .add("eventData", getEventData().toJson())
    }
}