package com.uca.nucas.engine.alphabet;

import javafx.scene.paint.Color;

public class BinaryAlphabet extends Alphabet {
    public BinaryAlphabet() {
        super(2);
        super.colors[1] = Color.BLACK;
    }
}
