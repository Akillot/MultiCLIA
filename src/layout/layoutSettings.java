package layout;

public class layoutSettings {
    // private int width;
    // private int height;

    public static void interfaceConstructor(int width, int height) {
        Character[] borderSymbols = new Character[]{'-', '|', '+'};

        for (int x = 0; x < width; x++) {
            if (x == 0) {
                System.out.print(borderSymbols[2]);
                continue;
            }
            System.out.println(borderSymbols[0]);
            if (x == width - 1) {
                System.out.print(borderSymbols[2]);
            }
            for (int y = 0; y < height; y++) {

                if (y == 0) {
                    System.out.print(borderSymbols[2]);
                    continue;
                }
                System.out.println(borderSymbols[1]);
                if (y == height - 1) {
                    System.out.print(borderSymbols[2]);
                }
            }
        }
    }
}
