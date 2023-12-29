package io.toytech.backend.member.service;

import io.toytech.backend.member.domain.Member;
import io.toytech.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;

  public Member findOneReturnEntity(Long memberId) {
    return memberRepository.findById(memberId).get();
  }

}
