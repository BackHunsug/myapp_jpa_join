package com.woori.myapp.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id; // PK
    private String title; // 제목
    private String contents; // 내용
    private Long memberId; // 작성자 아이디
    private String writer; //작성자명
    private Integer hits=0; // 조회 수
    private String deleteYn="N"; // 삭제 여부
    private String createdDate;// 생성일
    private String modifiedDate;
    private String name; //
    private String email; //
    private String phone; //

    public static BoardDto fromEntity(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getMember().getName())
                .email(board.getMember().getEmail())
                .memberId(board.getMember().getId())
                .createdDate(board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                .build();
    }

}
