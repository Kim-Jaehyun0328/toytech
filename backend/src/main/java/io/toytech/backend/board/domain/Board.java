package io.toytech.backend.board.domain;

import io.toytech.backend.board.constant.BoardType;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.comment.domain.Comment;
import io.toytech.backend.member.domain.Member;
import jakarta.persistence.CascadeType;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "title", "content", "views", "likeCount", "boardType"})
public class Board {

  @Id
  @GeneratedValue
  @Column(name = "board_id")
  private Long id;

  private String title;
  private String content;

  private int views = 0;
  private int likeCount = 0;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Enumerated(EnumType.STRING)
  private BoardType boardType;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private List<BoardFile> boardFiles = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  private int commentCount = comments.size();


  @Builder
  public Board(String title, String content, BoardType boardType, Member member,
      List<Comment> comments,
      List<BoardFile> boardFiles) {
    this.title = title;
    this.content = content;
    this.createdAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
    this.boardType = boardType;
    this.member = member;
    this.comments = comments;
    this.boardFiles = boardFiles;
  }

  public void updateBoard(BoardDto boardDto,
      List<BoardFile> boardFiles) { //업데이트 (첨부파일 고려해서 다시 해야 함)
    title = boardDto.getTitle();
    content = boardDto.getContent();
    modifiedAt = LocalDateTime.now();
    boardType = boardDto.getBoardType();
    this.boardFiles = boardFiles;
  }

  public void updateView() {
    this.views += 1;
  }

  public void updateLikeCount(int num) {
    this.likeCount += num;
  }

}
