package io.toytech.backend.board.dto;

import io.toytech.backend.board.domain.File;
import lombok.Builder;
import lombok.Data;

@Data
public class FileDto {

  private Long id;                    //id

  private String originFileName;      //원본 파일명

  private String savedFileName;       //저장된 파일명

  private String uploadDir;           //경로명

  private String ext;           //확장자

  private Long size;                  //파일 사이즈

  private String contentType;         //ContentType

  public FileDto() {

  }

  @Builder
  public FileDto(Long id, String originFileName, String savedFileName, String uploadDir
      , String ext, Long size, String contentType) {
    this.id = id;
    this.originFileName = originFileName;
    this.savedFileName = savedFileName;
    this.uploadDir = uploadDir;
    this.ext = ext;
    this.size = size;
    this.contentType = contentType;
  }

  public File toEntity() {
    return File.builder()
        .originFileName(originFileName)
        .savedFileName(savedFileName)
        .uploadDir(uploadDir)
        .ext(ext)
        .size(size)
        .contentType(contentType)
        .build();
  }
}
