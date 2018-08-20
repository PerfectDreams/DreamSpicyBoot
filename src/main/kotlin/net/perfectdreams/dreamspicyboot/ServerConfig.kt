package net.perfectdreams.dreamspicyboot

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import net.perfectdreams.dreamspicyboot.plugins.ServerPlugin

class ServerConfig(
		@JsonProperty("server-name")
		val serverName: String,
		@JsonProperty("platform-type")
		val platformType: PlatformType,
		@JsonProperty("minecraft-version")
		val serverVersion: ServerVersion,
		@JsonProperty("auto-update")
		val autoUpdate: Boolean,
		@JsonProperty("delete-plugins-on-boot")
		val deletePluginsOnBoot: Boolean,
		@JsonProperty("jrebel")
		val jrebel: JRebelConfig,
		@JsonProperty("flags")
		val flags: String,
		@JsonProperty("plugins")
		var plugins: MutableList<ServerPlugin>
)

class JRebelConfig @JsonCreator constructor(
		@JsonProperty("enabled")
		val enabled: Boolean,
		@JsonProperty("port")
		val port: Int
)