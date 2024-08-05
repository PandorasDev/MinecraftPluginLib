package dev.pandoras.lib.minecraft.identifier

import dev.pandoras.lib.json.IToPandoraJson
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonObject

abstract class Identifier(
    val namespace: String,
    val path: String
): IToPandoraJson {

    override fun toJson(): JsonElement {
        return toJsonObject()
    }

    override fun toJsonObject(): JsonObject {
        return JsonObject()
            .add("namespace", namespace)
            .add("path", path)
    }

    override fun toString(): String {
        return "$namespace:$path"
    }

    override fun hashCode(): Int = 31 * namespace.hashCode() + path.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Identifier) return false
        return namespace == other.namespace && path == other.path
    }

    fun isEmpty(): Boolean {
        return namespace.isEmpty() || path.isEmpty()
    }

    fun notEmpty(): Boolean {
        return !isEmpty()
    }
}