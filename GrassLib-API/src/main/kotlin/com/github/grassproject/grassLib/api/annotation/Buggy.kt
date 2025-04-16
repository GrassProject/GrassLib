package com.github.grassproject.grassLib.api.annotation

import kotlin.annotation.AnnotationTarget.*

@ComingSoon
@MustBeDocumented
@Target(CLASS, FUNCTION, PROPERTY, ANNOTATION_CLASS, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
//@Deprecated("Some bugs haven't been fixed yet", level = DeprecationLevel.WARNING)
annotation class Buggy(val cause:String)
