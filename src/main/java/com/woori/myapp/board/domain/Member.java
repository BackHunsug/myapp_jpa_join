package com.woori.myapp.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_MEMBER")
@ToString(exclude = "Board")
public class Member {

	@Id // PK를 숫자로 변경하여 AUTO_INCREMENT 활용
	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 auto_increment 사용

	@Column(name = "member_id")
	private Long id; // 관례적으로 id라는 이름을 사용합니다.

	@Column(name = "user_id", length = 80, unique = true, nullable = false)
	private String userId; // 실제 로그인 아이디 (Unique Key)

	@Column(name="password", nullable = false)
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "email")

	private String email;
	@Column(name = "phone")
	private String phone;


}