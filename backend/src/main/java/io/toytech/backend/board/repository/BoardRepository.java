package io.toytech.backend.board.repository;

import io.toytech.backend.board.constant.BoardType;
import io.toytech.backend.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  Page<Board> findByBoardType(BoardType boardType, Pageable pageable);
}
