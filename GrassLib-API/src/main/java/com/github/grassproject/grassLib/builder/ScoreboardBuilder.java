package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.utilities.component.StringExt;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * @author APO2073
 * @apiNote Scoreboard Builder
 * */
public class ScoreboardBuilder {
    private String boardID;
    private Criteria type = Criteria.DUMMY;
    private Component title;
    private DisplaySlot slot;
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective;
    private boolean autoUpdate = false;

    public ScoreboardBuilder(String boardID, Criteria type, String title, DisplaySlot slot) {
        this.boardID = boardID;
        this.type = type;
        this.title = StringExt.Companion.toMiniMessage(title);
        this.slot = slot;
    }

    public Scoreboard build() {
        if (objective == null) {
            objective = scoreboard.registerNewObjective(boardID, type, title);
            if (slot != null) {
                objective.setDisplaySlot(slot);
            }
            objective.setAutoUpdateDisplay(autoUpdate);
        }
        return scoreboard;
    }

    public ScoreboardBuilder setBoardID(String boardID) {
        this.boardID = boardID;
        return this;
    }

    public ScoreboardBuilder setType(Criteria type) {
        this.type = type;
        return this;
    }

    public ScoreboardBuilder setTitle(String title) {
        this.title = StringExt.Companion.toMiniMessage(title);
        return this;
    }

    public ScoreboardBuilder setTitle(Component title) {
        this.title = title;
        return this;
    }

    public ScoreboardBuilder setSlot(DisplaySlot slot) {
        this.slot = slot;
        return this;
    }

    public ScoreboardBuilder setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        return this;
    }

    public Objective getObjective() {
        return objective;
    }

    public ScoreboardBuilder addObject(String entry, int score) {
        if (objective == null) build();
        objective.getScore(entry).setScore(score);
        return this;
    }

    public ScoreboardBuilder removeObject(String entry) {
        if (objective == null) return this;
        scoreboard.resetScores(entry);
        return this;
    }
}