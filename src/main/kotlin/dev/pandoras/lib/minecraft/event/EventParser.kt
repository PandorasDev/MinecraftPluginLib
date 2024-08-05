package dev.pandoras.lib.minecraft.event

import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.event.imp.JsonRespondEvent
import dev.pandoras.lib.minecraft.identifier.EventIdentifier

class EventParser private constructor() {

    companion object {

        init {
            registerEvent(EventIdentifier("pandoras", "json_response")) { json ->
                JsonRespondEvent.fromJson(json)
            }
        }


        private val eventMapper: MutableMap<EventIdentifier, (JsonObject) -> BaseEvent> = mutableMapOf()

        fun registerEvent(eventIdentifier: EventIdentifier, eventParser: (JsonObject) -> BaseEvent) {
            eventMapper[eventIdentifier] = eventParser
        }

        fun parseEvent(json: JsonObject): BaseEvent {
            val eventIdentifier = EventIdentifier.fromJson(json.get("eventIdentifier"))
            val eventParser = eventMapper[eventIdentifier] ?: throw IllegalArgumentException("EventIdentifier not found: $eventIdentifier")
            return eventParser.invoke(json)
        }
    }
}