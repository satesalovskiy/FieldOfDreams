package com.tsa;

import java.util.Random;

public class Drum {
    private Random random = new Random();

    public int roll() {
        return random.nextInt(5);
    }
}
