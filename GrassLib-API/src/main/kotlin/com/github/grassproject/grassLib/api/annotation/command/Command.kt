package com.github.grassproject.grassLib.api.annotation.command

import com.github.grassproject.grassLib.api.annotation.ComingSoon

@ComingSoon
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(val name:String)
