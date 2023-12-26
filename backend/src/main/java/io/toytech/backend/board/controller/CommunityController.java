package io.toytech.backend.board.controller;

import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

  private final BoardService boardService;

  @PostMapping("/community/new") //커뮤니티 생성 첨부 파일이 없는
  public Long createBoard(@RequestBody BoardDto boardDto) {
    Long id = boardService.createBoard(boardDto);
    return id;
  }

  @GetMapping("/community/{communityId}")
  public BoardDto getBoard(@PathVariable("communityId") Long id) {
    return boardService.findOne(id);
  }

  @PatchMapping("/community/{communityId}/edit") //커뮤니티 수정 (제목, 본문, 타입, 수정된 날짜가 바뀜)
  public Long updateBoard(@PathVariable("communityId") Long id,
      @RequestBody BoardDto boardDto) {
    boardService.updateBoard(id, boardDto);
    return id;
  }
}
