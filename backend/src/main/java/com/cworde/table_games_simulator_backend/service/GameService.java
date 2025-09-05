package com.cworde.table_games_simulator_backend.service;

import com.cworde.table_games_simulator_backend.dto.BlackjackGameState;
import com.cworde.table_games_simulator_backend.dto.PlayerProfile;
import com.cworde.table_games_simulator_backend.game.BlackjackGame;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private BlackjackGame currentGame;
    private PlayerProfile playerProfile;

    public GameService() {
        this.playerProfile = new PlayerProfile();
        this.playerProfile.setChips(1000); // Starting chip stack
    }

    public BlackjackGameState startNewGame(int betAmount) {
        // Validate bet
        if (betAmount <= 0 || betAmount > playerProfile.getChips()) {
            throw new IllegalArgumentException("Invalid bet amount");
        }
        // Deduct the bet amount from total chip stack
        playerProfile.setChips(playerProfile.getChips() - betAmount);

        // Create new game using engine from game package
        this.currentGame = new BlackjackGame(betAmount);
        currentGame.dealInitialHand();

        // Check if there are BJs (or if both have BJ)
        if (currentGame.getGameStatus().endsWith("BJ") || currentGame.getGameStatus().equals("PUSH")) {
            resolveBet();
        }

        // Return game state to frontend
        return createGameStateDTO();
    }

    private BlackjackGameState createGameStateDTO() {
        BlackjackGameState gameStateDTO = new BlackjackGameState();

        // Populate the DTO with data from current game and player profile
        gameStateDTO.setPlayerHand(currentGame.getPlayerHand().getCards());
        gameStateDTO.setPlayerSum(currentGame.getPlayerHand().getSum());
        gameStateDTO.setDealerHand(currentGame.getDealerHand().getCards());
        gameStateDTO.setDealerSum(currentGame.getDealerHand().getSum());

        gameStateDTO.setGameStatus(currentGame.getGameStatus());
        gameStateDTO.setPlayerChipBalance(playerProfile.getChips());

        return gameStateDTO;
    }

    private void resolveBet() {
        String status = currentGame.getGameStatus();
        int bet = currentGame.getBet();

        switch(status) {
            case "PLAYER_BJ":
                playerProfile.setChips(playerProfile.getChips() + (int)(bet * 2.5));
                break;
            case "PLAYER_WIN":
            case "DEALER_BUST":
                playerProfile.setChips(playerProfile.getChips() + (bet * 2));
                break;
            case "PUSH":
                playerProfile.setChips(playerProfile.getChips() + bet);
        }
    }
}
