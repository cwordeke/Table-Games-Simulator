package com.cworde.table_games_simulator_backend.dto;

public class PlayerProfile {

    // Variables
    private int chips;
    private int gamesWon;
    private int gamesLost;
    private int gamesPushed;

    // Public Getters and Setters for each field

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesPushed() {
        return gamesPushed;
    }

    public void setGamesPushed(int gamesPushed) {
        this.gamesPushed = gamesPushed;
    }
}
