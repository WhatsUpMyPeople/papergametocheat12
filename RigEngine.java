package com.papergamerig.client.rig;

import com.papergamerig.client.config.ModConfig;
import net.minecraft.client.Minecraft;

import java.util.Random;

/**
 * Decision engine for all three minigames. This is the pure-logic layer:
 * it does NOT perform item swaps on servers you don't own. Hooks that would
 * perform the swap live client-side only and are gated by ModConfig flags.
 */
public class RigEngine {
    private final ModConfig cfg;
    private final Random rng = new Random();

    private int fiftyCallIndex = 0;
    private int fiftyStreak = 0;

    public RigEngine(ModConfig cfg) { this.cfg = cfg; }

    public void tick(Minecraft mc) {
        // Reserved for per-tick animations / overlay state. The actual rig
        // decisions are pulled on-demand by the hooks below.
    }

    // --- PaperGame -------------------------------------------------------

    /** Returns item id to render for the "paper" slot. */
    public String paperGameChosenItem() {
        if (!cfg.paperGameRigOn) return null;
        return cfg.paperGameCurrentSide == 0 ? cfg.sideA : cfg.sideB;
    }

    // --- 50/50 -----------------------------------------------------------

    /** true = heads/win, false = tails/lose. */
    public boolean fiftyFiftyNext() {
        boolean result;
        switch (cfg.fiftyMode) {
            case FORCE_HEADS -> result = true;
            case FORCE_TAILS -> result = false;
            case ALTERNATE   -> result = (fiftyCallIndex++ & 1) == 0;
            case RANDOM_WIN  -> {
                if (fiftyStreak >= cfg.fiftyWinStreakTarget) { fiftyStreak = 0; result = false; }
                else { result = rng.nextDouble() < 0.75; }
            }
            default -> result = rng.nextBoolean();
        }
        if (result) fiftyStreak++; else fiftyStreak = 0;
        return result;
    }

    // --- Blackjack -------------------------------------------------------

    public boolean blackjackShouldHit(int playerTotal, int dealerUpcard, boolean soft) {
        if (cfg.bjForcePlayer21) return playerTotal < 21;
        if (soft) return playerTotal <= 17;
        if (playerTotal <= 11) return true;
        if (playerTotal == 12) return dealerUpcard < 4 || dealerUpcard > 6;
        if (playerTotal <= 16) return dealerUpcard >= 7;
        return false;
    }

    private int runningCount = 0;
    public void countCard(int cardValue) {
        if (cardValue >= 2 && cardValue <= 6) runningCount++;
        else if (cardValue == 1 || cardValue >= 10) runningCount--;
    }
    public int runningCount() { return runningCount; }
    public void resetCount() { runningCount = 0; }
}
