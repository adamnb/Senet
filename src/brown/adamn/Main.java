package brown.adamn;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Start Game loop
        GameManager gm = new GameManager( new Senet('@', '#', 1, 2));
        gm.run();
    }
}
