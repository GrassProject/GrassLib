package com.github.grassproject.grassLib.api.annotation.command

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val permission:String)
