package dev.pandoras.lib.minecraft.listener.paper

import com.google.common.io.ByteStreams
import dev.pandoras.lib.json.imp.JsonObject
import dev.pandoras.lib.minecraft.event.EventParser
import dev.pandoras.lib.minecraft.listener.PluginMessageListener
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener as BukkitPluginMessageListener

class PaperPluginMessageListener(val listenToPrefix: String = "pandoras") : PluginMessageListener(), BukkitPluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (!channel.startsWith(listenToPrefix)) return
        val json = JsonObject.fromJsonString(
            ByteStreams
                .newDataInput(message)
                .readUTF()
        )
        eventTrigger(channel, EventParser.parseEvent(json))
    }
}