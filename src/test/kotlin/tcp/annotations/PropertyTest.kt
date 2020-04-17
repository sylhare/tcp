package tcp.annotations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PropertyTest {

    class Config {
        @Property(key = "server.host")
        val host: String = ""

        @Property(key = "server.port")
        val port: Int = 0

        val otherConfig = 0
    }

    @Test
    fun checkProperties() {
        System.getProperties()["server.host"] = "127.0.0.1"
        System.getProperties()["server.port"] = "1234"
        val config = Config()
        PropertyProcessor().bind(config)
        assertEquals("127.0.0.1", config.host)
        assertEquals(1234, config.port)
        assertEquals(0, config.otherConfig)
    }
}