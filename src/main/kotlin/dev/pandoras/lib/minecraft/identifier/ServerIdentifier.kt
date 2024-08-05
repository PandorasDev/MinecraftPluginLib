package dev.pandoras.lib.minecraft.identifier

import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonObject

class ServerIdentifier(
    namespace: String,
    path: String
): Identifier(
    namespace,
    path
) {
    init {
        require(namespace.startsWith("server")) {
            "ServerIdentifier Namespace must start with 'server'"
        }
    }

    companion object {
        fun fromJson(json: JsonElement): ServerIdentifier {
            require(json.isJsonObject()) { "ServerIdentifier must be a JsonObject" }
            json as JsonObject
            val namespace = json.get("namespace").asJsonPrimitive().asString()
            val path = json.get("path").asJsonPrimitive().asString()
            return ServerIdentifier(namespace, path)
        }
    }

}