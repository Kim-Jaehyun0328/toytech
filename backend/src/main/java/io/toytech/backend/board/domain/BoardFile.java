package io.toytech.backend.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  private String originFileName;
  private String savedFileName;

  public BoardFile(String originFileName, String savedFileName) {
    this.originFileName = originFileName;
    this.savedFileName = savedFileName;
  }

  @Builder
  public BoardFile(Board board, String originFileName, String savedFileName) {
    this.board = board;
    this.originFileName = originFileName;
    this.savedFileName = savedFileName;
  }

  public void connetBoardId(Board board) {
    this.board = board;
  }

}
