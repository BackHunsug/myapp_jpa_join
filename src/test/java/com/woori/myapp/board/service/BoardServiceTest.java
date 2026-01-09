package com.woori.myapp.board.service;



import com.woori.myapp.board.domain.BoardDto;
import com.woori.myapp.board.repository.BoardRepository;
import com.woori.myapp.common.BaseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//통합테스트
@SpringBootTest
@Transactional // 테스트 완료 후 자동으로 DB 롤백!
@Slf4j // 추가
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 저장 및 목록 조회 테스트 (N+1 확인용)")
    void getListTest() {
        log.info(">>> 테스트 시작: 게시글 저장 및 목록 조회");
        // 1. Given: 테스트 데이터 준비 (필요시 Member도 먼저 저장해야 함)
        // 실제 DB에 테스트용 Member가 있다고 가정하거나, 여기서 저장 로직을 추가하세요.

        // 2. When: 목록 조회 실행
        BaseDto baseDto = new BaseDto();
        baseDto.setPg(0);
        baseDto.setPgSize(10);

        List<BoardDto> list = boardService.getList(baseDto);

        // 3. Then: 검증
        assertThat(list).isNotNull();
        System.out.println("조회된 게시글 개수: " + list.size());

        // 콘솔 로그에서 'join fetch' 쿼리가 1번만 나가는지 꼭 확인해보세요!
        log.info("종료");
    }

    @Test
    @DisplayName("상세 보기 테스트")
    void getViewTest() {
        // Given: 임의의 ID (실제 DB에 있는 ID를 넣거나 하나를 먼저 저장하세요)
        Long targetId = 1L;

        // When
        BoardDto result = boardService.getView(targetId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(targetId);
    }
}