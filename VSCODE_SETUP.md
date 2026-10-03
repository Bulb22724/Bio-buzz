# Developing in VS Code

This project can be built and deployed to the robot from VS Code, without Android Studio.

## One-time setup (per computer)

Follow the section for your operating system. Everything is typed in a terminal: Terminal on macOS, **PowerShell** on Windows. In VS Code, open the integrated terminal with `` Ctrl+` ``.

You need three things on every computer: a **JDK**, the **Android SDK** (which includes `adb`), and a **`local.properties`** file telling Gradle where the SDK is. The easiest way to get the JDK and the SDK is to install Android Studio once, even if you never use it afterwards.

### macOS

1. **Install Android Studio** (it provides the JDK and the SDK). The SDK is in `~/Library/Android/sdk`.
2. **Set up the environment.** From the repo root (the folder containing `gradlew`, `FtcRobotController` and `TeamCode`), run:

   ```bash
   echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
   echo 'export ANDROID_HOME=$HOME/Library/Android/sdk' >> ~/.zshrc
   echo 'export PATH=$ANDROID_HOME/platform-tools:$PATH' >> ~/.zshrc
   echo 'export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"' >> ~/.zshrc
   echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
   source ~/.zshrc
   ```

   If your Mac uses bash instead of zsh, replace `~/.zshrc` with `~/.bash_profile`.
3. **Check it.** Open a new terminal, then:

   ```bash
   echo $JAVA_HOME
   adb version
   java -version
   ```

### Windows

1. **Install Android Studio** (it provides the JDK and the SDK). The SDK is in `%LOCALAPPDATA%\Android\Sdk`, usually `C:\Users\<you>\AppData\Local\Android\Sdk`. The JDK is in `C:\Program Files\Android\Android Studio\jbr`.
2. **Set up the environment.** In PowerShell, from the repo root, run these one at a time:

   ```powershell
   # Tell Gradle where the SDK is (forward slashes are required in this file)
   "sdk.dir=$($env:LOCALAPPDATA -replace '\\','/')/Android/Sdk" | Out-File -Encoding ascii local.properties

   # Environment variables (stored for your user account)
   [Environment]::SetEnvironmentVariable("ANDROID_HOME", "$env:LOCALAPPDATA\Android\Sdk", "User")
   [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Android\Android Studio\jbr", "User")

   # Add adb and the JDK to your PATH without overwriting it
   $p = [Environment]::GetEnvironmentVariable("Path", "User")
   [Environment]::SetEnvironmentVariable("Path", "$p;$env:LOCALAPPDATA\Android\Sdk\platform-tools;C:\Program Files\Android\Android Studio\jbr\bin", "User")
   ```

   Do not use `setx PATH`: it cuts the PATH at 1024 characters and can corrupt it.
3. **Close and reopen VS Code completely** (not just the terminal). Environment variables are only read when a program starts.
4. **Check it.** In a new terminal:

   ```powershell
   echo $env:JAVA_HOME
   adb version
   java -version
   ```

   If Android Studio is installed somewhere else, adjust the paths. You can see or edit the variables in Start > "Edit environment variables for your account".

### Both systems

- **Install the VS Code extension.** Open Extensions (`Ctrl+Shift+X`, `Cmd+Shift+X` on macOS) and install **Extension Pack for Java** by Microsoft.
- **Open the project.** In VS Code, choose File > Open Folder and pick the repo root.
- **Build once with internet.** In the terminal:

  | macOS | Windows |
  |---|---|
  | `./gradlew assembleDebug` | `.\gradlew.bat assembleDebug` |

  This downloads Gradle and every dependency into a local cache (`~/.gradle`). Do it **before** joining the robot's Wi-Fi: a laptop connected to the Control Hub has no internet. After that, builds work offline. A new computer needs this step too, and so does any change to a dependency in a `build.gradle` file.
- **`local.properties` is not in git** (it is in `.gitignore`) because it is specific to each computer. Every team member must create their own, and it is normal that it never shows up in `git status`.

On Windows, `./gradlew` does not work in PowerShell: use `.\gradlew.bat`. The VS Code tasks below already do this for you.

## Deploying code to the robot

The robot is reached over Wi-Fi, so your computer has no internet while connected. Do the one-time setup and the first build first.

1. **Join the Control Hub's Wi-Fi network** (`FTC-xxxx`).
2. **Connect adb to the robot.** From the Command Palette (`Cmd+Shift+P` / `Ctrl+Shift+P`), choose "Tasks: Run Task", then **FTC: Connect via ADB (Wi-Fi)**. This runs `adb connect 192.168.43.1:5555`. You can also type that command yourself in the terminal.
3. **Confirm the robot is visible.** Run the task **FTC: ADB Devices** (or `adb devices`). Your robot should be listed as `device`. If it says `offline` or `unauthorized`, or isn't listed, see Troubleshooting.
4. **Build and deploy.** Press `Cmd+Shift+B` (`Ctrl+Shift+B` on Windows). This is the default build task, **FTC: Build and Install (Deploy)**, which runs `installDebug`: it compiles your code and installs the app on the robot.
5. **Run your OpMode.** On the Driver Station, select your OpMode and press Init or Start as usual.

**Compile or deploy?** `assembleDebug` only compiles (no robot needed, good to check your code). `installDebug` compiles and then installs on the robot, so it fails if no device is connected. `installDebug` already includes `assembleDebug`.

If you use a USB cable instead of Wi-Fi, skip steps 1 and 2: plug the Control Hub in and check `adb devices`.

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
- **`command not found: adb`** (macOS) or **`'adb' is not recognized`** (Windows): the PATH change didn't apply. Open a new terminal (on Windows, restart VS Code completely) and recheck the setup for your system.
- **`JAVA_HOME` is not set or invalid**: recheck the setup, and restart VS Code so its terminals pick up the change. The path must point to the folder that contains `bin`.
- **`SDK location not found`**: `local.properties` is missing or wrong. Recreate it with the setup commands. On Windows, use forward slashes in the path.
- **Could not resolve / download errors while on the robot's Wi-Fi**: Gradle needs a dependency that isn't cached yet. Leave the robot's Wi-Fi, run `assembleDebug` with internet, then reconnect. Adding `--offline` to a Gradle command makes it use only the cache and fail immediately if something is missing.
- **`No connected devices`** when deploying: run **FTC: Connect via ADB (Wi-Fi)**, then **FTC: ADB Devices**.
- **`./gradlew` fails in PowerShell**: use `.\gradlew.bat`.
- **Robot not listed, `offline` or `unauthorized` in `adb devices`**: run **FTC: ADB Disconnect**, then connect again. Check the Control Hub screen or Driver Hub for a prompt to authorize the computer.
- **Autocomplete or go-to-definition is missing for FTC classes**: this is a known limitation of the Java extension with Android projects. Building and deploying still work.
