package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a Game in the Tic-Tac-Toe game.
 * @Data annotation is used to automatically generate
 * boilerplate code like getters, setters, equals, hashCode, and toString methods.
 */
@Entity
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String boardState;
    private String result; // "WIN", "LOSS", "DRAW"
    private LocalDateTime datePlayed;


    /**
     * Default constructor.
     * Required by JPA.
     */
    public Game() {}

    /**
     * Parameterized constructor to create a new game instance.
     *
     * @param user The user who played the game.
     * @param boardState The state of the board at the end of the game.
     */
    public Game(User user, String boardState) {
        this.user = user;
        this.boardState = boardState;
        this.datePlayed = LocalDateTime.now();
    }

}


