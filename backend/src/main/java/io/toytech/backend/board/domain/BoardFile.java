package io.toytech.backend.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardFile {

  @Id
  @GeneratedValue
  @Column(name = "board_file_id")
  private Long id;

  private Long boardId;
  private String delYn;

  @OneToOne
  @JoinColumn(name = "file_id") //연관키는 보드파일이 가지고 있음
  private File file;

  @Builder
  public BoardFile(Long boardId, Long fileId, String delYn, File file) {
    this.boardId = boardId;
    this.delYn = "N";
    this.file = file;
  }
}
