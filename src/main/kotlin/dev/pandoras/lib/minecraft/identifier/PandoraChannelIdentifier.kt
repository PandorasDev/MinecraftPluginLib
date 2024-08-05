package dev.pandoras.lib.minecraft.identifier

import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.utils.asLambda

class PandoraChannelIdentifier(
    namespace: String,
    path: String
): Identifier(
    namespace,
    path
), ChannelIdentifier {

    companion object {
        fun fromJson(json: JsonElement): PandoraChannelIdentifier {
            require(json.isJsonObject(), "PandoraChannelIdentifier must be a JsonObject".asLambda())
            val jsonObject = json.getAsJsonObject()
            return PandoraChannelIdentifier(
                jsonObject.get("namespace").getAsJsonPrimitive().getAsString(),
                jsonObject.get("path").getAsJsonPrimitive().getAsString()
            )
        }
    }

    override fun getId(): String {
        return "$namespace:$path"
    }
}