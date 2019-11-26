package com.bitacademy.myportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.myportal.repository.GuestbookDao;
import com.bitacademy.myportal.repository.GuestbookDaoimpl;
import com.bitacademy.myportal.repository.GuestbookVo;
@Service
public class GuestbookServiceimpl implements GuestbookService {
	@Autowired
	GuestbookDao guestbookDaoimpl;
	
	@Override
	public List<GuestbookVo> getMessageList() {
		List<GuestbookVo> list = guestbookDaoimpl.selectAll();
		return list;
	}

	@Override
	public boolean writeMessage(GuestbookVo vo) {
		int insertedCount = guestbookDaoimpl.insert(vo);
		return 1 == insertedCount;
	}

	@Override
	public boolean deleteMessage(GuestbookVo vo) {
		int deletedCount = guestbookDaoimpl.delete(vo);
	
		return 1 == deletedCount;
	}

}
