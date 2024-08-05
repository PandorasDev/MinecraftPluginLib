package dev.pandoras.lib.minecraft.identifier

import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.utils.asLambda

class EventIdentifier(
    namespace: String,
    path: String
): Identifier(
    namespace,
    path
) {

    init {
        require(namespace.startsWith("event")) {
            "EventIdentifier Namespace must start with 'event'"
        }
    }

    companion object {
        fun fromJson(json: JsonElement): EventIdentifier {
            require(json.isJsonObject(), "EventIdentifier must be a JsonObject".asLambda())
            val jsonObject = json.getAsJsonObject()
            return EventIdentifier(
                jsonObject.get("namespace").getAsJsonPrimitive().getAsString(),
                jsonObject.get("path").getAsJsonPrimitive().getAsString()
            )
        }
    }
}