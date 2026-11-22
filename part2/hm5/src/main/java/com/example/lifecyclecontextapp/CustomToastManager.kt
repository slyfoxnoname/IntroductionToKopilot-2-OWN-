package com.example.lifecyclecontextapp

import android.content.Context
import android.widget.Toast
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * Завдання 05: Робота з Context та усунення витоків пам'яті (Memory Leaks).
 *
 * Чому НЕ можна зберігати Activity Context напряму у синглтоні (Частина 1):
 * Об'єкт `object CustomToastManager` живе стільки ж, скільки й увесь процес застосунку.
 * Якщо зберегти у поле `private var context: Context? = context` пряме посилання на `MainActivity`,
 * то після повороту екрана або закриття `MainActivity` Garbage Collector (GC) не зможе звільнити
 * старий екземпляр Activity з усією її ієрархією View, ресурсами та Bitmap-зображеннями.
 */
object CustomToastManager {

    // =========================================================================
    // СПОСІБ А (Рекомендований): Збереження глобального applicationContext
    // =========================================================================
    // Безпечно, оскільки ApplicationContext існує в єдиному екземплярі протягом усього
    // часу роботи процесу застосунку і не утримує в пам'яті знищені Activity.
    private var appContext: Context? = null

    // =========================================================================
    // СПОСІБ Б (Альтернативний): Використання слабкого посилання WeakReference
    // =========================================================================
    // Дозволяє GC безперешкодно видалити Activity з пам'яті після її знищення,
    // навіть якщо посилання зберігається всередині синглтона.
    private var contextRef: WeakReference<Context>? = null

    fun init(context: Context) {
        // Реалізація Способу А: беремо саме глобальний applicationContext
        this.appContext = context.applicationContext

        // Реалізація Способу Б: огортаємо переданий контекст у WeakReference
        this.contextRef = WeakReference(context)

        Timber.d("CustomToastManager: ініціалізовано безпечно (applicationContext + WeakReference)")
    }

    /**
     * Виклик Toast через Спосіб А (applicationContext)
     */
    fun showToast(message: String) {
        val currentContext = appContext
        if (currentContext != null) {
            Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show()
            Timber.d("CustomToastManager (Спосіб А - applicationContext): показано Toast '$message'")
        } else {
            Timber.w("CustomToastManager: appContext ще не ініціалізовано!")
        }
    }

    /**
     * Виклик Toast через Спосіб Б (WeakReference<Context>)
     */
    fun showToastViaWeakRef(message: String) {
        val context = contextRef?.get()
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            Timber.d("CustomToastManager (Спосіб Б - WeakReference): показано Toast '$message'")
        } else {
            Timber.w("Context вже знищено сміттєзбирачем!")
        }
    }
}
