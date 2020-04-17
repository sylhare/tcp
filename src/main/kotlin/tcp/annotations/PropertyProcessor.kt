package annotations

import java.util.*

internal class PropertyProcessor(private val props: Properties = Properties()) {
    init {
        props.putAll(System.getProperties())
    }
    
    fun bind(obj: Any) {
        obj.javaClass.declaredFields
            .filter { it.isAnnotationPresent(Property::class.java) }
            .forEach {
                it.isAccessible = true
                it.set(obj, extractValue(it.type,
                    props.getOrDefault(it.getAnnotation(Property::class.java).key, "") as String))
            }
    }

    private fun extractValue(type: Class<*>, value: String): Any {
        return when (type) {
            Int::class.java -> value.toInt()
            else -> value
        }
    }
}