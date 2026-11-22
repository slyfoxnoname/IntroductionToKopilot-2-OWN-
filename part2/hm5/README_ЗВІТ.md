# Звіт із виконання домашнього завдання
## Теми:
1. **Компоненти Jetpack Lifecycle (`LifecycleOwner`, `LifecycleObserver`, `Lifecycle.State`)** (`03_LifecycleComponentTask.md`)
2. **Детектор фонового стану (`Background Detector` та `ProcessLifecycleOwner`)** (`04_BackgroundDetectorTask.md`)
3. **Робота з `Context` та запобігання витокам пам'яті (`Memory Leaks`)** (`05_ContextAndMemoryLeakTask.md`)

---

## 1. Компоненти Jetpack Lifecycle (`03_LifecycleComponentTask`)
* **`AudioPlayer.kt`** — реалізує `DefaultLifecycleObserver` та автоматично логує старт (`onStart`) і зупинку (`onStop`) фонового аудіо.
* **`AnalyticsTracker.kt`** — реалізує `LifecycleEventObserver` і через `when (event)` обробляє події `ON_RESUME` та `ON_PAUSE`.
* **`MainActivity.kt`** — підписує обидва спостерігачі через `lifecycle.addObserver(...)` у `onCreate()` без захаращення самої `Activity` методами життєвого циклу.
* **`MyApp.kt`** — реєструє `registerActivityLifecycleCallbacks` і виводить у Logcat назву колбеку, ім'я класу `Activity` та поточний стан `activity.lifecycle.currentState`:
  * під час `onActivityCreated` → стан `CREATED`
  * під час `onActivityStarted` → стан `STARTED`
  * під час `onActivityResumed` → стан `RESUMED`
  * під час `onActivityPaused` → стан `STARTED` (оскільки подія `ON_PAUSE` переводить `Activity` зі стану `RESUMED` назад у `STARTED`)
  * під час `onActivityStopped` → стан `CREATED` (оскільки подія `ON_STOP` переводить `Activity` зі стану `STARTED` у `CREATED`)
  * під час `onActivityDestroyed` → стан `DESTROYED`

---

## 2. Детектор фонового стану (`04_BackgroundDetectorTask`)
* **Частина 1 (Ручний спосіб у `MyApp.kt`):** Лічильник `startedActivityCount` збільшується в `onActivityStarted` (при переході `0 -> 1` логується вихід у `Foreground`) і зменшується в `onActivityStopped` (при переході `1 -> 0` логується перехід у `Background`). При навігації `MainActivity -> SecondActivity` спочатку викликається `onActivityStarted` для `SecondActivity` (`startedActivityCount = 2`), а потім `onActivityStopped` для `MainActivity` (`startedActivityCount = 1`), тому хибного спрацювання переходу у фон немає.
* **Частина 2 і 3 (`AppLifecycleObserver.kt`):** Підписаний на `ProcessLifecycleOwner.get().lifecycle`. Зберігає `backgroundTimestamp = System.currentTimeMillis()` у `onStop()` і при поверненні в `onStart()` перевіряє, чи минуло понад 5000 мс: якщо так — виводить лог і показує `Toast("Ви були відсутні більше 5 секунд! Сесію оновлено.")`.

### Відповіді на контрольні запитання (`04_BackgroundDetectorTask`):
1. **Чому `ProcessLifecycleOwner` викликає `onStop()` із затримкою (~700 мс), а не миттєво після згортання Activity?**
   Затримка (~700 мс) необхідна для того, щоб відрізнити справжній вихід застосунку у фон від **повороту екрана (Configuration Change)** або переходу між двома `Activity`. Під час повороту екрана стара `Activity` спочатку повністю проходить `onPause() -> onStop() -> onDestroy()`, і лише за кілька десятків мілісекунд створюється й запускається нова `Activity` (`onCreate() -> onStart() -> onResume()`). Якби `ProcessLifecycleOwner` реагував миттєво, під час кожного повороту екрана він би помилково сигналізував про вихід у Background.
2. **Чим відрізняється `Lifecycle.State.CREATED` для `ProcessLifecycleOwner` від `CREATED` для звичайної `Activity`?**
   У `ProcessLifecycleOwner` подія `ON_CREATE` (і перехід у початковий стан `CREATED`) відбувається **рівно один раз** за весь час життя процесу застосунку під час його старту (і стан повертається до `CREATED` після `ON_STOP`, коли всі `Activity` згорнуті), тоді як окрема `Activity` переходить у `CREATED` щоразу при відкритті нового екрана або перестворенні під час повороту пристрою.
3. **Чи буде викликано метод `onDestroy()` у `ProcessLifecycleOwner`, якщо користувач закриє застосунок зі списку останніх програм (Recent Apps)?**
   **Ні, ніколи.** Документація Jetpack Lifecycle прямо зазначає, що `ProcessLifecycleOwner` ніколи не диспатчить подію `ON_DESTROY` (і не викликає `onDestroy()`), оскільки при закритті зі списку Recent Apps або вбивстві процесу через Low Memory Killer операційна система знищує процес примусово (`SIGKILL`) без гарантованого фінального колбеку рівня процесу.
4. **Який із двох підходів (ручний лічильник чи `ProcessLifecycleOwner`) краще використовувати у реальних проєктах і чому?**
   У реальних проєктах варто використовувати **`ProcessLifecycleOwner`**, оскільки він є офіційним протестованим компонентом Android Jetpack, автоматично враховує зміну конфігурації (повороти екрана, зміну мови чи теми, де ручний лічильник `startedActivityCount` під час перестворення єдиної `Activity` тимчасово падає до `0` і дає хибне спрацювання Background!), а також безшовно інтегрується з корутинами (`lifecycleScope`) та `DefaultLifecycleObserver`.

---

## 3. Робота з `Context` та запобігання витокам пам'яті (`05_ContextAndMemoryLeakTask`)
* У файлі `CustomToastManager.kt` реалізовано обидва безпечні способи збереження контексту:
  * **Спосіб А (`context.applicationContext`):** Зберігає глобальний контекст процесу, який живе стільки ж, скільки і сам синглтон, тому знищені `Activity` вільно прибираються Garbage Collector'ом.
  * **Спосіб Б (`WeakReference<Context>`):** Зберігає слабке посилання на `Context`, яке не перешкоджає Garbage Collector'у видалити `Activity` з RAM після виклику `onDestroy()`.

### Відповіді на контрольні запитання (`05_ContextAndMemoryLeakTask`):
1. **Чому зберігання `Activity Context` у статичному полі або синглтоні є небезпечним?**
   Тому що статичні поля та синглтони (`object` у Kotlin) мають життєвий цикл усього процесу застосунку (є коренями досяжності GC Root), а `Activity` має короткий життєвий цикл (знищується при повороті екрана або закритті). Сильне посилання із синглтона не дозволяє Garbage Collector'у очистити пам'ять знищеної `Activity` разом з усім деревом її `View` та важкими ресурсами, що призводить до `Memory Leak` і зрештою до `OutOfMemoryError`.
2. **Яка різниця між `WeakReference` та звичайним ("сильним") посиланням в Kotlin/Java?**
   Звичайне (**сильне / Strong reference**) посилання гарантує, що поки воно існує, сміттєзбирач (GC) за жодних умов не видалить об'єкт із пам'яті. **Слабке посилання (`WeakReference`)** не утримує об'єкт у пам'яті: щойно на об'єкт не залишиться жодного сильного посилання, під час наступного циклу збирання сміття GC видалить цей об'єкт, а метод `weakRef.get()` почне повертати `null`.
3. **Чи можна використовувати `ApplicationContext` для створення кнопки (`Button(context)`) або показу діалогового вікна (`AlertDialog`)? Чому?**
   * Для **`AlertDialog` — категорично не можна**: діалогове вікно потребує віконного токена (`Window token`) конкретної `Activity`, до якої воно прикріплюється у `WindowManager`, а також теми оформлення; спроба показати діалог із `ApplicationContext` завершиться крешем `WindowManager$BadTokenException` (або помилкою невідповідності теми `IllegalStateException`).
   * Для **створення UI-компонентів (`Button(applicationContext)`) — не рекомендується**: `ApplicationContext` не успадковує `ContextThemeWrapper` і не містить інформації про UI-тему вашої `Activity` (стилі Material Design, кольори атрибутів теми), тому створена кнопка втратить кастомну стилізацію теми або викличе помилку при спробі зчитати theme-атрибути.
