# Kotlin Homeworks 26

У репозиторії є консольний Kotlin/JVM-проєкт і Android-застосунки з домашніх
завдань. HM4 під’єднаний як окремий Gradle-модуль `hm4`. Команда `run` запускає
консольну підказку; вона не намагається запустити Android-код як JVM-програму.

## Потрібно

- JDK 17 або новіший
- Для Android HM4: Android SDK Platform 35 і Android Build Tools 35.0.0
- Інтернет під час першого запуску Gradle Wrapper (він завантажить Gradle та залежності)

Android SDK можна встановити через Android Studio → **Tools → SDK Manager**.
Якщо Gradle не знаходить SDK, задайте `ANDROID_HOME` або створіть локальний
`local.properties` із рядком `sdk.dir=C:\\Users\\<ім’я>\\AppData\\Local\\Android\\Sdk`.

## Консольний Kotlin/JVM-проєкт

У Windows PowerShell:

```powershell
.\gradlew.bat run
.\gradlew.bat test
```

Для запуску іншого Kotlin-файлу з функцією `main` задайте його клас:

```powershell
.\gradlew.bat run -PmainClass=НазваФайлуKt
```

Якщо у файлі є `package`, додайте його до імені класу, наприклад
`-PmainClass=lesson.HelloKt`.

## Android HM4

Відкрийте корінь репозиторію в Android Studio, дочекайтеся синхронізації Gradle
та запустіть конфігурацію `hm4` на емуляторі або під’єднаному пристрої.

Зібрати debug APK без емулятора можна з PowerShell:

```powershell
.\gradlew.bat :hm4:assembleDebug
```

Встановити на під’єднаний пристрій або запущений емулятор:

```powershell
.\gradlew.bat :hm4:installDebug
```

Після встановлення відкрийте застосунок **LifecycleHomeworkApp** на пристрої.
Android Studio може встановити й одразу запустити його кнопкою **Run**.

APK буде створено в `src/part2/HM4/build/outputs/apk/debug/`.
У VS Code ці дії також доступні через **Terminal → Run Task** → **Android: HM4 build**
або **Android: HM4 install**. Звичайна задача **Kotlin: run** запускає лише
консольну програму, а не Android-застосунок.
