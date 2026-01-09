package com.woori.myapp.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.myapp.board.domain.Board;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<Board, Long>{

	// @EntityGraph를 쓰거나 직접 JPQL로 Fetch Join을 작성합니다.
	@Query("select b from Board b join fetch b.member")
	List<Board> findAllWithMember();

	@Query(value = "select b from Board b join fetch b.member",
			countQuery = "select count(b) from Board b")
	Page<Board> findAllWithMember(Pageable pageable);



	List<Board> findByTitleOrderByIdDesc(String title, Pageable pageable);
	List<Board> findByOrderByIdDesc(Pageable pageable);
	List<Board> findByTitleLike(String title, Pageable pageable);

	//findByOrderBySeatNumberAsc
	//@Query("select b from BoardDto b left join fetch b.user_id")
	//List<BoardDto> findFetchAll();
//
	 //쿼리작성시 실제 테이블명을 사용하는게 아니라 도메인 이름을 써야 한다 
//	 @Query("SELECT m FROM BoardDto as m WHERE m.title LIKE %:title% or m.writer LIKE %:writer% or m.contents like %:contents%  order by m.id desc")
//	 List<BoardDto> searchByTitleWriterContentsLike(@Param("title")String title, 
//			 										@Param("writer") String writer,
//			                                        @Param("contents") String contents, 
//			                                        Pageable pageable);
//	 
	 
	 
	 //List<BoardDto> findByTitleContentsLike(String title, Pageable pageable);
   //Page<Board> findAllByAccount(Pageable pageable, String account);
   // 2. 상태 조건 find
  // Page<User> findAllByStatus(Pageable pageable, UserStatus status);
   // 3. 계정, 상태 조건 find
  // Page<User> findAllByAccountAndStatus(Pageable pageable, String account, UserStatus status);
   // 4. Specification를 이용하여 동적으로 조건을 세팅하여 find
  // Page<User> findAll(Specification<User> spec, Pageable pageable);
   
}
