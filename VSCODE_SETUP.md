# Developing in VS Code

This project can be built and deployed to the robot from VS Code, without Android Studio.

## One-time setup (per computer)

1. **Install a JDK.** The easiest option is the JDK bundled with Android Studio. Add this to `~/.zshrc` (macOS):

   ```bash
   export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
   ```

   If you don't have Android Studio, install JDK 17 or newer and point `JAVA_HOME` at it.

2. **Install the Android SDK.** You need the SDK and its `platform-tools` (which include `adb`). Installing Android Studio once is the simplest way to get them. The default location on macOS is `~/Library/Android/sdk`.

3. **Add ADB to your PATH** (macOS, default SDK location):

   ```bash
   echo 'export PATH=$PATH:~/Library/Android/sdk/platform-tools' >> ~/.zshrc
   source ~/.zshrc
   ```

   Check it with `adb version`.

4. **Install the VS Code extension.** In VS Code, open Extensions (`Cmd+Shift+X`) and install **Extension Pack for Java** by Microsoft.

5. **Open the project.** In VS Code, choose File > Open Folder and pick the repo root (the folder containing `gradlew`, `FtcRobotController` and `TeamCode`).

6. **Check that Gradle works.** In the VS Code terminal:

   ```bash
   ./gradlew tasks
   ```

   The first run downloads Gradle and dependencies, so do it while you have internet.

On Windows, use `gradlew.bat`, set `JAVA_HOME` in the system environment variables, and add `%LOCALAPPDATA%\Android\Sdk\platform-tools` to `Path`.

## Deploying code to the robot

1. **Connect to the robot.**
   - **USB (Control Hub or Driver Hub):** plug your laptop into the Control Hub with a USB cable.
   - **Wi-Fi (Control Hub):** join the Control Hub's Wi-Fi network (`FTC-xxxx`), then run the task **FTC: Connect via ADB (Wi-Fi)**. Run it from the Command Palette: `Cmd+Shift+P`, then "Tasks: Run Task".
2. **Confirm the robot is visible.** In the terminal, run `adb devices`. Your robot should be listed as `device`. If it says `unauthorized` or isn't listed, see Troubleshooting.
3. **Build and deploy.** Press `Cmd+Shift+B` (the default build task, **FTC: Build and Install (Deploy)**). This runs `./gradlew installDebug`, which compiles your code and installs the app on the robot.
4. **Run your OpMode.** On the Driver Station, select your OpMode and press Init or Start as usual.

Deploy while you have internet if you can. Once your laptop joins the Control Hub's Wi-Fi it has no internet access, so Gradle can't download anything new.

## Tasks

| Task | What it does |
|---|---|
| FTC: Build and Install (Deploy) | Builds and installs to the robot (`Cmd+Shift+B`) |
| FTC: Clean Project | Deletes build outputs. Use it if a build behaves strangely |
| FTC: Connect via ADB (Wi-Fi) | Runs `adb connect 192.168.43.1:5555` |
| FTC: ADB Devices | Lists connected devices (`adb devices`). Your robot should show as `device` |
| FTC: ADB Disconnect | Disconnects all adb connections (`adb disconnect`). Use it before reconnecting if a device shows `offline` |
| FTC: Logcat | Streams the robot's logs (`adb logcat`) in its own terminal panel. Press `Ctrl+C` to stop |

## Keyboard shortcuts

On Windows and Linux, use `Ctrl` where the table says `Cmd`.

**Building and running**

| Shortcut (macOS) | Windows / Linux | What it does |
|---|---|---|
| `Cmd+Shift+B` | `Ctrl+Shift+B` | Run the default build task (**FTC: Build and Install (Deploy)**) |
| `Cmd+Shift+P` | `Ctrl+Shift+P` | Command Palette. Type "Tasks: Run Task" to run any FTC task |
| `Ctrl+Backtick` | `Ctrl+Backtick` | Show or hide the integrated terminal (the backtick key is above Tab) |
| `Cmd+Shift+X` | `Ctrl+Shift+X` | Open the Extensions view |

**Finding and navigating code**

| Shortcut (macOS) | Windows / Linux | What it does |
|---|---|---|
| `Cmd+P` | `Ctrl+P` | Quick open a file by name |
| `Cmd+Shift+F` | `Ctrl+Shift+F` | Search across the whole project |
| `Cmd+Shift+O` | `Ctrl+Shift+O` | Jump to a method or field in the current file |
| `Cmd+T` | `Ctrl+T` | Search for a class or symbol across the project |
| `F12` | `F12` | Go to definition |
| `Shift+F12` | `Shift+F12` | Find all references |
| `Ctrl+-` / `Ctrl+Shift+-` | `Alt+Left` / `Alt+Right` | Go back / forward |
| `Cmd+Shift+E` | `Ctrl+Shift+E` | Open the file Explorer |
| `Cmd+B` | `Ctrl+B` | Show or hide the sidebar |

**Editing**

| Shortcut (macOS) | Windows / Linux | What it does |
|---|---|---|
| `Cmd+S` | `Ctrl+S` | Save the file |
| `Cmd+/` | `Ctrl+/` | Comment or uncomment the selected lines |
| `Option+Up` / `Option+Down` | `Alt+Up` / `Alt+Down` | Move the current line up or down |
| `Shift+Option+Up` / `Shift+Option+Down` | `Shift+Alt+Up` / `Shift+Alt+Down` | Duplicate the current line |
| `Cmd+D` | `Ctrl+D` | Select the next match of the selected word |
| `F2` | `F2` | Rename a symbol everywhere |
| `Shift+Option+F` | `Shift+Alt+F` | Format the file |
| `Ctrl+Space` | `Ctrl+Space` | Trigger autocomplete |
| `Cmd+Z` / `Cmd+Shift+Z` | `Ctrl+Z` / `Ctrl+Y` | Undo / redo |

**Useful Command Palette commands** (`Cmd+Shift+P`, then type the name)

| Command | What it does |
|---|---|
| Tasks: Run Task | Pick any task, such as **FTC: Connect via ADB (Wi-Fi)** or **FTC: Clean Project** |
| Java: Clean Java Language Server Workspace | Fixes stale errors or missing autocomplete after big changes |
| Shell Command: Install 'code' command in PATH | Lets you open projects with `code .` from a terminal |
| Developer: Reload Window | Restarts VS Code's window. Use it after changing `JAVA_HOME` or the PATH |

## Troubleshooting

- **`Minimum supported Gradle version is ...`**: Android Studio's "Upgrade Android Gradle Plugin" assistant changed the build files. Do not accept that prompt. To undo it, run `git checkout -- build.gradle build.common.gradle gradle.properties gradle/wrapper/gradle-wrapper.properties TeamCode/build.gradle`. The SDK release pins the Gradle and Android Gradle Plugin versions.
- **`command not found: adb`**: the PATH change didn't apply. Open a new terminal, or recheck step 3.
- **`JAVA_HOME` is not set or invalid**: recheck step 1, and restart VS Code so its terminals pick up the change.
- **Robot not listed or `unauthorized` in `adb devices`**: reconnect the cable or Wi-Fi. Check the Control Hub screen or Driver Hub for a prompt to authorize the computer.
- **Autocomplete or go-to-definition is missing for FTC classes**: this is a known limitation of the Java extension with Android projects. Building and deploying still work.
