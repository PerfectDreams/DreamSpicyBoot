package net.perfectdreams.dreamspicyboot

import com.github.ajalt.mordant.TermColors
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.jsoup.Jsoup
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern

object DreamSpicyBoot {
	val gson = Gson()
	val randomStartupTips = mutableListOf(
			"Yeah, I know... I gotta believe!",
			"Hmmmmm... Spicy Calamari!",
			"Sonhos que cabem no seu bolso!",
			"Transformando sonhos em realidade!",
			"A Lori e a Pantufa te amam! <3",
			"You are filled with DETERMINATION"
	)

	const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0"
	val random = SplittableRandom()
	val t = TermColors()
	val CIRCLECI_PATTERN = Pattern.compile("([0-9]+)-([0-9]+)")
	const val HEADER = """  _____                            _____       _            ____              _
 |  __ \                          / ____|     (_)          |  _ \            | |
 | |  | |_ __ ___  __ _ _ __ ___ | (___  _ __  _  ___ _   _| |_) | ___   ___ | |_
 | |  | | '__/ _ \/ _` | '_ ` _ \ \___ \| '_ \| |/ __| | | |  _ < / _ \ / _ \| __|
 | |__| | | |  __/ (_| | | | | | |____) | |_) | | (__| |_| | |_) | (_) | (_) | |_
 |_____/|_|  \___|\__,_|_| |_| |_|_____/| .__/|_|\___|\__, |____/ \___/ \___/ \__|
                                        | |            __/ |
                                        |_|           |___/                      """

	@JvmStatic
	fun main(args: Array<String>) {
		// hmmmmmm, spicy! https://youtu.be/6zGCKhfXPVo

		println("\n" + t.yellow(HEADER))

		val randomTip = randomStartupTips[random.nextInt(randomStartupTips.size)]

		println(t.brightBlue(">> $randomTip"))

		val rootFolder = File(System.getProperty("serverFolder", ".")).absoluteFile
		val spicyFolder = Paths.get(DreamSpicyBoot::class.java.protectionDomain.codeSource.location.toURI()).toFile().parentFile

		val spicyConfigFile = File(spicyFolder, "config.json")

		if (!spicyConfigFile.exists()) {
			error("config.json não existe!")
			return
		}

		val spicyConfig = gson.fromJson<SpicyConfig>(spicyConfigFile.readText())

		val pluginsParadiseFolder = File(spicyConfig.pluginsFolder)

		val serverConfigFile = File(rootFolder, "server_config.json")
		if (!serverConfigFile.exists()) {
			error("server_config.json não existe!")
			return
		}

		val serverConfig = gson.fromJson<ServerConfig>(serverConfigFile.readText())

		with(t) {
			println("Preparando ${(magenta + bold)(serverConfig.serverName)}...")
			println("Versão                      ${serverConfig.serverVersion.getPretty()} / ${serverConfig.platformType.name}")
			println("Deletar plugins ao iniciar? ${colorfulBoolean(serverConfig.deletePluginsOnBoot)}")
			println("Plugins                     ${serverConfig.plugins.size}")
		}

		val pluginsFolder = File(rootFolder, "plugins")

		if (serverConfig.autoUpdate) {
			println(t.cyan("Verificando novas versões do servidor..."))

			val document = Jsoup.connect("https://yivesmirror.com/downloads/paperspigot")
					.userAgent(USER_AGENT)
					.get()
					.body()

			val rel = document.getElementsByAttributeValue("rel", serverConfig.serverVersion.getPretty())

			val parent = rel.first().parent()
			val downloadButton = parent.getElementsByTag("a").first()
			val href = downloadButton.attr("href")
			println(t.cyan("Download encontrado: ${href}"))
			val fileName = href.split("/").last()

			val storedServerJarFile = File(pluginsParadiseFolder, fileName)

			if (storedServerJarFile.exists()) {
				println(t.brightGreen("A última versão de ${serverConfig.serverVersion.getPretty()} / ${serverConfig.platformType.name} (${t.white(fileName)}) já está disponível no nosso repositório de plugins! ~(˘▾˘~)"))
			} else {
				val downloadUrl = URL("https://yivesmirror.com/files/paperspigot/$fileName")
				val downloadConnection = downloadUrl.openConnection() as HttpURLConnection
				downloadConnection.setRequestProperty("User-Agent", USER_AGENT)
				val downloadInputStream = downloadConnection.getInputStream()
				val jarBytes = downloadInputStream.readBytes()
				storedServerJarFile.writeBytes(jarBytes)

				println(t.brightGreen("${serverConfig.serverVersion.getPretty()} / ${serverConfig.platformType.name} (${t.white(fileName)}) foi atualizada com sucesso!"))
			}
			val file = File(rootFolder, "server.jar")
			storedServerJarFile.copyTo(file, true)

			println(t.brightGreen("Servidor atualizado com sucesso!"))
		}

		val eulaFile = File(rootFolder, "eula.txt")
		if (!eulaFile.exists()) {
			println(t.cyan("Criando e aceitando EULA..."))
			eulaFile.writeText("eula=true")
			println(t.brightGreen("EULA aceito com sucesso!"))
		}

		val list = serverConfig.plugins

		val deferred = list.filter { it.autoUpdate } .map { pluginInfo ->
			async {
				println(t.cyan("Verificando ${pluginInfo.name}..."))

				if (pluginInfo.updateFrom == UpdateSource.CIRCLECI) {
					val buildIndex = pluginInfo.buildIndex ?: "latest"
					val payload = getCircleArtifactInfo("github", pluginInfo.organization, pluginInfo.name, pluginInfo.buildIndex ?: "latest")

					if (payload == null) {
						error("${t.brightYellow(pluginInfo.name)} não foi encontrado no CircleCI!")
						return@async
					}

					val firstArtifact = payload.firstOrNull()?.url

					if (firstArtifact == null) {
						error("${t.brightYellow(pluginInfo.name)} não tem nenhum artifact no CircleCI!")
						return@async
					}

					val matcher = CIRCLECI_PATTERN.matcher(firstArtifact).apply { this.find() }
					val buildNumber = matcher.group(1)

					val circleArtifactFile = File(pluginsParadiseFolder, pluginInfo.storedJarName.replace("{{build}}", "b$buildNumber"))

					if (circleArtifactFile.exists()) {
						println(t.brightGreen("A última versão de ${t.brightYellow(pluginInfo.name)} (${t.white("build ${buildNumber}")}) já está disponível no nosso repositório de plugins! ~(˘▾˘~)"))
						return@async
					}

					var originalJarName = payload.first().path.split("/").last()

					// Hora de baixar o artifact!
					val downloadUrl = URL(firstArtifact)
					val downloadConnection = downloadUrl.openConnection()
					val downloadInputStream = downloadConnection.getInputStream()
					val jarBytes = downloadInputStream.readBytes()
					val file = File(pluginsParadiseFolder, pluginInfo.storedJarName.replace("{{build}}", buildIndex))
					file.writeBytes(jarBytes)
					// Nós também iremos copiar a JAR com o nome "b{buildIndex}" na pasta de plugins
					file.copyTo(circleArtifactFile)

					println(t.brightGreen("${t.brightYellow(pluginInfo.name)} (${t.white("build ${buildNumber}")}) foi atualizado com sucesso!"))
				} else {
					throw RuntimeException("Plugin ${pluginInfo.name} utiliza source inexistente!")
				}
			}
		}

		runBlocking {
			deferred.forEach {
				it.await()
			}

			pluginsFolder.mkdirs()

			if (serverConfig.deletePluginsOnBoot) {
				println(t.cyan("Deletando plugins antigos..."))
				pluginsFolder.listFiles().filter { it.extension == "jar" }.forEach { it.delete() }
			}

			println(t.cyan("Movendo plugins..."))
			// Agora nós iremos pegar todas as JARs necessárias para iniciar o servidor
			for (pluginInfo in list) {
				val sourceJar = pluginsParadiseFolder.listFiles().firstOrNull {
					it.extension == "jar" && it.nameWithoutExtension.matches(Regex(pluginInfo.sourceJarPattern))
				}

				if (sourceJar == null) {
					error("Source JAR de ${t.brightYellow(pluginInfo.name)} não existe ou não foi encontrada!")
					continue
				}

				sourceJar.copyTo(File(pluginsFolder, pluginInfo.jarName), true)
				println(t.brightGreen("${t.brightYellow(pluginInfo.name)} copiado com sucesso para a pasta do servidor! Nome: ${t.brightYellow(pluginInfo.jarName)}"))
			}
		}

		println(t.cyan("Criando script de inicialização..."))

		val startupScript = File(rootFolder, "start0.sh")
		startupScript.delete()

		var scriptLines = ""

		for (line in HEADER.lines()) {
			scriptLines += "# $line\n"
		}

		scriptLines += "# $randomTip\n"
		scriptLines += "# Gerado às ${System.currentTimeMillis()}\n\n"

		var javaStartup = "java"

		if (spicyConfig.extraFlags != null) {
			javaStartup += " ${spicyConfig.extraFlags.replace("{{serverName}}", serverConfig.serverName.replace(" ", "_").toLowerCase())}"
		}
		if (serverConfig.enableJRebel && spicyConfig.jrebelFlags != null) {
			javaStartup += " ${spicyConfig.jrebelFlags.replace("{{jrebelPort}}", serverConfig.jrebelPort.toString())}"
		}

		val classpathJars = mutableListOf("server.jar")
		classpathJars.addAll(spicyConfig.classpathJars)

		javaStartup += " ${serverConfig.flags} -cp ${classpathJars.joinToString(":", transform = { "\"$it\""})} org.bukkit.craftbukkit.Main"

		scriptLines += javaStartup

		startupScript.writeText(scriptLines)

		println(t.brightGreen("Sucesso! Servidor está pronto para iniciar e brilhar! ʕ•ᴥ•ʔ"))
	}

	fun getCircleArtifactInfo(vcsType: String, organization: String, repository: String, buildIndex: String = "latest"): List<CircleArtifact>? {
		val url = URL("https://circleci.com/api/v1.1/project/$vcsType/$organization/$repository/$buildIndex/artifacts")

		val connection = url.openConnection() as HttpURLConnection
		val responseCode = connection.responseCode

		if (responseCode == 404) {
			return null
		}

		val inputStream = connection.inputStream
		val byteArray = inputStream.readBytes()
		val content = byteArray.toString(Charsets.UTF_8)

		return gson.fromJson(content)
	}

	fun error(text: Any) {
		println(t.brightRed(text.toString()))
	}

	fun colorfulBoolean(boolean: Boolean): String {
		return if (boolean) {
			t.brightGreen("SIM")
		} else {
			t.red("NÃO")
		}
	}
}