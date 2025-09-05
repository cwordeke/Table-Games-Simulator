package com.cworde.table_games_simulator_backend.game;

import java.util.ArrayList;

public class BlackjackGame {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private String gameStatus; // PLAYER_TURN, DEALER_TURN, PLAYER_WIN, PLAYER_BUST, PLAYER_BJ DEALER_WIN, DEALER_BUST, DEALER_BJ, PUSH
    private int bet;

    public BlackjackGame(int initialBet) {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.gameStatus = "PLAYER_TURN";
        this.bet = initialBet; // This round only
    }

    public void dealInitialHand() {
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        // Check for BlackJack
        boolean playerBJ = (playerHand.getSum() == 21);
        boolean dealerBJ = (dealerHand.getSum() == 21);

        if (playerBJ && dealerBJ) {
            this.gameStatus = "PUSH";
        } else if (playerBJ) {
            this.gameStatus = "PLAYER_BJ";
        } else if (dealerBJ) {
            this.gameStatus = "DEALER_BJ";
        }
    }

    public void playerStand() {
        this.gameStatus = "DEALER_TURN";
        dealerTurn();
    }

    public void playerHit() {
        playerHand.addCard(deck.dealCard());
        if (playerHand.getSum() > 21) {
            this.gameStatus = "PLAYER_BUST";
        }
    }

    public void playerDouble() {
        // Only valid if player has 2 cards
        this.bet *= 2;
        playerHand.addCard(deck.dealCard());
        if (playerHand.getSum() > 21) {
            this.gameStatus = "PLAYER_BUST";
        } else {
            playerStand();
        }
    }

    public void dealerTurn() {
        // Hit until 17+
        while (dealerHand.getSum() < 17) {
            dealerHand.addCard(deck.dealCard());
        }
        determineOutcome();
    }

    public void determineOutcome() {
        int playerSum = playerHand.getSum();
        int dealerSum = dealerHand.getSum();

        if (playerSum > 21) {
            this.gameStatus = "PLAYER_BUST";
        } else if (dealerSum > 21) {
            this.gameStatus = "DEALER_BUST";
        } else if (playerSum > dealerSum) {
            this.gameStatus = "PLAYER_WINS";
        } else if (dealerSum > playerSum) {
            this.gameStatus = "DEALER_WINS";
        } else {
            this.gameStatus = "PUSH";
        }
    }
}
