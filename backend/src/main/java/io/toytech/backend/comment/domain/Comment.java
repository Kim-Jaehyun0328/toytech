package io.toytech.backend.comment.domain;

import io.toytech.backend.comment.dto.CommentDto;
import io.toytech.backend.community.domain.Community;
import io.toytech.backend.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue
  @Column(name = "comment_id")
  private Long id;

  private String text; //댓글 내용

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "community_id")
  private Community community;

  public CommentDto toDto() {
    CommentDto commentDto = new CommentDto();
    commentDto.setId(id);
    commentDto.setText(text);
    commentDto.setMemberDto(member.toDto());
    commentDto.setCommunityDto(community.toDto());
    return commentDto;
  }
}
