package com.bitacademy.myportal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.myportal.repository.BoardDao;
import com.bitacademy.myportal.repository.BoardVo;
import com.bitacademy.myportal.repository.MemberVo;
import com.bitacademy.myportal.service.BoardService;
import com.bitacademy.myportal.service.BoardServiceimpl;



@Controller
@RequestMapping("/board")
public class BoardController {
	//logger설정
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService boardServiceimpl;
//	@Autowired
//	BoardDao boardDaoimpl;
	//리스트
//	@RequestMapping({"","/","/list"})
	@RequestMapping("/list")
	public String list(Model model) {
		logger.debug("Board List");
		List<BoardVo> list = boardServiceimpl.getList();
//		List<BoardVo> list = boardDaoimpl.selectAll();
		logger.debug("Board List:" + list.toString());
		System.out.println("LIST:" + list);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	//작성 폼
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String writeForm(HttpSession session) {
//		MemberVo vo = (MemberVo)session.getAttribute("authMember");
		//개별 요청 메서드에서 매번 세션을 체크하는 것은 바람직하지 않다
		//TODO: ->interceptor로 변경 처리
		
		//사용자 검증
//		if (vo == null) {
//			logger.debug("로그인 하지 않은 사용자의 접근");
//			//로그인 사용자 아님
//			return "redirect:/";
//		}
		return "board/write";
	}
	
	//게시물 등록
//	@ResponseBody
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String writeAction(@ModelAttribute BoardVo vo, HttpSession session) {
		MemberVo authMember = (MemberVo)session.getAttribute("authMember");
//		if(authMember == null) {
//			//로그인 안한 사용자
//			return "redirect:/";
//		}
		
		// ->인증 처리는 인터셉터로 활용
		vo.setMemberNo(authMember.getNo()); // 게시물 작성자
		
		boolean isSuccess = boardServiceimpl.write(vo);
//		boolean isSuccess = boardDaoimpl.insert(vo) == 1;
		
		if(isSuccess) {
			return "redirect:/board/list";
		}else {
			return "redirect:/board/write";
		}
//		return vo.toString();
	}
	
	//수정폼
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modifyForm() {
		return "board/modify";
	}
	
	//게시물 조회
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable Long no) {
		return "board/view";
	}
}
