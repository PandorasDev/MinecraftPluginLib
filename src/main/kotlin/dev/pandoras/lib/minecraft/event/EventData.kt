package dev.pandoras.lib.minecraft.event

import dev.pandoras.lib.json.IToPandoraJson
import dev.pandoras.lib.json.JsonElement
import dev.pandoras.lib.json.imp.JsonBytes

class EventData(val data: ByteArray): IToPandoraJson {

    override fun toJson(): JsonElement {
        return toJsonPrimitive()
    }

    override fun toJsonBytes(): JsonBytes {
        return JsonBytes(data)
    }

    companion object {
        fun fromJson(json: JsonElement): EventData {
            return EventData(json.asJsonBytes().get())
        }
    }

}