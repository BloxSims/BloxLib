package net.bloxsims.bloxlib

import java.lang.reflect.Field
import java.lang.reflect.Method

fun Field.getInaccessibleValue(obj: Any): Any? {
    return try {
        isAccessible = true
        val result = get(obj)
        isAccessible = false
        result
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Field.setInaccessibleValue(obj: Any, value: Any?) {
    try {
        isAccessible = true
        set(obj, value)
        isAccessible = false
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Any.getValue(name: String): Any? {
    return javaClass.getDeclaredField(name).getInaccessibleValue(this)
}

fun Any.setValue(name: String, value: Any?) {
    javaClass.getDeclaredField(name).setInaccessibleValue(this, value)
}

fun Class<*>.getStaticValue(name: String): Any? {
    return getDeclaredField(name).getInaccessibleValue(this)
}

fun Any.getMethod(name: String, vararg parameterTypes: Class<*>): Method? {
    return try {
        javaClass.getDeclaredMethod(name, *parameterTypes)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Any.invokeMethod(name: String, vararg parameterTypes: Class<*>, obj: Any) {
    getMethod(name, *parameterTypes)?.apply {
        isAccessible = true
        invoke(obj)
        isAccessible = false
    }
}

fun Class<*>.invokeStaticMethod(name: String, vararg parameterTypes: Class<*>) {
    try {
        getMethod(name, *parameterTypes).apply {
            isAccessible = true
            invoke(this)
            isAccessible = false
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}