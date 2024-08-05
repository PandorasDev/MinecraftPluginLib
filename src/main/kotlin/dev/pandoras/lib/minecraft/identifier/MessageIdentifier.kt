package dev.pandoras.lib.minecraft.identifier

import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.utils.asLambda

class MessageIdentifier(
    namespace: String,
    path: String
): Identifier(
    namespace,
    path
) {

    init {
        require(namespace.startsWith("msg")) {
            "MessageIdentifier Namespace must start with 'msg'"
        }
    }

    companion object {

        val EMPTY = MessageIdentifier("msg", "")

        fun fromJson(json: JsonElement): MessageIdentifier {
            require(json.isJsonObject(), "MessageIdentifier must be a JsonObject".asLambda())
            val jsonObject = json.getAsJsonObject()
            return MessageIdentifier(
                jsonObject.get("namespace").getAsJsonPrimitive().getAsString(),
                jsonObject.get("path").getAsJsonPrimitive().getAsString()
            )
        }
    }
}