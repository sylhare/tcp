package tcp.annotations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.jvm.javaField


internal class AnnotationsTest {

    @TestSize(TestSizes.SMALL)
    class Example

    @TestSize(TestSizes.MEDIUM)
    class ExampleMedium

    @TestSize(TestSizes.LARGE)
    class ExampleLarge

    @Greeter(greet="Good morning")
    class Greetings

    class MyClass(@MyAnnotation val attr: String)

    @Test
    fun annotationWorks() {
        assertEquals(
            TestSizes.SMALL,
            getTestSize(Example::class)
        )
        assertEquals(
            TestSizes.MEDIUM,
            getTestSize(ExampleMedium::class)
        )
        assertEquals(
            TestSizes.LARGE,
            getTestSize(ExampleLarge::class)
        )
    }

    @Test
    fun myAnnotationTest() {
        val prop = MyClass::attr
        println("Is MyAnnotation annotated:")
        assertTrue(prop.javaField?.isAnnotationPresent(MyAnnotation::class.java)!!)
        prop.javaField?.annotations?.forEach { println("Annotation present is - ${it.annotationClass.qualifiedName}") }
    }

    @Test
    fun propertyTest() {
        val greetings = Greetings::class.java.getAnnotation(Greeter::class.java)
        assertEquals("Good morning", greetings.greet)
    }




}