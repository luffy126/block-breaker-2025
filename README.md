# BlockBreakerJava
## EL QUE NO TRABAJA SE VA A GUANTANAMO

Un juego estilo retro en Java, todo WIP por ahora

### Para echar a andar el proyecto:

- Usar IntelliJ preferentemente, descargar la carpeta y después importarla como proyecto
- en IntelliJ abrir File>Project Structure
- Dentro de la sección Project seleccionar el SDK **Corretto 17** (si no aparece en la lista clickear "Download JDK..." y seleccionar la version 17 y en vendor Amazon Corretto) y en Language level seleccionar el 17
- En el archivo gradle.properties cambiar la linea de open.gradle.java.home y cambiar la ruta que aparece por la ruta donde está instalado el JDK 17 
- Finalmente seleccionar el archivo gradlew sin extension y ejecutarlo con el botón verde de arriba a la derecha o dandole a enter
- Si aparece un error en la consola **POR EL AMOR DE DIOS AVISEN!!!!** por discord o por whatsapp eso jeje

Abajo hay cosas utiles del mismo gradle para debug y tal pero eso cualquier cosa que vayan a hacer por favor avisen la comunicación es clave ahora
 
## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
