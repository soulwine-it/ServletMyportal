package com.bitacademy.myportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.myportal.exception.MembersDaoException;
import com.bitacademy.myportal.repository.MemberVo;
import com.bitacademy.myportal.service.MembersService;

@Controller
@RequestMapping("/members")
public class MembersController {
	private static final Logger logger= LoggerFactory.getLogger(MembersController.class); 
	@Autowired
	MembersService membersService;
	
	//가입폼 보여주기
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String joinform(@ModelAttribute MemberVo memberVo) {
		return "members/joinform";
	}
	
	//가입 절차 처리
//	@ResponseBody
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinAction(@ModelAttribute @Valid MemberVo vo, BindingResult result, Model model) {
		//@valid 어노테이션이 붙은 모델을 검증작업 수행
		//검증 작업 결과는 BINDINGrESULT로 넘어오게된다
		
		if(result.hasErrors()) {
			//모델 검증 결과 에러가 있을 경우
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error: errors) {
				logger.error("Object Error:" + error);
			}
			
			//BindingResult의 모든 정보를 model에 세팅
			model.addAllAttributes(result.getModel());
			return "members/joinform";
		}
		
		//이러면 이 아래는 수행하지 않음
		boolean isSuccess = false;
		try {
		isSuccess = membersService.join(vo);
		}catch (MembersDaoException e) {
			System.err.println("MembersDao 오류:" + e.getMessage());
			System.err.println("오류 발생 당시의 객체정보:" + e.getMessage());
		}
		if(isSuccess) {
			//가입 성공
			return "redirect:/members/joinsuccess";
		}else {
			//가입 실패
			return "redirect:/members/join";
		}
		
}
	
	//가입 성공시 패이지
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "members/joinsuccess";
	}
	
	//로그인 폼
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginform() {
		return "members/loginform";
	}
	
	//로그인 기능
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAction(@RequestParam(value="email", required=false) String email, 
							@RequestParam(value="password", required=false)String password, HttpSession session) {
		//1차 데이터 검증
		if(email.length() == 0 || password.length() == 0){
			System.err.println("로그인 할 수 없음");
			return "redirect:/members/login";
		}
		MemberVo authMember= membersService.getUser(email, password);
		if(authMember == null) {
			//사용자를 찾지못함
			return "redirect:/members/login";
		}else {
			//사용자를 찾음
			//세션에 등록한 후 홈페이지로 리다이렉트
			session.setAttribute("authMember", authMember);
			return "redirect:/";
		}
	}
	
	//로그 아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutAction(HttpSession session) {
		session.invalidate(); //세션 무효화
		return "redirect:/";
	}
	
	//이메일 체크(json api)
	@ResponseBody
	@RequestMapping("/checkEmail")
	public Object checkEmail(@RequestParam String email) {
		//이메일 중복 체크를 위해서 map을 생성
		//json울 변환하여 반환
		MemberVo vo = membersService.getUser(email);
		boolean isExists = vo != null;
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("exists", isExists);
		
		return map;
	}
}
