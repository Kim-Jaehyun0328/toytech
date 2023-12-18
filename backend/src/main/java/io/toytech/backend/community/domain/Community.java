package io.toytech.backend.community.domain;

import io.toytech.backend.comment.domain.Comment;
import io.toytech.backend.community.constant.CommunityType;
import io.toytech.backend.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community {

  @Id
  @GeneratedValue
  @Column(name = "community_id")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "community")
  private List<Comment> comments = new ArrayList<>();


}
