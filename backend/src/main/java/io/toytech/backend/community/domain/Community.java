package io.toytech.backend.community.domain;

import io.toytech.backend.comment.domain.Comment;
import io.toytech.backend.community.constant.CommunityType;
import io.toytech.backend.community.dto.CommunityDto;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id", "title", "content", "views", "likes", "dislikes", "communityType"})
public class Community {

  @Id
  @GeneratedValue
  @Column(name = "community_id")
  private Long id;

  private String title;
  private String content;

  private int views = 0;
  private int likes = 0;
  private int dislikes = 0;

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Enumerated(EnumType.STRING)
  private CommunityType communityType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "community")
  private List<Comment> comments = new ArrayList<>();

  public static Community createCommunity(CommunityDto communityDto, Member member) {
    Community community = new Community();
    community.member = member;
    community.title = communityDto.getTitle();
    community.content = communityDto.getContent();
    community.communityType = communityDto.getCommunityType();
    community.createdAt = LocalDateTime.now();
    community.modifiedAt = LocalDateTime.now();

    return community;
  }

  public void updateCommunity(CommunityDto communityDto) {
    title = communityDto.getTitle();
    content = communityDto.getContent();
    modifiedAt = LocalDateTime.now();
    communityType = communityDto.getCommunityType();
  }

  public CommunityDto toDto() {
    CommunityDto communityDto = new CommunityDto();
    communityDto.setId(id);
    communityDto.setTitle(title);
    communityDto.setContent(content);
    return communityDto;
  }

}
