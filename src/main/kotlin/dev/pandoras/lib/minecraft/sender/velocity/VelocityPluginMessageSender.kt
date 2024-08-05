package dev.pandoras.lib.minecraft.sender.velocity

import com.google.common.io.ByteStreams
import com.velocitypowered.api.proxy.ProxyServer
import dev.pandoras.lib.minecraft.event.BaseEvent
import dev.pandoras.lib.minecraft.sender.PluginMessageSender

class VelocityPluginMessageSender(private val proxyServer: ProxyServer): PluginMessageSender() {

    override fun sendPluginMessage(event: BaseEvent) {
        val channel = event.getChannelIdentifier()

        var servers = proxyServer.allServers.stream()
        if (channel.path != "all") servers = servers.filter { it.serverInfo.name.startsWith(channel.path) }

        if (servers.count() == 0L) return

        val message = ByteStreams.newDataOutput()
        message.writeUTF(event.toJson().toJsonString())
        servers.forEach {
            it.sendPluginMessage(channel, message.toByteArray())
        }
    }
}