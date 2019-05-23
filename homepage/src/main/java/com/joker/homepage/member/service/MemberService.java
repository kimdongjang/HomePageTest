package com.joker.homepage.member.service;

import javax.servlet.http.HttpServletResponse;

import com.joker.homepage.member.db.MemberDTO;

//Service Ŭ������ �����ϰ� interface�� ����
public interface MemberService {
	public void check_id(String id, HttpServletResponse response) throws Exception;
	
	public void check_email(String email, HttpServletResponse response) throws Exception;

	public int join_member(MemberDTO member, HttpServletResponse response) throws Exception;
	
	public String create_key() throws Exception;
	
	public void send_mail(MemberDTO member) throws Exception;	
	
	public void approval_member(MemberDTO member, HttpServletResponse response) throws Exception;
	
	public MemberDTO login(MemberDTO member, HttpServletResponse response) throws Exception;
	
}