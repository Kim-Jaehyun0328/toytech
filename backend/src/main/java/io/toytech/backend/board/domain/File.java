package io.toytech.backend.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File {

  @Id
  @GeneratedValue
  @Column(name = "file_id")
  private Long id;

  private String originFileName;
  private String savedFileName;

  private String uploadDir;
  private String ext;
  private Long size;
  private String contentType;

  private LocalDateTime createdAt;

  @OneToOne(mappedBy = "file")
  private BoardFile boardFile;

  @Builder
  public File(Long id, String originFileName, String savedFileName
      , String uploadDir, String ext, Long size, String contentType) {
    this.id = id;
    this.originFileName = originFileName;
    this.savedFileName = savedFileName;
    this.uploadDir = uploadDir;
    this.ext = ext;
    this.size = size;
    this.contentType = contentType;
    this.createdAt = LocalDateTime.now();
  }

}
