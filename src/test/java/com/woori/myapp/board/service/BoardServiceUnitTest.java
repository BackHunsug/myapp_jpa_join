package com.woori.myapp.board.service;

import com.woori.myapp.board.domain.Board;
import com.woori.myapp.board.domain.BoardDto;
import com.woori.myapp.board.domain.Member;
import com.woori.myapp.board.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Mockito를 사용하여 단위 테스트 수행
class BoardServiceUnitTest {

    @Mock
    private BoardRepository boardRepository; // 가짜 객체 생성

    @InjectMocks
    private BoardServiceImpl boardService; // 가짜 객체를 주입받을 실제 서비스

    @Test
    @DisplayName("상세 보기 성공 테스트 (단위 테스트)")
    void getView_Success() {
        // 1. Given (준비)
        // 가짜 데이터 생성
        Member member = Member.builder().name("홍길동").email("test@test.com").build();
        Board board = Board.builder()
                .id(1L)
                .title("테스트 제목")
                .contents("테스트 내용")
                .member(member)
                .build();

        // Mock 객체의 동작 정의 (가짜 DB 응답 설정)
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));

        // 2. When (실행)
        BoardDto result = boardService.getView(1L);

        // 3. Then (검증)
        //성공시 아무 것도 출력하지 않는다 실패시 출력을 한다
        assertThat(result.getTitle()).isEqualTo("테스트 제목");
        assertThat(result.getName()).isEqualTo("홍길동");

        // 실제로 repository의 findById가 호출되었는지 확인
        verify(boardRepository).findById(anyLong());
    }
}