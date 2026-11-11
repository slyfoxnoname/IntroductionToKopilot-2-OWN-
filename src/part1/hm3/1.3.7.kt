package hm3

import java.util.Locale

fun main() {
    val journalEntries = listOf(
        "2026-11-05 INFO: Запущено сервіс авторизації",
        "2026-11-05 WARN: Повільна відповідь від кеш-сервера",
        "2026-11-05 ERROR: Помилка 502 Bad Gateway при зверненні до вузла B",
        "2026-11-05 INFO: Сесію користувача #408 оновлено",
        "2026-11-05 ERROR: Відмова запису у таблицю транзакцій",
        "2026-11-05 WARN: Черга повідомлень заповнена на 75%",
        "2026-11-05 INFO: Щогодинну перевірку цілісності виконано"
    )

    var countInfo = 0
    var countWarn = 0
    val failedEntries = mutableListOf<String>()

    journalEntries.forEach { record ->
        when {
            "INFO:" in record -> countInfo++
            "WARN:" in record -> countWarn++
            "ERROR:" in record -> failedEntries.add(record)
        }
    }

    val countError = failedEntries.size
    val allEntriesCount = journalEntries.size
    val errorShare = if (allEntriesCount > 0) (countError * 100.0) / allEntriesCount else 0.0
    val errorShareFormatted = "%.2f".format(Locale.US, errorShare)

    val errorDetails = if (failedEntries.isEmpty()) {
        "  (критичних збоїв немає)"
    } else {
        failedEntries.joinToString("\n") { "  [!] $it" }
    }

    val logReport = """
        +--------------------------------------------------+
        |             ПІДСУМКИ ОБРОБКИ ЖУРНАЛУ              |
        +--------------------------------------------------+
        Всього записів у журналі : $allEntriesCount
        Інформаційних [INFO]     : $countInfo
        Попереджень   [WARN]     : $countWarn
        Критичних     [ERROR]    : $countError
        Відсоток збоїв           : $errorShareFormatted%
        ----------------------------------------------------
        Перелік зафіксованих збоїв:
        $errorDetails
        +--------------------------------------------------+
    """.trimIndent()

    println(logReport)
}
