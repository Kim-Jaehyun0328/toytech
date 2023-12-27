package io.toytech.backend.board.service;

import io.toytech.backend.board.domain.Board;
import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.board.repository.BoardRepository;
import io.toytech.backend.member.domain.Member;
import io.toytech.backend.member.repository.MemberRepository;
import io.toytech.backend.util.FileStore;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;
  private final FileStore fileStore;
  private final BoardFileService boardFileService;


  @Transactional
  public BoardDto findOne(Long id) {
    Board board = boardRepository.findById(id).get();
    board.updateView(); //조회수 1 증가

    return new BoardDto(board);
  }

  @Transactional
  public Long createBoard(BoardDto boardDto) throws IOException {
    Member member = Member.createMember(); //가정의 유저 생성
    memberRepository.save(member);

    List<BoardFile> boardFiles = fileStore.storeFiles(boardDto.getMultipartFiles());
    Board board = boardDto.toEntity(member, boardFiles);  //보드 엔티티 생성

    boardRepository.save(board);

    for (BoardFile boardFile : boardFiles) { //boardFile에 board연관 관계 설정 및 저장
      boardFile.connetBoardId(board);
      boardFileService.createBoardFile(boardFile);
    }

    return board.getId();
  }

  @Transactional
  public void updateBoard(Long id, BoardDto boardDto) {
    Board board = boardRepository.findById(id).get();
    board.updateBoard(boardDto);
  }

  @Transactional
  public void deleteBoard(Long id) {
    boardRepository.deleteById(id);
  }

}
