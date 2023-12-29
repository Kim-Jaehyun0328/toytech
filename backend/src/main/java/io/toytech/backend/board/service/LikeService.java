package io.toytech.backend.board.service;


import io.toytech.backend.board.domain.Board;
import io.toytech.backend.board.domain.Like;
import io.toytech.backend.board.repository.LikeRepository;
import io.toytech.backend.member.domain.Member;
import io.toytech.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;
  private final MemberService memberService;
  private final BoardService boardService;


  @Transactional
  public void likeBoard(Long boardId, Long memberId) {
    Board board = boardService.findOneReturnEntity(boardId);
    Member member = memberService.findOneReturnEntity(memberId);
    if (likeRepository.existsByBoardAndMember(board,
        member)) { //이미 좋아요를 눌렀다면 -> 취소가 되어야 한다.
      Like like = likeRepository.findByBoardAndMember(board, member);
      likeRepository.delete(like); //db에서 삭제
      boardService.updateLikeCount(board, -1);
    } else {

      Like like = Like.builder()
          .board(board)
          .member(member)
          .build();
      likeRepository.save(like); //db에 생성
      boardService.updateLikeCount(board, 1);
    }
  }

}
