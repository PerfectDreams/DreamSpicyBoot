<p align="center">
<img src="https://perfectdreams.net/assets/img/perfectdreams_logo.png">
<br>
<a href="https://perfectdreams.net/"><img src="https://perfectdreams.net/assets/img/perfectdreams_badge.png?v2"></a>
<a href="https://perfectdreams.net/loja"><img src="https://img.shields.io/badge/donate-perfectdreams-00CE44.svg"></a>
<a href="https://loritta.website/donate"><img src="https://img.shields.io/badge/donate-loritta-00CE44.svg"></a>
</p>
<p align="center">
<a href="https://perfectdreams.net/discord"><img src="https://discordapp.com/api/guilds/320248230917046282/widget.png"></a>
<a href="https://fb.me/perfectdreamsmc"><img src="https://img.shields.io/badge/👍 Curtir-PerfectDreams 🎮-3B5998.svg?longCache=true"></a>
<a href="https://twitter.com/intent/user?screen_name=perfectdreamsmc"><img src="https://img.shields.io/twitter/follow/perfectdreamsmc.svg?style=social&label=Seguir%20PerfectDreams"></a>
<a href="https://twitter.com/intent/user?screen_name=MrPowerGamerBR"><img src="https://img.shields.io/twitter/follow/mrpowergamerbr.svg?style=social&label=Seguir%20MrPowerGamerBR"></a>
<a href="https://mrpowergamerbr.com/"><img src="https://img.shields.io/badge/website-mrpowergamerbr-blue.svg"></a>
</p>
<p align="center">
<a href="https://perfectdreams.net/open-source">
<img src="https://perfectdreams.net/assets/img/perfectdreams_opensource_iniciative_rounded.png">
</a>
</p>
<h1 align="center">🌶️ DreamSpicyBoot 🌶️</h1>
<p align="center">
<a href="https://jenkins.perfectdreams.net/job/DreamSpicyBoot/"><img src="https://jenkins.perfectdreams.net/job/DreamSpicyBoot/badge/icon"></a>
<a href="https://www.codacy.com/app/MrPowerGamerBR/DreamSpicyBoot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=PerfectDreams/DreamSpicyBoot&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/4bcd6b41d3fd4c8c809b05cca7befb81"></a>
<a href="https://github.com/PerfectDreams/DreamSpicyBoot/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-AGPL%20v3-lightgray.svg"></a>
</p>
<p align="center">
<a href="https://github.com/PerfectDreams/DreamSpicyBoot/watchers"><img src="https://img.shields.io/github/watchers/PerfectDreams/DreamSpicyBoot.svg?style=social&label=Watch"></a>
<a href="https://github.com/PerfectDreams/DreamSpicyBoot/stargazers"><img src="https://img.shields.io/github/stars/PerfectDreams/DreamSpicyBoot.svg?style=social&label=Stars"></a>
</p>
<p align="center">Deixando o startup do seu servidor mais apimentado. — Gerenciador de plugins, auto updater e muito mais! Hmmmm, Spicy Calamari!</p>

<p align="center">🦑🌶️ https://youtu.be/6zGCKhfXPVo 🌶🦑️</p>

DreamSpicyBoot é um gerenciador de inicialização de servidores para o [PerfectDreams](https://perfectdreams.net), originalmente criado para automaticamente atualizar plugins do [PerfectDreams](https://github.com/PerfectDreams) diretamente do CircleCI para o servidor, ele foi expandido para:

* Gerenciar plugins automaticamente
* Atualizar plugins diretamente pelo CircleCI
* Atualizar a JAR do servidor automaticamente
* Permitir customização das flags de inicialização
* Ativar o JRebel em servidores específicos
* E muito mais!

A ideia do DreamSpicyBoot surgiu ao administrar plugins, já que muitas vezes, ao alterar uma JAR enquanto o servidor está rodando, pode causar problemas no plugin devido as diferenças nas classes.

## 💁 Como Ajudar?
Existem vários repositórios [na nossa organização](https://github.com/PerfectDreams) de várias partes do PerfectDreams, caso você queria contribuir em outras partes do PerfectDreams, siga as instruções no `README.md` de cada repositório!

### 💵 Como Doar?

Mesmo que você não saiba programar, você pode ajudar no desenvolvimento do PerfectDreams comprando vantagens em nossos servidores! https://perfectdreams.net/loja

Você também pode doar para a [Loritta](https://loritta.website/support), a mascote do PerfectDreams! 😊

### 🙌 Como Usar?

#### Usando Windows?
DreamSpicyBoot também funciona para windows! Só que com uma pequena diferença. Você deve criar um arquivo chamado `start.bat` ao invés de `start.sh` com o seguinte:
```
java -Xmx256M -Xms256M -DserverRoot=%CD% -jar DreamSpicyBoot-1.0-SNAPSHOT-jar-with-dependencies.jar

start start0.bat
```

Usar o DreamSpicyBoot é simples, primeiro você precisa colocá-lo em alguma pasta e criar um `config.yml`.
```yml
plugins-folder: "/home/servers/perfectdreams/plugins_paradise/"
extra-flags: "-Dserver={{serverName}}"
java-path: 'Path do java no lugar'
classpath-jars:
  - "/home/servers/perfectdreams/plugins_paradise/kotlin/*"
jrebel-flags: "-Drebel.remoting_port={{jrebelPort}} -agentpath:/home/jrebel-2018-1-3/lib/libjrebel64.so -Drebel.remoting_plugin=true"
```

| Chave  | Significado  |
|---|---|
| `plugins-folder`  | Aonde o DreamSpicyBoot irá salvar plugins baixados. |
| `extra-flags`  | JVM flags que serão adicionadas no script de inicialização do servidor. |
| `classpath-jars`  | JARs que deverão ser colocadas na classpath. |
| `java-path` | Diretorio do java.exe
| `jrebel-flags`  | Flags que serão usadas pelo JRebel, caso esteja ativado no servidor. |

Após configurar o DreamSpicyBoot, você deverá criar uma configuração chamada `server_config.yml` na pasta do seu servidor.
```yml
server-name: PerfectDreams Lobby
platform-type: PAPER
minecraft-version: "Minecraft 1.12.2"
auto-update: false
deletePluginsOnBoot: true
flags: "-Xmx512M -Xms512M -Dprotocolsupport.replacedecoderencoder -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=50 -XX:+AggressiveOpts -XX:+AlwaysPreTouch"
jrebel:
  enabled: true
  port: 54549
plugins:
- name: DreamCore
  auto-update:
    update-source: "JenkinsUpdater"
```

| Chave  | Significado  |
|---|---|
| `server-name`  | Nome do servidor. |
| `platform-type`  | Plataforma que o servidor utiliza. |
| `minecraft-version`  | Versão do servidor. |
| `auto-update`  | Se o servidor irá atualizar automaticamente a JAR da plataforma escolhida. |
| `delete-plugins-on-boot`  | Se o DreamSpicyBoot deve deletar todas as JARs da pasta de plugins ao iniciar o servidor. |
| `flags`  | JVM flags. |
| `jrebel.enabled`  | Se o JRebel deve estar ativado no servidor. |
| `jrebel.port`  | Porta que o JRebel deverá utilizar. |
| `plugins`  | Uma lista com todos os plugins que o servidor utiliza. |

Plugins são registrados assim:
```yml
- name: DreamCore
  auto-update:
    update-source: "JenkinsUpdater"
```
```yml
- name: Citizens
  version: LATEST
```
| Chave  | Significado  |
|---|---|
| `name`  | Nome do plugin. |
| `version`  | (Opcional) Versão do plugin |
| `auto-update.update-source`  | (Opcional) Fonte dos Updates |

**Update Sources disponíveis:** `JenkinsUpdater`, `CircleUpdater`, `GitHubUpdater`, `SpigotUpdater`.

Caso você coloque `LATEST` no `version`, o DreamSpicyBoot irá pegar o arquivo que foi editado mais recentemente na pasta do plugin.

Caso você esteja utilizando o `GitHubUpdater`, a versão publicada no GitHub será pega pelo valor do `version`

Caso você esteja utilizando o `JenkinsUpdater`, é possível alterar o servidor em que as versões são pegas alterando o valor do `server`

Caso você esteja utilizando o `CircleUpdater`, é possível alterar a organização em que as versões são pegas alterando o valor do `organization`

Caso você esteja utilizando o `JenkinsUpdater` ou o `CircleUpdater`, é possível definir qual build será baixado usando `build-index`

O `version` também suporta tagged releases locais, para criar uma tag, crie um arquivo chamado `tags.yml` na pasta do plugin
```yml
tags:
- name: "LATEST_1_7_10"
  jar-name: "ProtocolSupport-1_7_10.jar"
```

Após criar as configurações necessárias, crie um arquivo chamado `start.sh` e inicie ele pelo bash
```
java -Xmx256M -Xms256M -DserverRoot=$PWD -jar /home/servers/perfectdreams/dreamspicyboot/DreamSpicyBoot-1.0-SNAPSHOT-jar-with-dependencies.jar

sh start0.sh

sh start.sh
```

E se tudo der certo...
![https://mrpowergamerbr.com/uploads/2018-06-24_22-08-14.gif](https://mrpowergamerbr.com/uploads/2018-06-24_22-08-14.gif)

#### 👨‍💻 Como Compilar?

Você também pode usar este projeto e usar em outros lugares, mas lembrando...
* Nós deixamos o código-fonte de nossos projetos para que outras pessoas possam se inspirar e aprender com nossos projetos, o objetivo é que pessoas que são fãs do PerfectDreams aprendam como o servidor funciona e, caso queiram, podem ajudar o servidor com bug fixes e novas funcionalidades.
* Eu não irei dar suporte caso você queria usar o nosso projeto no seu servidor sem dar nada em troca para o PerfectDreams, lembre-se, a licença do projeto é [AGPL v3](https://github.com/PerfectDreams/DreamSpicyBoot/blob/master/LICENSE), você é **obrigado a deixar todas as suas alterações no projeto públicas**!
* Eu não irei ficar explicando como arrumar problemas no seu projeto se você apenas quer pegar o código-fonte para outra coisa não relacionada com o PerfectDreams, **você está por sua conta e risco**.
* Lembrando que nossos projetos precisam de setups e workflows específicos, você **não irá conseguir usar** nossos projetos apenas compilando e usando!
* Existem várias coisas "hard coded" no projeto, ou seja, você terá que editar o código-fonte dela e recompilar, afinal, o projeto foi criado apenas para ser utilizado no PerfectDreams então você terá que fazer algumas modificações no código-fonte dela para funcionar. 😉
* Caso você irá usar a sua versão em um lugar que não seja no PerfectDreams ou em seu servidor de desenvolvimento, você não poderá utilizar o nome "PerfectDreams", o nome do projeto ou "Loritta".

Mas se você quiser mesmo compilar o projeto, siga os seguintes passos:
1. Tenha o MongoDB instalado na sua máquina.
2. Tenha o JDK 8 (ou superior) na sua máquina.
3. Tenha o Git Bash instalado na sua máquina.
4. Tenha o Maven instalado na sua máquina com o `PATH` configurado corretamente. (para que você possa usar `mvn install` em qualquer pasta e o `JAVA_HOME`, para que o `mvn install` funcione)
5. Tenha o IntelliJ IDEA instalado na sua máquina.
6. Tenha um servidor de Minecraft rodando [Paper](https://github.com/PaperMC/Paper) na última versão disponível, para transformar sonhos em realidade, nossos projetos sempre utilizam a última versão disponível no momento que o projeto foi criado.
7. Faça ```git clone https://github.com/PerfectDreams/DreamSpicyBoot.git``` em alguma pasta no seu computador.
8. Agora, usando o PowerShell (ou o próprio Git Bash), entre na pasta criada e utilize `mvn install`
9. Após terminar de compilar, vá na pasta `target` e pegue a JAR do projeto.
10. Pronto, agora é só utilizar o projeto e se divertir! 🎉

### 🔀 Pull Requests
No seu Pull Request, você deverá seguir o meu estilo de código bonitinho que eu faço, é recomendado que você coloque comentários nas partes do seu código para que seja mais fácil na hora da leitura.

Caso o seu código possua texto, você é obrigado a utilizar o sistema de localização da Loritta, para que o seu Pull Request possa ser traduzido para outras linguagens, ou seja, após criar o seu Pull Request, crie um Pull Request no repositório de linguagens da Loritta com as keys necessárias.

O seu código não pode ser algo "gambiarra", meu código pode ter algumas gambiarras mas isto não significa que você também deve encher o PerfectDreams com mais gambiarras no seu Pull Request.

Você precisa pensar "será que alguém iria utilizar isto?", se você criar um comando que só seja útil para você, provavelmente eu irei negar o seu Pull Request.

## 📦 Dependências

Nós utilizamos várias [dependências no código-fonte](https://github.com/PerfectDreams/DreamSpicyBoot/blob/master/pom.xml) deste projeto, obrigado a todos os mantenedores das dependências! Sem vocês, talvez nosso projeto não iria existir (ou teria várias funcionalidades reduzidas ou talvez até inexistentes!)

| Nome  | Mantenedor |
| ------------- | ------------- |
| [Kotlin](https://kotlinlang.org/) | JetBrains  |
| [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) | JetBrains  |
| [Mordant](https://github.com/ajalt/mordant) | ajalt |
| [jsoup](https://github.com/jhy/jsoup) | jhy |
| [Gson](https://github.com/google/gson) | Google |
| [Kotson](https://github.com/SalomonBrys/Kotson) | SalomonBrys |

## 📄 Licença

O código-fonte deste projeto está licenciado sob a [GNU Affero General Public License v3.0](https://github.com/LorittaBot/Loritta/blob/master/LICENSE)

PerfectDreams é © MrPowerGamerBR — Todos os direitos reservados

A personagem Loritta é © MrPowerGamerBR & PerfectDreams — Todos os direitos reservados

Ao utilizar o projeto você aceita os [termos de uso da Loritta](https://loritta.website/privacy) e os [termos de uso do PerfectDreams](https://perfectdreams.net/privacy).
