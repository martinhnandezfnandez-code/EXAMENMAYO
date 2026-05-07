package com.examenmayo.console;

import com.examenmayo.console.input.InputHandler;
import com.examenmayo.console.renderer.ConsoleRenderer;
import com.examenmayo.console.screens.*;
import com.examenmayo.domain.service.*;
import com.examenmayo.infrastructure.persistence.json.DataStore;
import lombok.Getter;

@Getter
public class GameEngine {
    private final ConsoleRenderer renderer;
    private final InputHandler input;
    private final DataStore dataStore;
    private GameState state;
    private final MainMenuScreen mainMenu;
    private final CharacterScreen characterScreen;
    private final CombatScreen combatScreen;
    private final InventoryScreen inventoryScreen;
    private final SkillScreen skillScreen;
    private final MissionScreen missionScreen;
    private final ShopScreen shopScreen;

    public enum GameState { MAIN_MENU, CHARACTER, COMBAT, INVENTORY, SKILLS, MISSIONS, SHOP, EXIT }

    public GameEngine() {
        this.renderer = new ConsoleRenderer();
        this.input = new InputHandler();
        this.dataStore = new DataStore();
        this.state = GameState.MAIN_MENU;
        this.mainMenu = new MainMenuScreen(this);
        this.characterScreen = new CharacterScreen(this);
        this.combatScreen = new CombatScreen(this);
        this.inventoryScreen = new InventoryScreen(this);
        this.skillScreen = new SkillScreen(this);
        this.missionScreen = new MissionScreen(this);
        this.shopScreen = new ShopScreen(this);
    }

    public void setState(GameState newState) {
        this.state = newState;
    }

    public void run() {
        renderer.clear();
        renderer.renderTitle(" RPG ONLINE - RETRO EDITION ");
        System.out.println();
        renderer.renderBox(
            "Bienvenido a RPG Online modo retro!",
            "Un mundo de aventuras, combates y magia.",
            "¡Prepárate para la aventura!"
        );
        System.out.println();
        renderer.pressAnyKey();
        input.readString();

        while (state != GameState.EXIT) {
            renderer.clear();
            switch (state) {
                case MAIN_MENU -> mainMenu.show();
                case CHARACTER -> characterScreen.show();
                case COMBAT -> combatScreen.show();
                case INVENTORY -> inventoryScreen.show();
                case SKILLS -> skillScreen.show();
                case MISSIONS -> missionScreen.show();
                case SHOP -> shopScreen.show();
            }
        }

        renderer.clear();
        renderer.renderTitle(" GRACIAS POR JUGAR ");
        renderer.renderBox("¡Hasta la próxima aventura!");
    }
}
