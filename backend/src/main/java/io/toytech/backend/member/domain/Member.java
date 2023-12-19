package io.toytech.backend.member.domain;

import io.toytech.backend.comment.domain.Comment;
import io.toytech.backend.community.domain.Community;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @OneToMany(mappedBy = "member")
  private List<Community> communities = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Comment> comments = new ArrayList<>();


  public static Member createMember() {
    Member member = new Member();
    return member;
  }
}
