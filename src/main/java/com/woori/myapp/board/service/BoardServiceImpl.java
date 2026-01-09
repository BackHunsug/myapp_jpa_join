package com.woori.myapp.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.woori.myapp.board.domain.BoardDto;
import com.woori.myapp.common.BaseDto;
import com.woori.myapp.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woori.myapp.board.domain.Board;
import com.woori.myapp.board.repository.BoardRepository;

	
	
@Service
@Transactional
@RequiredArgsConstructor // 클래스에 final이 있을경우에 객체를 주입하는 생성자를 만든다
public class BoardServiceImpl implements BoardService{

	private final BoardRepository repository;
		  
	public List<BoardDto> getList(BaseDto dto){
		int page = dto.getPg();
		int size = dto.getPgSize();

		Sort sort = Sort.by(Sort.Direction.fromString("desc"), "id");
		Pageable pageable = PageRequest.of(page, size, sort); // 정렬 정보 포함

		//문제가 있는 코딩방식임 N+1문제 발생
		//findAll로 전체 데이터를 가져 온 후 getMember().getName()을 다시 실행한다
		//N+1문제라고 본다.
		//해결책 - 쿼리를 바꾼다 . Fetch Join 은 한방 쿼리이다.
		//repository에 별도의 함수를 추가한다
		/*List<Board> list = repository.findAll(pageable).getContent();
		for(int i=0; i<list.size(); i++)
		{
			System.out.println(list.get(i).getTitle());
			System.out.println(list.get(i).getMember().getName());
			//불합리한 코드
		}*/

		//DB에서 게시글과 멤버 정보를 Join해서 한 바구니에 다 담아왔습니다.
		//따라서 루프 안에서 getMember().getName()을 호출해도 DB에 다시 가지 않고, 이미 메모리에 올라와 있는 정보를 그냥 꺼내 쓰기만 합니다. 쿼리는 딱 1번만 나갑
		List<Board> list = repository.findAll(pageable).getContent();
		for(int i=0; i<list.size(); i++)
		{
			System.out.println(list.get(i).getTitle());
			System.out.println(list.get(i).getMember().getName());
			//불합리한 코드
		}

		return list.stream()
				.map(BoardDto::fromEntity)
				.collect(Collectors.toList());

		//검색을 해야 할경우에
		//return repository.findByTitleLike("%"+board.getTitle()+"%", pageable);
		//return repository.searchByTitleLike(board.getTitle(), board.getContents());
		//return repository.searchByTitleWriterContentsLike(board.getTitle(), board.getWriter(), board.getContents(), pageable);
	
	}
	  

	//둘다 
	@Transactional
	public void write(Board dto) {
	    repository.save(dto);
	}
	@Transactional
	public void update(Board dto) {
		System.out.println(dto.getId());
		Optional<Board> resultDto = repository.findById(dto.getId());
		resultDto.orElseThrow(() -> new RuntimeException(dto.getId() + "가 존재하지 않습니다"));
		repository.save(dto);  // 게시글 수정
	}

	public void delete(Board dto) {
		 //BoardDto	dto = repository.findById(id).get();
		Optional<Board> resultDto = repository.findById(dto.getId());
		resultDto.orElseThrow(() -> new RuntimeException(dto.getId() + "가 존재하지 않습니다"));
		repository.delete(dto);
	}

	public BoardDto getView(Long id){

		Optional<Board> resultDto = repository.findById(id);

		resultDto.orElseThrow(() -> new RuntimeException(id + "가 존재하지 않습니다"));
		Board board = resultDto.get();
		BoardDto dto = 	BoardDto.builder()
				.id(board.getId())
				.title(board.getTitle())
				.contents( board.getContents())
				//.user_id( board.getUser_id())
				.name(board.getMember().getName())
				.email(board.getMember().getEmail())
				.build();

		return dto;
	}

}
