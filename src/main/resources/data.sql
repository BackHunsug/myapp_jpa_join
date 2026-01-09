-- 1. 제약 조건 체크를 잠시 끄기 (가장 확실한 방법)
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 기존 데이터 삭제 (자식 -> 부모 순서 권장)
DELETE FROM TB_BOARD;
DELETE FROM TB_MEMBER;

-- 3. (선택사항) 자동 증가(Auto Increment) 번호 초기화
-- 다시 실행했을 때 ID 1번부터 시작하게 하고 싶을 때 사용합니다.
ALTER TABLE TB_BOARD AUTO_INCREMENT = 1;
ALTER TABLE TB_MEMBER AUTO_INCREMENT = 1;

INSERT INTO TB_MEMBER (user_id, password, name, email, phone)
VALUES ('admin', '1234', '관리자', 'admin@test.com', '010-1111-1111');

INSERT INTO TB_MEMBER (user_id, password, name, email, phone)
VALUES ('user01', '1234', '홍길동', 'hong@test.com', '010-2222-2222');

INSERT INTO TB_MEMBER (user_id, password, name, email, phone)
VALUES ('user02', '1234', '김철수', 'kim@test.com', '010-2222-2222');

-- 2. Board 데이터 (member_id 컬럼에 숫자를 넣어야 에러가 안 납니다)
-- 홍길동(1번)이 쓴 글
INSERT INTO TB_BOARD (title, contents, member_id, hits, deleteYn, createdDate, modifiedDate)
VALUES ('첫 번째 게시글입니다', '안녕하세요, 반가워요.', 1, 0, 'N', NOW(), NOW());

-- 김철수(2번)가 쓴 글
INSERT INTO TB_BOARD (title, contents, member_id, hits, deleteYn, createdDate, modifiedDate)
VALUES ('두 번째 질문있어요', 'JPA 조인이 어렵네요.', 2, 5, 'N', NOW(), NOW());
