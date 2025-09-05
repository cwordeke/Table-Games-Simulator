package com.cworde.table_games_simulator_backend.game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    // Empty list for cards
    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getSum() {
        int sum = 0;
        int aceCount = 0;

        // Loop through the Card objects in hand list
        for (Card card : this.cards) {
            String rank = card.getRank();

            if (rank.equals("A")) {
                sum += 11;
                aceCount++;
            } else if ("KQJ".contains(rank)) {
                sum += 10;
            } else {
                sum += Integer.parseInt(rank);
            }
        }

        // Adjust for aces if sum > 21 (soft)
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }

        return sum;
    }

}
