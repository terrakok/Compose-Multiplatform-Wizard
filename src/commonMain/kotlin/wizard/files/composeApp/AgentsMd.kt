package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform.*
import wizard.hasPlatform

class AgentsMd(info: ProjectInfo) : ProjectFile {
    override val path = "AGENTS.MD"
    override val content = """
        # Compose Multiplatform Application

        ## Project Structure

        ```
        ├── sharedUI/          # ← ALL: business logic, UI, utilities, data
        └── *App/              # ← thin wrapper: entry point
        ```

        **Rule:** All project code — business logic, UI, navigation, data, utilities — goes in the `sharedUI` module.  
        Platform modules (`androidApp`, `desktopApp`, `iosApp`, `webApp`) are thin wrappers containing only an entry point (`main()` / `Application` / `Activity`).

        **Source sets:** common JS and Wasm code must be written in the `webMain` source set.

        ${runAndBuildBlock(info)}
        ## Build Verification Order

        When checking correctness, compile in this order (skip targets not present in the project):

        1. **JVM** — fastest feedback loop
        2. **Android**
        3. **Wasm / JS**
        4. **iOS** — slowest, compile last

        ## Library Selection

        When searching for a Kotlin Multiplatform library to solve a task:

        1. Check **[kmp-awesome](https://raw.githubusercontent.com/terrakok/kmp-awesome/refs/heads/master/README.MD)** for curated recommendations.
        2. Query **[klibs.io MCP](https://api.klibs.io/mcp)** to verify dependency metadata:
           - Supported targets
           - Maven coordinates
           - Latest / latest stable versions
           - License
           - Maintenance / activity signals
           - Comparable alternatives

        ## Dependency Management

        - Use **version catalog** (`gradle/libs.versions.toml`) for all dependencies.

        ## Kotlin Documentation (MCP)

        Use the Kotlin Documentation MCP for API lookups and reference: **[mcp.kotlinlang.org](https://mcp.kotlinlang.org)**

        ## MCP Tools

        - **[MCP IDEA](https://plugins.jetbrains.com/plugin/22372-kotlin-ide-mcp)** — ask the user to connect for IDE-level code analysis, refactoring, and inspections.
        - **[klibs.io MCP](https://api.klibs.io/mcp)** — KMP dependency metadata.
        - **[mcp.kotlinlang.org](https://mcp.kotlinlang.org)** — Kotlin documentation.
        - **[Compose Hot Reload MCP](https://kotlinlang.org/docs/multiplatform/compose-hot-reload.html#mcp-server-for-ai-agents)** — when the desktop app is running with `hotRun --auto`, connect to the `hotMcpServer` Gradle task to interact with the running app:
          - `reload` — recompiles and hot-reloads changed classes
          - `take_screenshot` — captures the current state of an application window
          - `get_semantic_tree` — returns the Compose semantic tree (UI structure)
          - `get_logs` — returns recent log output from the running application
          - `click`, `type_text`, `scroll` — simulate user input to test interactive flows

        ## Skills

        Prefer using existing skills for development tasks. Recommended skills: **[chrisbanes/skills](https://github.com/chrisbanes/skills)**

        > 💡 **Tip:** When a task could benefit from a skill or MCP tool, ask the user to install/enable the relevant skill or MCP server first.

        ## Platform-Specific Code

        When platform-specific implementation is needed:

        1. **First, try to find a ready-made library** with KMP support.
        2. If no ready solution exists, use **`expect` / `actual`** for platform-specific implementations:
           - Interfaces and `expect` declarations — in `commonMain`
           - `actual` implementations — in `androidMain`, `jvmMain`, `iosMain`, `webMain`

        ## Code Quality & Review

        ### Automated Checks

        Before considering a task complete, the AI agent must:

        1. **Analyze** code via `IDEA MCP` (call hierarchy, symbol search, refactoring).
        2. **Compile** in order: JVM → Android → Wasm/JS → iOS (skipping absent targets).

        ## General Guidelines

        1. **Kotlin Multiplatform First** — write code that works on all target platforms. Avoid platform-specific code in `commonMain`.
        2. **Compose Multiplatform** — use Compose for UI on all platforms.
        3. **Coroutines & Flow** — use Kotlin Coroutines and Flow for asynchronous operations and reactive data.
        4. **Keep it Simple** — don't overcomplicate. Choose the simplest working approach.
        5. **Ask Before Complex Changes** — if a task requires significant architectural decisions, ask the user.
    """.trimIndent()

    private fun runAndBuildBlock(info: ProjectInfo) = buildString {
        appendLine("## Build & Run")
        appendLine("")
        appendLine("        | Target | Command |")
        appendLine("        |--------|---------|")
        if (info.hasPlatform(Android)) {
            appendLine("        | **Android** (debug APK) | `./gradlew :androidApp:assembleDebug` |")
        }
        if (info.hasPlatform(Jvm)) {
            appendLine("        | **Desktop** (run) | `./gradlew :desktopApp:run` |")
            appendLine("        | **Desktop** (hot reload) | `./gradlew :desktopApp:hotRun --auto` |")
        }
        if (info.hasPlatform(Js)) {
            appendLine("        | **JS Browser** (dev) | `./gradlew :webApp:jsBrowserDevelopmentRun` |")
        }
        if (info.hasPlatform(Wasm)) {
            appendLine("        | **Wasm Browser** (dev) | `./gradlew :webApp:wasmJsBrowserDevelopmentRun` |")
        }
    }
}