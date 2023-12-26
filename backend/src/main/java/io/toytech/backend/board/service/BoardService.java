package io.toytech.backend.board.service;

import io.toytech.backend.board.domain.Board;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.board.repository.BoardRepository;
import io.toytech.backend.member.domain.Member;
import io.toytech.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;


  @Transactional
  public BoardDto findOne(Long id) {
    Board board = boardRepository.findById(id).get();
    board.updateView(); //조회수 1 증가
    return new BoardDto(board);
  }

  @Transactional  //첨부파일 없는 게시글 생성
  public Long createBoard(BoardDto boardDto) {
    Member member = Member.createMember(); //가정의 유저 생성
    memberRepository.save(member);

    Board board = io.toytech.backend.board.domain.Board.createBoard(boardDto, member);
    boardRepository.save(board);

    return board.getId();
  }

//  @Transactional  //첨부파일 있는 게시글 생성(보류)
//  public void createCommunity(final BoardDto communityDto) {
//    Member member = Member.createMember();
//    memberRepository.save(member);
//
//    Board community = Board.createCommunity(communityDto, member);
//    boardRepository.save(community);
//
//    List<FileRequest> files = fileUtils.uploadFiles(community.getFiles());
//    fileService.saveFiles(community, files);
//  }

  @Transactional
  public void updateBoard(Long id, BoardDto boardDto) {
    Board board = boardRepository.findById(id).get();
    board.updateBoard(boardDto);
  }

}
