package io.toytech.backend.board.controller;

import io.toytech.backend.board.constant.BoardType;
import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.board.service.BoardFileService;
import io.toytech.backend.board.service.BoardService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

  private final BoardService boardService;
  private final BoardFileService boardFileService;


  @PostMapping(value = "/board/new", consumes = "multipart/form-data") //커뮤니티 생성
  public Long createBoard(@RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("boardType") BoardType boardType,
      @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles)
      throws IOException {
    Long id = boardService.createBoard(
        new BoardDto(title, content, boardType, multipartFiles)); //게시글 생성
    return id;
  }

  @GetMapping("/board/{boardId}") //게시판 상세 정보
  public BoardDto getBoard(@PathVariable("boardId") Long id) {
    return boardService.findOne(id);
  }

  @PatchMapping("/board/{boardId}/edit") //커뮤니티 수정 (제목, 본문, 타입, 수정된 날짜가 바뀜)
  public Long updateBoard(@PathVariable("boardId") Long id,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("boardType") BoardType boardType,
      @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles)
      throws IOException {
    boardService.updateBoard(id, new BoardDto(title, content, boardType, multipartFiles));
    return id;
  }


  @DeleteMapping("/board/{boardId}/delete")  //디렉토리에 있는 첨부파일도 삭제해야 함
  public void deleteBoard(@PathVariable("boardId") Long boardId) {
    List<BoardFile> boardFiles = boardService.findOneForDelete(boardId).getBoardFiles();
    boardService.deleteBoard(boardId); //게시글 삭제
    boardFileService.deleteBoardFiles(boardFiles); //디렉토리(로컬)에서 첨부파일 삭제
  }
}
