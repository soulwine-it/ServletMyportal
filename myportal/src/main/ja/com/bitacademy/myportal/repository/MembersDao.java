package com.bitacademy.myportal.repository;

public interface MembersDao {
	public int insert(MemberVo vo); //가입 insert
	public MemberVo selectUser(String email); //중복 검사용 select
	public MemberVo selectUser(String email, String password); // login용 select
}
