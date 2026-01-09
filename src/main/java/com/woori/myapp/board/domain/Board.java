package com.woori.myapp.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "TB_BOARD")
@ToString(exclude = "member") // 연관관계 필드는 ToString에서 제외하는 것이 좋습니다 (순환 참조 방지)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL은 IDENTITY 방식을 사용합니다.
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "LONGTEXT") // MySQL에서 큰 텍스트를 저장하기 위해 타입을 명시합니다.
    private String contents;

    @Column(columnDefinition = "integer default 0")
    private Integer hits ;

    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    private String deleteYn;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Column(updatable = false)
    private LocalDateTime modifiedDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    private BoardDto convertToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .name(board.getMember().getName())
                .email(board.getMember().getEmail())
                .build();
    }
}