package io.toytech.backend.board.repository;

import io.toytech.backend.board.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

}
