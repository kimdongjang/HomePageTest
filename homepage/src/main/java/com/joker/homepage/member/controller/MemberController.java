package com.joker.homepage.member.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joker.homepage.member.db.MemberDTO;
import com.joker.homepage.member.service.MemberServiceImp;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	private MemberServiceImp service;
	
	// �� ������ /member/memberJoinForm.do�� ��û�� ������ /member/memberJoinForm.jsp ������ �������ִ� ����
	@RequestMapping(value = "/memberJoinForm.do")
	public String memberJoinForm() throws Exception{
		return "/member/memberJoinForm";
	}
	
	// ���̵� �ߺ� �˻�(AJAX)
	@RequestMapping(value = "/check_id.do", method = RequestMethod.POST)
	public void check_id(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		service.check_id(id, response);
	}

	// �̸��� �ߺ� �˻�(AJAX)
	@RequestMapping(value = "/check_email.do", method = RequestMethod.POST)
	public void check_email(@RequestParam("email") String email, HttpServletResponse response) throws Exception{
		service.check_email(email, response);
	}
	
	// ȸ�� ����
	@RequestMapping(value = "/join_member.do", method = RequestMethod.POST)
	public String join_member(@ModelAttribute MemberDTO member, RedirectAttributes rttr, HttpServletResponse response) throws Exception{
		rttr.addFlashAttribute("result", service.join_member(member, response));
		return "redircet:./memberJoinForm.do";
	}
	
	// ȸ�� ����
	@RequestMapping(value="/approval_member.do", method=RequestMethod.POST)
	public void approval_member(@ModelAttribute MemberDTO member, HttpServletResponse response) throws Exception{
		service.approval_member(member, response);
	}
	
	// �α��� �� �̵�
	@RequestMapping(value="/login_form.do", method=RequestMethod.GET)
	public String login_form() throws Exception{
		return "/member/loginForm";
	}
	

	// �α���
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@ModelAttribute MemberDTO member, HttpSession session, HttpServletResponse response) throws Exception{
		member = service.login(member, response);
		session.setAttribute("member", member);
		return "index";
	}
}
