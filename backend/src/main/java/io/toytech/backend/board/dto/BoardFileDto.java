package io.toytech.backend.board.dto;


import io.toytech.backend.board.domain.BoardFile;
import lombok.Data;

@Data
public class BoardFileDto {

  private Long id;

  private String originFileName;
  private String savedFileName;


  public BoardFileDto(BoardFile boardFile) {
    this.id = boardFile.getId();
    this.originFileName = boardFile.getOriginFileName();
    this.savedFileName = boardFile.getSavedFileName();
  }


  public BoardFile toEntity() {
    return BoardFile.builder()
        .originFileName(originFileName)
        .savedFileName(savedFileName)
        .build();
  }
}
