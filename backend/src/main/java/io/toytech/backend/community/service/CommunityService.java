package io.toytech.backend.community.service;

import io.toytech.backend.community.domain.Community;
import io.toytech.backend.community.dto.CommunityDto;
import io.toytech.backend.community.repository.CommunityRepository;
import io.toytech.backend.member.domain.Member;
import io.toytech.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

  private final CommunityRepository communityRepository;
  private final MemberRepository memberRepository;

  public CommunityDto findOne(Long id) {
    Community community = communityRepository.findById(id).get();

    CommunityDto communityDto = community.toDto();

    return communityDto;
  }

  @Transactional
  public Long createCommunity(CommunityDto communityDto) {
    Member member = Member.createMember(); //가정의 유저 생성
    memberRepository.save(member);

    Community community = Community.createCommunity(communityDto, member);
    communityRepository.save(community);

    return community.getId();
  }

  @Transactional
  public void updateCommunity(Long id, CommunityDto communityDto) {
    Community community = communityRepository.findById(id).get();
    community.updateCommunity(communityDto);
  }

}
