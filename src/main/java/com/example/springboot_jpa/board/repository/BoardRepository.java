package com.example.springboot_jpa.board.repository;

import com.example.springboot_jpa.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
}