package io.toytech.backend.board.dto;


import io.toytech.backend.board.constant.BoardType;
import io.toytech.backend.board.domain.Board;
import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.comment.dto.CommentDto;
import io.toytech.backend.member.domain.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {

  private Long id;

  private String title;
  private String content;

  private int views;
  private int likeCount;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Enumerated(EnumType.STRING)
  private BoardType boardType;


  //  private MemberDto memberDto;
  private String name; //글쓴이 이름 lazy 확인하려고

  private List<CommentDto> comments;

  private List<BoardFileDto> boardFiles = new ArrayList<>();

  private List<MultipartFile> multipartFiles = new ArrayList<>();


  public BoardDto(String title, String content, BoardType boardType,  //처음 게시글 생성할 때
      List<MultipartFile> multipartFiles) {
    this.title = title;
    this.content = content;
    this.boardType = boardType;
    this.multipartFiles = multipartFiles;
  }

  public BoardDto(Board board) { //toDto  바깥으로 조회해야할때
    this.id = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.views = board.getViews();
    this.likeCount = board.getLikeCount();
    this.createdAt = board.getCreatedAt();
    this.modifiedAt = board.getModifiedAt();
    this.boardType = board.getBoardType();
//    this.memberDto = new MemberDto(board.getMember()); //lazy 로딩인지 확인해야 됨  ===> 무한 루프에 빠짐
    this.name = board.getMember().getName();
    this.comments = board.getComments().stream() //이것도 무한 루프인 지 확인해봐야함
        .map(c -> new CommentDto(c)).collect(Collectors.toList());
    this.boardFiles = board.getBoardFiles().stream()
        .map(bf -> new BoardFileDto(bf)).collect(Collectors.toList());
  }

  public Board toEntity(Member member, List<BoardFile> boardFile) {
    return Board.builder()
        .member(member)
        .title(this.title)
        .content(this.content)
        .boardType(this.boardType)
        .boardFiles(boardFile)
        .build();
  }


}
