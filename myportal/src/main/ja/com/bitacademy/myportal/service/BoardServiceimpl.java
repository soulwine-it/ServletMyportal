package com.bitacademy.myportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.myportal.repository.BoardDao;
import com.bitacademy.myportal.repository.BoardVo;
@Service
public class BoardServiceimpl implements BoardService {
	@Autowired
	BoardDao boardDaoimpl;
	@Override
	public List<BoardVo> getList() {
		List<BoardVo> list= boardDaoimpl.selectAll();
		return list;
	}

	@Override
	public boolean write(BoardVo vo) {
		int insertedCount = boardDaoimpl.insert(vo);
		return 1 == insertedCount;
		
	}

}
