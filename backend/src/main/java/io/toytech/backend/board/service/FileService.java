package io.toytech.backend.board.service;

import io.toytech.backend.board.domain.BoardFile;
import io.toytech.backend.board.dto.BoardDto;
import io.toytech.backend.board.dto.BoardFileDto;
import io.toytech.backend.board.dto.FileDto;
import io.toytech.backend.board.repository.BoardFileRepository;
import io.toytech.backend.board.repository.FileRepository;
import io.toytech.backend.member.repository.MemberRepository;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FileService {

  private final FileRepository fileRepository;
  private final BoardFileRepository boardFileRepository;
  private final MemberRepository memberRepository;

  private String uploadDir = "/Users/gimjaehyeon/toytechFiles"; //나중에 properties 파일에 옮길 예정입니다.

  @Transactional
  public Map<String, Object> saveFile(BoardDto boardDto, Long boardId) {
    List<MultipartFile> multipartFiles = boardDto.getMultipartFiles();

    //결과 Map
    Map<String, Object> result = new HashMap<String, Object>();

    //파일 시퀀스 리스트
    List<Long> fileIds = new ArrayList<Long>();

    try {
      if (multipartFiles != null) {
        if (multipartFiles.size() > 0 && !multipartFiles.get(0).getOriginalFilename().equals("")) {
          for (MultipartFile file1 : multipartFiles) {
            String originalFileName = file1.getOriginalFilename();    //오리지날 파일명
            String extension = originalFileName.substring(
                originalFileName.lastIndexOf("."));    //파일 확장자
            String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

            File targetFile = new File(uploadDir + savedFileName);

            //초기값으로 fail 설정
            result.put("result", "FAIL");

            FileDto fileDto = FileDto.builder()
                .originFileName(originalFileName)
                .savedFileName(savedFileName)
                .uploadDir(uploadDir)
                .ext(extension)
                .size(file1.getSize())
                .contentType(file1.getContentType())
                .build();
            //파일 insert
            io.toytech.backend.board.domain.File file = fileDto.toEntity();
            Long fileId = insertFile(file);
            log.info("fileId={}", fileId);

            try {
              InputStream fileStream = file1.getInputStream();
              FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
              //배열에 담기
              fileIds.add(fileId);
              result.put("fileIdxs", fileIds.toString());
              result.put("result", "OK");
            } catch (Exception e) {
              //파일삭제
              FileUtils.deleteQuietly(targetFile);    //저장된 현재 파일 삭제
              e.printStackTrace();
              result.put("result", "FAIL");
              break;
            }

            BoardFileDto boardFileDto = BoardFileDto.builder()
                .boardId(boardId)
                .build();
            BoardFile boardFile = boardFileDto.toEntity(file);
            insertBoardFile(boardFile);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 파일 저장 db
   */
  @Transactional
  public Long insertFile(io.toytech.backend.board.domain.File file) {
    return fileRepository.save(file).getId();
  }

  @Transactional
  private Long insertBoardFile(BoardFile boardFile) {
    return boardFileRepository.save(boardFile).getId();
  }
}
