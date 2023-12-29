package io.toytech.backend.board.controller;


import io.toytech.backend.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

  private final LikeService likeService;

  @PostMapping("/{boardId}/{currentMemberId}/like")
  public ResponseEntity<String> likeBoard(@PathVariable("boardId") Long boardId,
      @PathVariable("currentMemberId") Long currentMemberId) {

    likeService.likeBoard(boardId, currentMemberId);

    return ResponseEntity.ok("Liked successfully");
  }

}
