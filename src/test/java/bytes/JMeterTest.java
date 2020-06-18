package bytes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class JMeterTest {

    @Test
    void randomHex() {
        List<String> hex = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");
        String[] hex2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        List hex3 = new ArrayList();
        char[] hex4 = "123456789abcdef".toCharArray();
        int count = ThreadLocalRandom.current().nextInt(0, 15);

        Collections.shuffle(hex);
        Math.random();

        List<String> fourHex = hex.subList(0, 4);
        System.out.println(fourHex);

        String concatFourHex = String.join("", fourHex);
        System.out.println(concatFourHex);
    }
}
