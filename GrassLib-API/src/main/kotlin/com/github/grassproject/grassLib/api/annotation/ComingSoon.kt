package com.github.grassproject.grassLib.api.annotation

import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(CLASS, FUNCTION, PROPERTY, ANNOTATION_CLASS, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ComingSoon()
