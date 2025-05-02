package com.github.grassproject.grassLib.api.reflect

import java.lang.reflect.Method

class Reflects {
    companion object {

        /**
         * Retrieves a method from a class based on the provided signature.
         * The signature should be in the format "fully.qualified.ClassName#methodName".
         *
         * @param signature The method signature.
         * @return The [Method] if found, otherwise null.
         * @throws IllegalArgumentException if the signature format is invalid.
         */
        @JvmStatic
        fun getMethod(signature: String): Method? {
            require(signature.contains("#")) { "Invalid signature format. Expected: 'className#methodName'" }

            val (className, methodName) = signature.split("#")
            return try {
                val clazz = Class.forName(className.trim())
                clazz.declaredMethods.firstOrNull { it.name == methodName.trim() }
            } catch (e: ClassNotFoundException) {
                println("Class not found: $className")
                null
            } catch (e: Exception) {
                println("Error retrieving method: ${e.message}")
                null
            }
        }

        /**
         * Invokes a method on an instance with the provided arguments.
         *
         * @param method The method to invoke.
         * @param instance The instance to invoke the method on (null for static methods).
         * @param args The arguments to pass to the method.
         * @return The result of the method invocation, or null if an error occurs.
         */
        @JvmStatic
        fun getValue(method: Method?, instance: Any?, vararg args: Any?): Any? {
            if (method == null) return null
            return try {
                method.isAccessible = true
                method.invoke(instance, *args)
            } catch (e: IllegalAccessException) {
                println("Cannot access method: ${method.name}")
                null
            } catch (e: IllegalArgumentException) {
                println("Invalid arguments for method: ${method.name}")
                null
            }catch (e: Exception) {
                println("Unexpected error invoking method")
                e.printStackTrace()
                null
            }
        }
    }
}


//fun main() {
//    val method = Reflects
//        .getMethod("com.github.grassproject.grassLib.api.utilities.Encryption#encrypt")
//    println("Method: ${method?.name}") // null
//    if (method != null) {
//        val encryptedValue = Reflects.getValue(method, null, "hello grass")
//
//        println("Encrypted Value: $encryptedValue")
//    } else {
//        println("Method not found!")
//    }
//}