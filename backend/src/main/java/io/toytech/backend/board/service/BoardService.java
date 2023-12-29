package io.toytech.backend.board.service;

import io.toytech.backend.board.constant.BoardType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Board findOneReturnEntity(Long id) {
    return boardRepository.findById(id).get();
  }

  @Transactional
  public BoardDto findOneReturnDto(Long id) { //게시글 조회
    Board board = boardRepository.findById(id).get();
    board.updateView(); //조회수 1 증가

    return new BoardDto(board);
  }

  @Transactional
  public Long createBoard(BoardDto boardDto) throws IOException {   //게시글 생성
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
  public void updateBoard(Long id, BoardDto boardDto)
      throws IOException {  //게시글 수정 (첨부 파일을 완전 지웠다 새걸로 갈아끼움)
    Board board = boardRepository.findById(id).get();  //보드 가져오기
    List<BoardFile> beforeUpdateBoardFiles = board.getBoardFiles();  //기존 첨부파일 불러오기 (삭제해야함)
    List<BoardFile> boardFiles = fileStore.storeFiles(boardDto.getMultipartFiles());  //수정한 첨부파일
    board.updateBoard(boardDto, boardFiles); //업데이트

    boardFileService.deleteBoardFiles(beforeUpdateBoardFiles);
    for (BoardFile boardFile : boardFiles) {  //수정한 첨부파일 db에 저장
      boardFile.connetBoardId(board);
    }

    boardFileService.deleteBoardFiles(beforeUpdateBoardFiles); //기존 첨부파일 삭제
    boardFileService.deleteBoardFileInDB(beforeUpdateBoardFiles);

  }

  @Transactional
  public void deleteBoard(Long id) { //게시글 삭제
    boardRepository.deleteById(id);
  }


  @Transactional
  public void updateLikeCount(Board board, int num) { //num이 1이면 좋아요 +1, -1이면 좋아요 -1
    board.updateLikeCount(num);
  }

  public Page<Board> getBoards(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }

  public Page<Board> getBoardsByType(BoardType boardType, Pageable pageable) {
    return boardRepository.findByBoardType(boardType, pageable);
  }
}
