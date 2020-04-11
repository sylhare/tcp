package bytes

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class BytesTest {


    @Test
    fun simpleBytes() {
        assertEquals(0x1, "1".toByte())
        assertEquals(0x1, 1.toByte())
        assertEquals(0x08, "8".toByte())
        assertNotEquals(0x11, 11.toByte())
        assertEquals(0x11, 17.toByte())
    }

    @Test
    fun hexAndBytes() {
        assertEquals(127, 0x7f)
        assertEquals(255, 0xff)
        assertEquals(15, (0x7f and 15))
        assertEquals(15, (0xff and 15))
        assertEquals("f", Integer.toHexString(15))
        assertEquals("11111111", String.format("%8s", Integer.toBinaryString((0xff and 0xff))).replace(' ', '0'))
    }

    @Test
    /**
     * Two's complement to represent decimal negative values
     * 3 bits from 000 to 111 can give:
     *   - from 0 to 7 (000 is 0 and 111 is seven)
     *   - from -4 to 3 using two's complement (100 is -4 and 011 is 3, 111 is -1)
     * https://en.wikipedia.org/wiki/Two%27s_complement
     */
    fun bitwiseOperation() {
        assertEquals(12, 0x0c)  // 00001100
        assertEquals(25, 0x19)  // 00011001
        assertEquals(29, (0x0c or 0x19)) // a OR b == 1 gives 1 -> 00011101
        assertEquals(8, (0x0c and 0x19)) // a AND b == 1 gives 1 -> 00001000
        assertEquals(21, (0x0c xor 0x19)) // a == 1 AND b == 0 gives 1 -> 00010101
        assertEquals(-(0x0F + 1), 0x0F.inv()) // should be inversed but compiler is showing 2's complement so -(n +1)
        assertEquals(0x08, (0x04 shl 1)) // shift a 0 to the left 0010 -> 0100
        assertEquals(0x02, (0x04 shr 1)) // shift a 0 to the right 0010 -> 0001
        assertEquals((0x04 shr 1), (0x04 ushr 1)) // shifts zero into the leftmost position
        assertEquals(2147483646, (-0x04 ushr 1)) // ushr only impacts 2's complement
        assertEquals(-0x02, (-0x04 shr 1))
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
    fun byteArraysToHex() {
        val bytes = byteArrayOf(10, 2, 15, 11)
        assertEquals("0A020F0B", bytes.joinToString("") { String.format("%02X", it) })
        assertEquals("A2FB", bytes.joinToString("") { String.format("%X", it) })
    }

    @Test
    fun bits() {
        // Returns a bit representation of the specified floating-point value as Long according to the IEEE 754 floating-point "double format" bit layout.
        assertEquals(4607182418800017408, 1.toDouble().toBits())
        // Returns a bit representation of the specified floating-point value as Int according to the IEEE 754 floating-point "single format" bit layout.
        assertEquals(1065353216, 1.toFloat().toBits())
    }

    @Test
    fun bitSet() {
        val b = BitSet(4)
        assertEquals(64, b.size())
    }
}