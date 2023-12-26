package io.toytech.backend.board.dto;


import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.domain.File;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardFileDto {

  private Long id;

  private Long boardId;

  public BoardFileDto() {

  }

  @Builder
  public BoardFileDto(Long boardId) {
    this.boardId = boardId;
  }

  public BoardFile toEntity(File file) {
    return BoardFile.builder()
        .boardId(boardId)
        .file(file)
        .build();
  }
}
