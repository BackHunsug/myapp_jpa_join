package com.woori.myapp.board.service;

import com.woori.myapp.board.domain.Board;
import com.woori.myapp.board.domain.BoardDto;
import com.woori.myapp.common.BaseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    public List<BoardDto> getList(BaseDto dto);

    public void write(Board dto);

    public void update(Board dto);
    public void delete(Board dto);

    public BoardDto getView(Long id);

}
