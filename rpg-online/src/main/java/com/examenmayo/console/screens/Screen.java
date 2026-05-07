package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import com.examenmayo.console.GameEngine.GameState;

public abstract class Screen {
    protected final GameEngine engine;

    protected Screen(GameEngine engine) {
        this.engine = engine;
    }

    public abstract void show();

    protected void navigateTo(GameState state) {
        engine.setState(state);
    }

    protected void waitForInput() {
        System.out.println();
        engine.getRenderer().pressAnyKey();
        engine.getInput().readString();
    }
}
