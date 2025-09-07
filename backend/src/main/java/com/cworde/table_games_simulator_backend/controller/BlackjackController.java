package com.cworde.table_games_simulator_backend.controller;

import com.cworde.table_games_simulator_backend.dto.BlackjackGameState;
import com.cworde.table_games_simulator_backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blackjack")
@CrossOrigin("*")
public class BlackjackController {

    private final GameService gameService;

    @Autowired
    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    // ===API endpoints===

    // /start endpoint
    @PostMapping("/start")
    public BlackjackGameState startGame(@RequestParam int bet) {
        return gameService.startNewGame(bet);
    }

    // /hit endpoint
    @PostMapping("/hit")
    public BlackjackGameState hit() {
        return gameService.playerHit();
    }

    // /stand endpoint
    @PostMapping("/stand")
    public BlackjackGameState stand() {
        return gameService.playerStand();
    }

    // /double endpoint
    @PostMapping("/double")
    public BlackjackGameState playerDouble() {
        return gameService.playerDouble();
    }
}
