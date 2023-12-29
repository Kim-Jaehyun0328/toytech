package io.toytech.backend.community.dto;


import io.toytech.backend.comment.dto.CommentDto;
import io.toytech.backend.community.constant.CommunityType;
import io.toytech.backend.member.dto.MemberDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDto {

  private Long id;

  private String title;
  private String content;

  private int views;
  private int likes;
  private int dislikes;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Enumerated(EnumType.STRING)
  private CommunityType communityType;


  private MemberDto memberDto;

  private List<CommentDto> commentDtos = new ArrayList<>();

}
