package com.examenmayo.domain.experience;

import com.examenmayo.domain.enums.ClasePersonaje;

public class RewardFactory {
    public static LevelUpReward getRewardForClass(ClasePersonaje clase) {
        return switch (clase) {
            case GUERRERO -> LevelUpReward.GUERRERO;
            case MAGO -> LevelUpReward.MAGO;
            case ARQUERO -> LevelUpReward.ARQUERO;
            case PALADIN -> LevelUpReward.PALADIN;
            case ASESINO -> LevelUpReward.ASESINO;
        };
    }
}
