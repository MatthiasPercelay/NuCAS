package com.uca.nucas.engine.alphabet;

import javafx.scene.paint.Color;

public class TernaryAlphabet extends Alphabet {
    public TernaryAlphabet() {
        super(3);
        super.colors[1] = Color.BLUE;
        super.colors[2] = Color.RED;
    }
}
