package com.github.grassproject.grassLib.annotation

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import kotlin.reflect.KClass

// Not Working 언젠간 할 수 있기를
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Listeners(val plugin: KClass<out JavaPlugin>)

object ListenerAnnotation {
    fun registerAnnotatedListeners() {
        val packageName = this::class.java.`package`.name
        val classes = getClassesInPackage(packageName)
        for (cls in classes) {
            val annotation = cls.getAnnotation(Listeners::class.java)
            if (annotation != null && Listener::class.java.isAssignableFrom(cls)) {
                try {
                    val pluginClass = annotation.plugin.java
                    val pluginInstance = JavaPlugin.getPlugin(pluginClass)
                    val listener = cls.getDeclaredConstructor().newInstance() as Listener
                    Bukkit.getPluginManager().registerEvents(listener, pluginInstance)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getClassesInPackage(packageName: String): List<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val dirs = mutableListOf<File>()
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            dirs.add(File(resource.file))
        }
        val classes = mutableListOf<Class<*>>()
        for (directory in dirs) {
            classes.addAll(findClasses(directory, packageName))
        }
        return classes
    }

    private fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        if (!directory.exists()) return classes
        val files = directory.listFiles() ?: return classes
        for (file in files) {
            if (file.isDirectory) {
                classes.addAll(findClasses(file, "$packageName.${file.name}"))
            } else if (file.name.endsWith(".class")) {
                val className = "$packageName.${file.name.substring(0, file.name.length - 6)}"
                try {
                    classes.add(Class.forName(className))
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        return classes
    }
}