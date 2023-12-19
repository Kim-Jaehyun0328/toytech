package io.toytech.backend.community.controller;

import io.toytech.backend.community.dto.CommunityDto;
import io.toytech.backend.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

  private final CommunityService communityService;

  @PostMapping("/community/new") //커뮤니티 생성
  public Long createCommunity(@RequestBody CommunityDto communityDto) {
    Long id = communityService.createCommunity(communityDto);
    return id;
  }

  @GetMapping("/community/{communityId}")
  public CommunityDto getCommunity(@PathVariable("communityId") Long id) {
    return communityService.findOne(id);
  }

  @PatchMapping("/community/{communityId}/edit") //커뮤니티 수정 (제목, 본문, 타입, 수정된 날짜가 바뀜)
  public Long updateCommunity(@PathVariable("communityId") Long id,
      @RequestBody CommunityDto communityDto) {
    communityService.updateCommunity(id, communityDto);
    return id;
  }
}
