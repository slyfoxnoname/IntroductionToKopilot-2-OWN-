# Kotlin Homeworks 26

Готове середовище для запуску консольних Kotlin/JVM-файлів із `part2`: Gradle
Kotlin DSL, Gradle Wrapper, приклад застосунку та тест на `kotlin.test`.

## Потрібно

- JDK 17 або новіший
- Інтернет під час першого запуску Gradle Wrapper (він завантажить Gradle та залежності)

Перевірити Java можна командою `java -version`.

## Запуск у Windows

```powershell
.\gradlew.bat run
.\gradlew.bat test
.\gradlew.bat build
```

Щоб запустити інший файл, який має власну функцію `main`, вкажіть його клас
командою `.\gradlew.bat run -PmainClass=НазваФайлуKt`. Наприклад, для
`Hello.kt` це буде `.\gradlew.bat run -PmainClass=HelloKt`. Якщо у файлі є
`package`, додайте його до імені класу, наприклад `-PmainClass=lesson.HelloKt`.

У VS Code ті самі команди доступні через **Terminal → Run Task**. Для підказок
мови та підтримки Gradle встановіть рекомендовані розширення.

## Структура

- `src/part2/kotlin/` — консольні Kotlin-файли
- `src/test/kotlin/` — автоматичні тести
- `build.gradle.kts` — плагіни та залежності

Початковий приклад розташований у `src/part2/kotlin/Main.kt`. Додавайте сюди
файли для запуску. Наявні Android-файли в `src/part2/HM3/` — це код для
Android-застосунку; його не можна запускати як звичайні консольні програми.
