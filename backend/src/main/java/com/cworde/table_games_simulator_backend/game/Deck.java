package com.cworde.table_games_simulator_backend.game;

import java.util.Random;

public class Deck {

    private final String[] suits = {"♠", "♦", "♥", "♣"};
    private final String[] ranks = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    private final Random random = new Random();

    public Deck() {
        //Generates and returns a random card
    }

    public Card dealCard() {
        // Get random rank
        String randomRank = ranks[random.nextInt(ranks.length)];

        // Get random suit
        String randomSuit = suits[random.nextInt(suits.length)];

        // Create new card & return
        return new Card(randomRank, randomSuit);
    }

}
