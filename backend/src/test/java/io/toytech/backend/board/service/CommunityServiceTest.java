package io.toytech.backend.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityServiceTest {

  @Autowired
  BoardService boardService;

//  @Test
//  @Transactional
//  @Rollback(value = false)
//  public void 커뮤니티_생성() {
//    List<MultipartFile> imageFiles = List.of(
//        new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
//        new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
//    );
//    BoardDto communityDto = new BoardDto("this is title", "this is content",
//        BoardType.BACKEND, imageFiles);
//
//    boardService.createCommunity(communityDto);
//  }
//
//  @Test
//  public void test() {
//
//  }
}