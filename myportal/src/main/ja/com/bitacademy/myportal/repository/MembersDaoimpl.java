package com.bitacademy.myportal.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.myportal.exception.MembersDaoException;

//실제 클래스명과 다른 이름으로 객체를 생성하고자 할 때
@Repository("membersDao")
public class MembersDaoimpl implements MembersDao {
	@Autowired
	SqlSession sqlSession;
	@Override
	public int insert(MemberVo vo) {
		// 가입을 위한 insert
		int insertedCount =0;
		//TODO: 오류처리
		// NOT NULL 컬럼에 빈 값을 넣을 경우 오류
		try {
		insertedCount = sqlSession.insert("member.insert", vo);
		}catch(Exception e) {
			//명시적인 익셉션으로 변환
			throw new MembersDaoException("가입 중 오류 발생", vo);
		}
		return insertedCount;
	}

	@Override
	public MemberVo selectUser(String email) {
		MemberVo member= sqlSession.selectOne("member.selectByEmail", email);
		return member;
	}

	@Override
	public MemberVo selectUser(String email, String password) {
		//템플릿에 전달한 파라미터가 둘 이상일 경우
		//별도의 VO객체가 없을 때에는 map을 만들어 넘겨줄 수 있다
		//템플릿 안쪽에서는 map 타입으로 설정할 수 있다
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("password", password);
		
		MemberVo member = sqlSession.selectOne("member.selectByEmailAndPassword", paramMap);
		//주의:selectone 메서드는 단일 레코드가 넘어올 때에만 사용할 수 있다
		return member;
	}

}
