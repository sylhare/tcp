package bytes

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class BytesTest {


    @Test
    fun simpleBytes() {
        assertEquals(0x1, "1".toByte())
        assertEquals(0x1,  1.toByte())
        assertEquals(0x08, "8".toByte() )
    }

    @Test
    fun otherBytes() {
        val b : Byte = 127
        assertEquals(15, (b.toInt() and 0x0f).toByte() )
        assertEquals(1, (1 and 0xff).toByte() )
        assertEquals("10000001", String.format("%8s", Integer.toBinaryString((129 and 0xff))).replace(' ', '0'))
    }

    @Test
    fun byteArrays() {
        val charset = Charsets.UTF_8
        val byteArray = "Hello".toByteArray(charset)
        assertEquals("[72, 101, 108, 108, 111]", byteArray.contentToString())
        assertEquals(72.toByte(), byteArray[0])
        assertEquals("Hello", byteArray.toString(charset))
    }

    @Test
    fun bits() {
        // Returns a bit representation of the specified floating-point value as Long according to the IEEE 754 floating-point "double format" bit layout.
        assertEquals( 4607182418800017408, 1.toDouble().toBits() )
        // Returns a bit representation of the specified floating-point value as Int according to the IEEE 754 floating-point "single format" bit layout.
        assertEquals( 1065353216, 1.toFloat().toBits() )
    }

    @Test
    fun bitset() {
        val b = BitSet(4)
        assertEquals(64, b.size())

    }
}