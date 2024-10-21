package extansions.browser;

import java.util.Random;

public class IpFunc {
    public static String generateIpv4() {
        Random rand = new Random();
        int firstOctet;
        do {
            firstOctet = rand.nextInt(256);
        } while (isInvalidFirstOctet(firstOctet));

        return firstOctet + "." + rand.nextInt(256)
                + "." + rand.nextInt(256) + "." + rand.nextInt(256);
    }

    private static boolean isInvalidFirstOctet(int firstOctet) {
        return (firstOctet == 0 || firstOctet == 10 || firstOctet == 127 || firstOctet == 172 || firstOctet == 192);
    }
}
