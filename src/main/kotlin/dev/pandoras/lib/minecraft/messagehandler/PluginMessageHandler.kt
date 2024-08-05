package dev.pandoras.lib.minecraft.messagehandler

import dev.pandoras.lib.minecraft.event.BaseEvent
import dev.pandoras.lib.minecraft.event.BaseRespondEvent
import dev.pandoras.lib.minecraft.identifier.MessageIdentifier
import dev.pandoras.lib.minecraft.listener.PluginMessageListener
import dev.pandoras.lib.minecraft.sender.PluginMessageSender
import dev.pandoras.lib.promise.Future
import dev.pandoras.lib.promise.Promise
import dev.pandoras.lib.utils.asLambda

class PluginMessageHandler(
    val sender: PluginMessageSender,
    val listener: PluginMessageListener
) {
    private val responses: MutableMap<MessageIdentifier, Promise<BaseRespondEvent<*>>> = mutableMapOf()

    init {
        listener.registerListener(this::handlePluginMessage.asLambda())
    }

    fun sendPluginMessageWithResponse(event: BaseEvent): Future<BaseRespondEvent<*>> {
        sender.sendPluginMessage(event)
        val promise = Promise<BaseRespondEvent<*>>()
        responses[event.getMessageIdentifier()] = promise
        return promise.future()
    }

    fun handlePluginMessage(event: BaseEvent) {
        if (event !is BaseRespondEvent<*>) return
        responses.remove(event.getMessageIdentifier())?.resolve(event)
    }

    fun clearResponseListeners() {
        responses.clear()
    }


}