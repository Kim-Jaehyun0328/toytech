package io.toytech.backend.board.domain;

import io.toytech.backend.board.constant.BoardType;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.comment.domain.Comment;
import io.toytech.backend.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "title", "content", "views", "likes", "dislikes", "boardType"})
public class Board {

  @Id
  @GeneratedValue
  @Column(name = "community_id")
  private Long id;

  private String title;
  private String content;

  private int views = 0;
  private int likes = 0;
  private int dislikes = 0;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Enumerated(EnumType.STRING)
  private BoardType boardType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "board")
  private List<Comment> comments = new ArrayList<>();


  public static Board createBoard(BoardDto boardDto,
      Member member) { //엔티티로 변경 (저장을 위함)
    Board board = new Board();
    board.member = member;
    board.title = boardDto.getTitle();
    board.content = boardDto.getContent();
    board.boardType = boardDto.getBoardType();
    board.createdAt = LocalDateTime.now();
    board.modifiedAt = LocalDateTime.now();

    return board;
  }

  public void updateBoard(BoardDto boardDto) { //업데이트
    title = boardDto.getTitle();
    content = boardDto.getContent();
    modifiedAt = LocalDateTime.now();
    boardType = boardDto.getBoardType();
  }

  public void updateView() {
    this.views += 1;
  }

}
