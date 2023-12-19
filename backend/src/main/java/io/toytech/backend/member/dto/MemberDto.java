package io.toytech.backend.member.dto;

import io.toytech.backend.comment.dto.CommentDto;
import io.toytech.backend.community.dto.CommunityDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberDto {

  private Long id;

  private List<CommunityDto> communityDtos = new ArrayList<>();

  private List<CommentDto> commentDtos = new ArrayList<>();
}
