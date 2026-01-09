package com.woori.myapp.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.woori.myapp.board.domain.BoardDto;
import com.woori.myapp.common.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woori.myapp.board.domain.Board;
import com.woori.myapp.board.service.BoardServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/board")
public class BoardController {

	//application.properties   로 부터 값을 읽어와야 한다 
	@Value("${fileUploadPath}")
	String fileUploadPath="";
	
	@Value("${domain}")
	String domain="";
	
	
	@Autowired
	BoardServiceImpl service;
	
	
	//http://localhost:9090/board/list?page=0&size=5
	@GetMapping("/list")
	ResponseEntity<HashMap<String, Object>> getList(Board board, BaseDto dto)
	{
		List<BoardDto> list = service.getList(dto);
		for(int i=0; i<list.size(); i++)
		{
			System.out.println(list.get(i).getTitle());
			System.out.println(list.get(i).getName());
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		resultMap.put("data", list);
		return ResponseEntity.ok(resultMap);
	}

	// http://127.0.0.1:9000/board/view?id=1
	@GetMapping("/view/{id}")
	ResponseEntity<HashMap<String, Object>> getView(@PathVariable(name="id")Long id, Board board)
	{
		BoardDto view = service.getView(id);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("result", "success");
		resultMap.put("data", view);

		return ResponseEntity.ok(resultMap);
	}

	
	//http://localhost:9090/board/write?title=test1&user_id=test&contents=c1&hits=0
	@PostMapping("/write")
	ResponseEntity<HashMap<String, Object>>write(Board board)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try
		{
			service.write(board);
			resultMap.put("result", "success");
		}
		catch(Exception ex)
		{
			resultMap.put("result", "fail");
			resultMap.put("error", ex.getMessage());
		}
		return ResponseEntity.ok(resultMap);
	}
	
	//http://localhost:9090/board/update?title=test1&writer=w1&contents=c1&hits=0&id=3
	@PostMapping("/update")
	ResponseEntity<HashMap<String, Object>>update(Board board)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try
		{
			System.out.println(board.getId());
			service.update(board);
			resultMap.put("result", "success");
		}
		catch(Exception ex)
		{
			resultMap.put("result", "fail");
			resultMap.put("error", ex.getMessage());
		}
		return ResponseEntity.ok(resultMap);
		//ResponseEntity.ok(null).
	}
	
	
	@GetMapping("/delete/{id}")
	ResponseEntity<String>  delete(Board board, @PathVariable long id)
	{
		System.out.println(board.getId());
		 service.delete(board);
		 return ResponseEntity.ok("success");
	}
	
	
	
	
	
	
}
