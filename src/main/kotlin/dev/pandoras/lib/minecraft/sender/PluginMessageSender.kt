package dev.pandoras.lib.minecraft.sender

import dev.pandoras.lib.minecraft.event.BaseEvent

abstract class PluginMessageSender {

    abstract fun sendPluginMessage(event: BaseEvent)
}