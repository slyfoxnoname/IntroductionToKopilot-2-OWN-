# Kotlin Homeworks 26

Репозиторій містить Kotlin/JVM-вправи та Android-застосунки. Кожне завдання
зібране як окремий Gradle-модуль.

## Модулі

| Модуль | Тип | Розташування |
|---|---|---|
| `:part1` | Kotlin/JVM | `part1/` |
| `:hm3` | Android | `part2/hm3/` |
| `:hm4` | Android | `part2/hm4/` |
| `:hm5` | Android | `part2/hm5/` |
| `:hm6` | Android | `part2/hm6/` |
| `:hm7` | Android | `part2/hm7/` |

## Потрібні інструменти

- JDK 17 або новіший
- Для Android-модулів: Android SDK Platform 35 і Build Tools 35.0.0
- Інтернет під час першого запуску Gradle Wrapper для завантаження Gradle та залежностей

Якщо Gradle не знаходить Android SDK, задайте `ANDROID_HOME` або вкажіть
шлях до SDK у локальному `local.properties`:

```properties
sdk.dir=C:\\Users\\<ім’я>\\AppData\\Local\\Android\\Sdk
```

`local.properties` є локальним файлом налаштувань і не додається до Git.

## Як запустити Kotlin/JVM-вправу (`:part1`)

Відкрийте PowerShell у корені репозиторію. Щоб зібрати JVM-модуль або запустити
його стандартну точку входу:

```powershell
.\gradlew.bat :part1:build
.\gradlew.bat :part1:run
```

Стандартна точка входу `MainKt` виводить коротку підказку про Android HM4.
Щоб запустити конкретну вправу, у якій є функція `main()`, передайте її
згенеровану JVM-назву через `-PmainClass`. Наприклад, для
`part1/src/main/kotlin/hm4/ex1/main.kt`:

```powershell
.\gradlew.bat :part1:run -PmainClass=hm4.ex1.MainKt
```

Загальний формат: `-PmainClass=<пакет>.<НазваФайлу>Kt`. Для файла без `package`
використовуйте лише `<НазваФайлу>Kt`. Файли, у яких немає `main()`, не
запускаються окремо — запускайте відповідну вправу з її точкою входу.

Інші корисні команди:

```powershell
.\gradlew.bat :part1:test
.\gradlew.bat projects
```

## Як запустити Android-застосунок (`:hm3`–`:hm7`)

1. Запустіть емулятор або під’єднайте Android-пристрій із увімкненим USB
   debugging.
2. Зберіть та встановіть потрібний застосунок, наприклад HM7:

```powershell
.\gradlew.bat :hm7:installDebug
```

Для інших застосунків замініть `hm7` на відповідний модуль (`hm3`–`hm6`). Щоб зібрати
debug APK без встановлення:

```powershell
.\gradlew.bat :hm3:assembleDebug
.\gradlew.bat :hm4:assembleDebug
.\gradlew.bat :hm5:assembleDebug
.\gradlew.bat :hm6:assembleDebug
.\gradlew.bat :hm7:assembleDebug
```

Команда `installDebug` встановлює, але не відкриває застосунок. Щоб запустити
його з PowerShell через ADB, використайте `applicationId` потрібного модуля:

| Модуль | `applicationId` |
|---|---|
| `:hm3` | `com.example.hm3` |
| `:hm4` | `com.example.lifecycleapp` |
| `:hm5` | `com.example.lifecyclecontextapp` |
| `:hm6` | `com.example.mvvm` |
| `:hm7` | `com.example.dice` |

Наприклад, після `:hm7:installDebug`:

```powershell
adb shell monkey -p com.example.dice 1
```

Для запуску іншого застосунку замініть `applicationId` у команді. `adb` має бути
доступний у `PATH` разом з Android SDK Platform Tools.

Також можна відкрити корінь репозиторію в Android Studio, дочекатися синхронізації
Gradle, вибрати потрібний модуль і натиснути **Run**. У VS Code ці команди доступні
через **Terminal → Run Task**.

Зібрані APK з’являються у відповідному каталозі
`part2/hmN/build/outputs/apk/debug/`.
