package io.toytech.backend.board.service;

import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.dto.BoardFileDto;
import io.toytech.backend.board.repository.BoardFileRepository;
import jakarta.persistence.PreRemove;
import java.io.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardFileService {

  private final BoardFileRepository boardFileRepository;
  //  @Value("${upload.path}")
  private String fileDir = "/Users/gimjaehyeon/toytechFiles/";

  public void createBoardFile(BoardFile boardFile) {
    boardFileRepository.save(boardFile);
  }

  public BoardFile getBoardFileById(Long boardFileId) { //uses or overrides a deprecated API. (체크하자)
    return boardFileRepository.findById(boardFileId).get();
  }


  @PreRemove
  public void deleteBoardFiles(List<BoardFileDto> boardFiles) {
    for (BoardFileDto boardFile : boardFiles) {
      deleteBoardFile(boardFile.getSavedFileName());
    }
  }

  private void deleteBoardFile(String savedFileName) {
    File file = new File(fileDir + savedFileName);
    if (file.exists()) {
      if (file.delete()) {
        log.info("로컬 파일 삭제 성공");
      } else {
        log.info("로컬 파일 삭제 실패");
      }
    }
  }

}
