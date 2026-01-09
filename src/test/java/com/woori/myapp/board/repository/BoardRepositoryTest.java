package com.woori.myapp.board.repository;

import com.woori.myapp.MyappJpaApplication;
import com.woori.myapp.board.domain.Board;
import com.woori.myapp.board.domain.Member;
import com.woori.myapp.config.JpaAuditConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@DataJpaTest // 이전방식, 자동설정때문에 H2사용시 유리, 실db사용안됨
@SpringBootTest (classes = MyappJpaApplication.class)
//모든 설정이 자동이라 편하나 테스트 데이터가 롤백 되지 않는다
//@Transactional을 추가하자
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB(MySQL 등)로 테스트하려면 추가
@Import(JpaAuditConfig.class) // Auditing 설정을 위해 필요할 수 있음
@Transactional
/*
@TestPropertySource(locations = "classpath:application.properties")
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url=jdbc:mysql://localhost:3306/mydb2",
        "spring.datasource.username=user02",
        "spring.datasource.password=1234"
})*/
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 저장 및 회원 조인 테스트")
    void saveAndJoinTest() {
        // 1. Given: 회원 생성 및 저장
        Member member = Member.builder()
                .userId("tester")
                .password("1234")
                .name("테스터")
                .build();
        Member savedMember = memberRepository.save(member);

        // 2. Given: 게시글 생성 (저장된 회원의 ID 참조)
        Board board = Board.builder()
                .title("테스트 제목")
                .contents("테스트 내용")
                .member(savedMember)
                .build();

        // 3. When: 게시글 저장
        Board savedBoard = boardRepository.save(board);

        // 4. Then: 검증
        assertThat(savedBoard.getId()).isNotNull();
        assertThat(savedBoard.getCreatedDate()).isNotNull(); // Auditing 작동 확인

        // 조인 확인 (Fetch Join이 설정된 메서드 호출 가정)
        Board foundBoard = boardRepository.findById(savedBoard.getId()).orElseThrow();
        System.out.println("작성자 이름: " + foundBoard.getMember().getName());

        assertThat(foundBoard.getMember().getName()).isEqualTo("테스터");
    }
}
