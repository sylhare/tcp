package tcp.annotations

import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

enum class TestSizes { SMALL, MEDIUM, LARGE }

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestSize(val size: TestSizes)

fun getTestSize(cls: KClass<*>): TestSizes? = cls.findAnnotation<TestSize>()?.size

@Target(AnnotationTarget.FIELD)
annotation class MyAnnotation

class MyClass(@MyAnnotation val attr: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Greeter(val greet: String = "")


