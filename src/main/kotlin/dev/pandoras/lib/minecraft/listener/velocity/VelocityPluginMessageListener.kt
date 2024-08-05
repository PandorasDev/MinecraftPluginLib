package dev.pandoras.lib.minecraft.listener.velocity

import com.google.common.io.ByteStreams
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.event.EventParser
import dev.pandoras.lib.minecraft.listener.PluginMessageListener

class VelocityPluginMessageListener(val listenToPrefix: String = "pandoras"): PluginMessageListener() {
    @Subscribe
    fun onPluginMessage(event: PluginMessageEvent) {
        if (event.identifier.id.startsWith(listenToPrefix)) {
            val json = JsonObject.fromJsonString(
                ByteStreams
                    .newDataInput(event.data)
                    .readUTF()
            )
            eventTrigger(event.identifier.id, EventParser.parseEvent(json))
        }
    }
}