package dev.pandoras.lib.minecraft.event

import dev.pandoras.lib.json.IToPandoraJson
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.identifier.ServerIdentifier
import java.util.*

class SenderData internal constructor(
    private val player: UUID? = null,
    private val server: ServerIdentifier? = null,
): IToPandoraJson {

    fun hasPlayer(): Boolean {
        return player != null
    }

    fun hasServer(): Boolean {
        return server != null
    }

    fun isAnonymous(): Boolean {
        return !hasPlayer() && !hasServer()
    }

    fun getPlayer(): UUID {
        return player!!
    }

    fun getServer(): ServerIdentifier {
        return server!!
    }

    override fun toJson(): JsonElement {
        return toJsonObject()
    }

    override fun toJsonObject(): JsonObject {
        val r = JsonObject()
        if (hasPlayer()) r.add("player", player!!.toString())
        if (hasServer()) r.add("server", server!!)
        return r;
    }

    companion object {
        val ANONYMOUS = SenderData()

        fun create(player: UUID? = null, server: ServerIdentifier? = null): SenderData {
            require(player != null || server != null) {
                "SenderData must have either a player or a server"
            }
            return SenderData(player, server)
        }

        fun fromJson(json: JsonElement): SenderData {
            require(json is JsonObject) { "SenderData must be a JsonObject" }
            val player = json.getOrNull("player")?.asJsonPrimitive()?.let { UUID.fromString(it.getAsString()) }
            val server = json.getOrNull("server")?.let { ServerIdentifier.fromJson(it) }
            return SenderData(player, server)
        }
    }


}