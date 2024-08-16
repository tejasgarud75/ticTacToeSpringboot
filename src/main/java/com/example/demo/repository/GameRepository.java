package com.example.demo.repository;

import com.example.demo.entity.Game;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
        boolean existsByIdAndResult(Long id, String result);

}
