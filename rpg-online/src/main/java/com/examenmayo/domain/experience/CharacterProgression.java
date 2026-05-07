package com.examenmayo.domain.experience;

import com.examenmayo.domain.effect.EffectManager;
import com.examenmayo.domain.enums.ClasePersonaje;
import com.examenmayo.domain.skill.Habilidad;
import com.examenmayo.domain.skill.SkillManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class CharacterProgression {
    @Getter
    private int nivel;
    @Getter
    private long experienciaActual;
    @Getter
    private int puntosDeHabilidad;
    private final ExperienceManager expManager;
    @Getter
    private final SkillManager skillManager;
    @Getter
    private final EffectManager effectManager;

    public CharacterProgression(ClasePersonaje clase) {
        this.nivel = 1;
        this.experienciaActual = 0;
        this.puntosDeHabilidad = 0;
        this.expManager = new ExperienceManager();
        this.skillManager = new SkillManager(clase);
        this.effectManager = new EffectManager();
        skillManager.desbloquearHabilidad(1);
    }

    public long getExperienciaNecesaria() {
        return expManager.getExpRequerida(nivel);
    }

    public LevelUpReward ganarExperiencia(long cantidad, ClasePersonaje clase) {
        ExperienceManager.ExpResult result = expManager.ganarExperiencia(experienciaActual, nivel, cantidad);
        this.experienciaActual = result.experienciaRestante();
        LevelUpReward reward = null;
        for (int i = 0; i < result.nivelesSubidos(); i++) {
            reward = subirNivel(clase);
        }
        return reward;
    }

    private LevelUpReward subirNivel(ClasePersonaje clase) {
        this.nivel++;
        this.puntosDeHabilidad++;
        LevelUpReward reward = RewardFactory.getRewardForClass(clase);
        log.info("¡Nivel {} alcanzado! Atributos mejorados.", this.nivel);
        return reward;
    }

    public List<Habilidad> getHabilidadesDisponibles() {
        return skillManager.getHabilidadesDisponibles();
    }

    public List<Habilidad> getTodasLasHabilidades() {
        return skillManager.getHabilidadesDesbloqueadas();
    }
}
