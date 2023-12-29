package io.toytech.backend.board.repository;

import io.toytech.backend.board.domain.Board;
import io.toytech.backend.board.domain.Like;
import io.toytech.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Like findByBoardAndMember(Board board, Member member);

  boolean existsByBoardAndMember(Board board, Member member);
}
