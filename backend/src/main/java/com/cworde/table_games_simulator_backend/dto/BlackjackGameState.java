package com.cworde.table_games_simulator_backend.dto;

import com.cworde.table_games_simulator_backend.game.Card;

import java.util.List;

public class BlackjackGameState {
    private List<Card> playerHand;
    private int playerSum;
    private List<Card> dealerHand;
    private int dealerSum;
    private String gameStatus;
    private int playerChipBalance;

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public void setPlayerSum(int playerSum) {
        this.playerSum = playerSum;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(List<Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public void setDealerSum(int dealerSum) {
        this.dealerSum = dealerSum;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getPlayerChipBalance() {
        return playerChipBalance;
    }

    public void setPlayerChipBalance(int playerChipBalance) {
        this.playerChipBalance = playerChipBalance;
    }
}
