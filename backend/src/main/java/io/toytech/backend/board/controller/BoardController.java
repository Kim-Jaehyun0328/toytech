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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("board")
  public ResponseEntity<Page<BoardDto>> boardList(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sort,
      @RequestParam(defaultValue = "desc") String sortOrder) {
    Sort.Order order = new Sort.Order(Direction.fromString(sortOrder), sort);
    Pageable pageable = PageRequest.of(page, size, Sort.by(order));
    Page<BoardDto> boardPage = boardService.getBoards(pageable).map(BoardDto::new);
    return ResponseEntity.ok(boardPage);
  }

  @GetMapping("/board/{boardType}")
  public ResponseEntity<Page<BoardDto>> boardListByType(
      @PathVariable String boardType,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sort,
      @RequestParam(defaultValue = "desc") String sortOrder) {

    // BoardType.valueOf(boardType)를 통해 문자열을 Enum으로 변환
    BoardType type = BoardType.valueOf(boardType);

    Sort.Order order = new Sort.Order(Direction.fromString(sortOrder), sort);
    Pageable pageable = PageRequest.of(page, size, Sort.by(order));
    Page<BoardDto> boardPage = boardService.getBoardsByType(type, pageable).map(BoardDto::new);
    return ResponseEntity.ok(boardPage);
  }

  @PostMapping(value = "/board/new", consumes = "multipart/form-data") //커뮤니티 생성
  public ResponseEntity<Long> createBoard(@RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("boardType") BoardType boardType,
      @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles)
      throws IOException {
    Long id = boardService.createBoard(
        new BoardDto(title, content, boardType, multipartFiles)); //게시글 생성
    return ResponseEntity.ok(id);
  }

  @GetMapping("/board/{boardId}") //게시판 상세 정보
  public ResponseEntity<BoardDto> getBoard(@PathVariable("boardId") Long id) {
    return ResponseEntity.ok(boardService.findOneReturnDto(id));
  }

  @PatchMapping("/board/{boardId}/edit") //커뮤니티 수정 (제목, 본문, 타입, 수정된 날짜가 바뀜)
  public ResponseEntity<Long> updateBoard(@PathVariable("boardId") Long id,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("boardType") BoardType boardType,
      @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles)
      throws IOException {
    boardService.updateBoard(id, new BoardDto(title, content, boardType, multipartFiles));
    return ResponseEntity.ok(id);
  }


  @DeleteMapping("/board/{boardId}/delete")  //디렉토리에 있는 첨부파일도 삭제해야 함
  public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
    List<BoardFile> boardFiles = boardService.findOneReturnEntity(boardId).getBoardFiles();
    boardService.deleteBoard(boardId); //게시글 삭제
    boardFileService.deleteBoardFiles(boardFiles); //디렉토리(로컬)에서 첨부파일 삭제
    return ResponseEntity.noContent().build();
  }
}
