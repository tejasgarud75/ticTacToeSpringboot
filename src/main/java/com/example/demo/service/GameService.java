package com.example.demo.service;

import com.example.demo.entity.Game;
import com.example.demo.entity.User;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for managing Tic-Tac-Toe game logic.
 *
 * This class provides methods to create new games, make moves, check game results,
 * handle AI moves, update game results, and retrieve games.
 */
@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    private static final char EMPTY = '-';
    private static final char X = 'X';
    private static final char O = 'O';

    /**
     * Creates a new game for the specified user.
     *
     * @param user The user for whom the game is created.
     * @return The newly created {@code Game} object.
     */
    public Game createNewGame(User user) {
        Game game = new Game(user, "---------"); // 3x3 board initially empty
        gameRepository.save(game);
        return game;
    }

    /**
     * Makes a move in the game at the specified position for the given player.
     *
     * @param game The game in which the move is to be made.
     * @param position The position on the board where the move is made.
     * @param player The player making the move ('X' or 'O').
     * @return The updated {@code Game} object.
     */
    public Game makeMove(Game game, int position, char player) {
        char[] board = game.getBoardState().toCharArray();
        if (board[position] == EMPTY) {
            board[position] = player;
            game.setBoardState(new String(board));
        }
        return gameRepository.save(game);
    }

    /**
     * Checks the current game board to determine if there is a winner.
     *
     * @param game The game to check for a winner.
     * @return The winner ('X' or 'O') or {@code EMPTY} if there is no winner yet.
     */
    public char checkWinner(Game game) {
        char[] board = game.getBoardState().toCharArray();
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i * 3] == board[i * 3 + 1] && board[i * 3] == board[i * 3 + 2] && board[i * 3] != EMPTY)
                return board[i * 3];
            if (board[i] == board[i + 3] && board[i] == board[i + 6] && board[i] != EMPTY)
                return board[i];
        }
        if (board[0] == board[4] && board[0] == board[8] && board[0] != EMPTY)
            return board[0];
        if (board[2] == board[4] && board[2] == board[6] && board[2] != EMPTY)
            return board[2];
        return EMPTY;
    }

    /**
     * Makes a move for the AI in the game.
     *
     * The AI selects a random available position and makes a move.
     *
     * @param game The game in which the AI makes a move.
     */
    public void aiMove(Game game) {
        char[] board = game.getBoardState().toCharArray();
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == EMPTY) {
                availablePositions.add(i);
            }
        }
        if (!availablePositions.isEmpty()) {
            int aiMove = availablePositions.get(new Random().nextInt(availablePositions.size()));
            makeMove(game, aiMove, O);
        }
    }

    /**
     * Updates the result of the game and the user's statistics.
     *
     * @param game The game whose result is to be updated.
     * @param result The result of the game ("WIN", "LOSS", or "DRAW").
     */
    public void updateGameResult(Game game, String result) {
        game.setResult(result);
        User user = game.getUser();
        switch (result) {
            case "WIN":
                user.setWins(user.getWins() + 1);
                break;
            case "LOSS":
                user.setLosses(user.getLosses() + 1);
                break;
            case "DRAW":
                user.setDraws(user.getDraws() + 1);
                break;
        }
        userRepository.save(user);
        gameRepository.save(game);
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameId The ID of the game to retrieve.
     * @return The {@code Game} object with the specified ID.
     * @throws RuntimeException If the game is not found.
     */
    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    /**
     * Checks if a user has already won a game.
     *
     * @param game The game to check.
     * @return {@code true} if the user has already won a game, {@code false} otherwise.
     */
    public boolean checkAlreadyWin(Game game) {
        return gameRepository.existsByIdAndResult(game.getId(), "WIN");
    }

    /**
     * Retrieves a list of all games.
     *
     * @return A list of all {@code Game} objects.
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
