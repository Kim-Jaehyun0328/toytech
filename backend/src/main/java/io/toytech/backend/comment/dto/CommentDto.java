package io.toytech.backend.comment.dto;

import io.toytech.backend.community.dto.CommunityDto;
import io.toytech.backend.member.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

  private Long id;

  private String text; //댓글 내용

  private MemberDto memberDto;
  private CommunityDto communityDto;


}
