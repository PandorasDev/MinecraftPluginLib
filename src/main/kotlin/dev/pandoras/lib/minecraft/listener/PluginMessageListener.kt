package dev.pandoras.lib.minecraft.listener

import dev.pandoras.lib.minecraft.event.BaseEvent

abstract class PluginMessageListener {

    protected val channelCallbacks: MutableMap<String, MutableList<(BaseEvent) -> Unit>> = mutableMapOf()
    protected val eventCallbacks: MutableMap<Class<out BaseEvent>, MutableList<(BaseEvent) -> Unit>> = mutableMapOf()
    protected val allEventCallbacks: MutableList<(BaseEvent) -> Unit> = mutableListOf()

    protected fun <E : BaseEvent> eventTrigger(channel: String, event: E) {
        allEventCallbacks.forEach { it(event) }

        channelCallbacks[channel]?.forEach { it(event) }

        val eventClass = event::class.java as Class<out BaseEvent>
        eventCallbacks[eventClass]?.forEach { it(event) }
    }

    fun <E : BaseEvent> registerChannelCallback(channel: String, callback: (E) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        channelCallbacks.getOrPut(channel, ::mutableListOf).add(callback as (BaseEvent) -> Unit)
    }

    fun <E : BaseEvent> registerEventCallback(eventClass: Class<E>, callback: (E) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        eventCallbacks.getOrPut(eventClass, ::mutableListOf).add(callback as (BaseEvent) -> Unit)
    }

    fun <E : BaseEvent> unregisterChannelCallback(channel: String, callback: (E) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        channelCallbacks[channel]?.remove(callback as (BaseEvent) -> Unit)
    }

    fun <E : BaseEvent> unregisterEventCallback(eventClass: Class<E>, callback: (E) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        eventCallbacks[eventClass]?.remove(callback as (BaseEvent) -> Unit)
    }

    fun registerListener(callback: (BaseEvent) -> Unit) {
        allEventCallbacks.add(callback)
    }

    fun unregisterListener(callback: (BaseEvent) -> Unit) {
        allEventCallbacks.remove(callback)
    }
}