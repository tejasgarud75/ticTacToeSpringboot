package com.example.demo.controller;

import com.example.demo.entity.Game;
import com.example.demo.entity.User;
import com.example.demo.exception.GameAlreadyWonException;
import com.example.demo.service.GameService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code GameController} class manages HTTP requests related to Tic-Tac-Toe games.
 *
 * It provides endpoints to start a new game, make a move in the game, retrieve the leaderboard,
 * and get a list of all games. The controller interacts with the {@code GameService} for game logic
 * and the {@code UserService} for user-related operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    /**
     * Starts a new game for the user identified by their email.
     *
     * This endpoint creates a new game and associates it with the user specified by the email address.
     *
     * @param email The email address of the user who will start the game.
     * @return A {@code ResponseEntity} containing the newly created {@code Game} object.
     */
    @PostMapping("/start")
    public ResponseEntity<Game> startGame(@RequestParam String email) {
        User user = userService.findByEmail(email);
        Game game = gameService.createNewGame(user);
        return ResponseEntity.ok(game);
    }

    /**
     * Processes a move in the game.
     *
     * This endpoint allows a player to make a move in the game. It checks if the game is already won,
     * performs the player's move, and then the AI makes its move. The game result is updated based
     * on the outcome of the moves.
     *
     * @param gameId The ID of the game in which the move is to be made.
     * @param position The position on the board where the move is to be made.
     * @return A {@code ResponseEntity} containing the updated {@code Game} object.
     * @throws GameAlreadyWonException If the game is already won.
     */
    @PostMapping("/move")
    public ResponseEntity<Game> makeMove(@RequestParam Long gameId, @RequestParam int position) {
        Game game = gameService.getGameById(gameId);
        boolean alreadyWon = gameService.checkAlreadyWin(game);

        if (alreadyWon) {
            throw new GameAlreadyWonException("Game is over. The user has already won.");
        }

        game = gameService.makeMove(game, position, 'X');
        char winner = gameService.checkWinner(game);

        if (winner == 'X') {
            gameService.updateGameResult(game, "WIN");
            return ResponseEntity.ok(game);
        }

        if (isBoardFull(game)) {
            gameService.updateGameResult(game, "DRAW");
            return ResponseEntity.ok(game);
        }

        // AI makes a move
        gameService.aiMove(game);
        winner = gameService.checkWinner(game);

        if (winner == 'O') {
            gameService.updateGameResult(game, "LOSS");
        } else if (isBoardFull(game)) {
            gameService.updateGameResult(game, "DRAW");
        }

        return ResponseEntity.ok(game);
    }

    /**
     * Retrieves the leaderboard of users based on game performance.
     *
     * This endpoint returns a list of users ranked by their performance in the games.
     *
     * @return A {@code ResponseEntity} containing a list of {@code User} objects representing the leaderboard.
     */
    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderboard() {
        List<User> leaderboard = userService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    /**
     * Checks if the game board is full.
     *
     * This method checks if the board state contains any empty positions.
     *
     * @param game The game to check.
     * @return {@code true} if the board is full, {@code false} otherwise.
     */
    private boolean isBoardFull(Game game) {
        return !game.getBoardState().contains("-");
    }

    /**
     * Retrieves a list of all games.
     *
     * This endpoint returns a list of all games that have been played.
     *
     * @return A {@code ResponseEntity} containing a list of {@code Game} objects.
     */
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }
}