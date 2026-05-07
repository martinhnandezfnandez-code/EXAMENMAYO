package com.examenmayo.console.screens;

import com.examenmayo.console.GameEngine;
import java.util.List;

public class MainMenuScreen extends Screen {

    public MainMenuScreen(GameEngine engine) {
        super(engine);
    }

    @Override
    public void show() {
        engine.getRenderer().renderMenu(
            List.of("Características y Progresión", "Combate", "Inventario", "Habilidades", "Misiones", "Tienda", "Salir"),
            " MENU PRINCIPAL "
        );

        int option = engine.getInput().readInt();
        switch (option) {
            case 1 -> navigateTo(GameEngine.GameState.CHARACTER);
            case 2 -> navigateTo(GameEngine.GameState.COMBAT);
            case 3 -> navigateTo(GameEngine.GameState.INVENTORY);
            case 4 -> navigateTo(GameEngine.GameState.SKILLS);
            case 5 -> navigateTo(GameEngine.GameState.MISSIONS);
            case 6 -> navigateTo(GameEngine.GameState.SHOP);
            case 7 -> navigateTo(GameEngine.GameState.EXIT);
            default -> engine.getRenderer().renderError("Opción inválida");
        }
    }
}
