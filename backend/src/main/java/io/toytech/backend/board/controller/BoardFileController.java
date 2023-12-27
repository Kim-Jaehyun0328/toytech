package io.toytech.backend.board.controller;

import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.service.BoardFileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardFileController {

  private final BoardFileService boardFileService;
  //  @Value("${upload.path}")
  private String fileDir = "/Users/gimjaehyeon/toytechFiles/";

  @GetMapping("/boardFile/download/{boardFileId}")
  public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long boardFileId)
      throws IOException {
    // BoardFileId에 해당하는 파일 정보 가져오기 (서비스 메서드 호출 등)
    BoardFile boardFile = boardFileService.getBoardFileById(boardFileId);

    // 파일을 읽어올 FileInputStream 생성
    File file = new File(fileDir + boardFile.getSavedFileName());
    FileInputStream fileInputStream = new FileInputStream(file);

    // 다운로드할 파일의 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentDispositionFormData("attachment",
        new String(boardFile.getOriginFileName().getBytes("UTF-8"), "ISO-8859-1"));
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.set("Content-Encoding", "UTF-8");

    // 파일 스트림을 ResponseEntity에 담아서 반환
    return ResponseEntity
        .ok()
        .headers(headers)
        .contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(fileInputStream));
  }


}
