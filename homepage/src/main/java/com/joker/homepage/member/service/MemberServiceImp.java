package com.joker.homepage.member.service;

import java.io.PrintWriter;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import com.joker.homepage.member.db.MemberDTO;
import com.joker.homepage.member.db.MemberDAO;

//Repository�� ���� �����ͺ��̽����� �����͸� ������ �� ��Ʈ�ѷ����� ������ �ִ� Ŭ�������� ����.
@Service
public class MemberServiceImp implements MemberService {
	
	@Inject
	private MemberDAO manager;
	
	// �������̽� ����
	// ���̵� �ߺ� �˻�(AJAX)
	@Override
	public void check_id(String id, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		out.println(manager.check_id(id));
		out.close();
	}

	// �̸��� �ߺ� �˻�(AJAX)
	@Override
	public void check_email(String email, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(manager.check_email(email));
		out.close();
	}


	// ȸ������
	@Override
	public int join_member(MemberDTO member, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (manager.check_id(member.getId()) == 1) {
			out.println("<script>");
			out.println("alert('������ ���̵� �ֽ��ϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return 0;
		} else if (manager.check_email(member.getEmail()) == 1) {
			out.println("<script>");
			out.println("alert('������ �̸����� �ֽ��ϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return 0;
		} else {
			// ����Ű set
            System.out.println("\n >>>>>>>>> ȸ������ �õ��� \n");
			member.setApproval_key(create_key());
			manager.join_member(member);
			// ���� ���� �߼�
			send_mail(member);
			return 1;
		}
	}
	
	@Override
	public String create_key() throws Exception{
		String key = "";
		Random rd = new Random();
		
		for(int i = 0; i<4; i++) {
			key += rd.nextInt(10);
		}
		return key;
	}
	
	@Override
	public void send_mail(MemberDTO member) throws Exception{
		// Mail Server ����
		String charSet= "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "naru_oo";
		String hostSMTPpwd = "exl2y79112t!";
		int SMTPPort = 465;
		int SSLPort = 587;
		
		// ������ ��� EMail, ����, ����
		String fromEmail = member.getEmail();
		String fromName = "Spring Homepage";
		String subject = "";
		String msg = "";

        System.out.println("\n >>>>>>>>> ������ ���� "+ member.getEmail() +" \n");
		// ȸ������ ���� ����
		subject = "Spring Homepage ȸ������ ���� �����Դϴ�.";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += member.getId() + "�� ȸ�������� ȯ���մϴ�.</h3>";
		msg += "<div style='font-size: 130%'>";
		msg += "�ϴ��� ���� ��ư Ŭ�� �� ���������� ȸ�������� �Ϸ�˴ϴ�.</div><br/>";
		msg += "<form method='post' action='http://localhost:8080/homepage/member/approval_member.do'>";
		msg += "<input type='hidden' name='email' value='" + member.getEmail() + "'>";
		msg += "<input type='hidden' name='approval_key' value='" + member.getApproval_key() + "'>";
		msg += "<input type='submit' value='����'></form><br/></div>";
		
		// �޴� ��� E-Mail �ּ�
		String mail = member.getEmail();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSLOnConnect(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(SSLPort);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setStartTLSEnabled(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("���Ϲ߼� ���� : " + e);
		}
	}
	
	// �̸��� ����
	@Override
	public void approval_member(MemberDTO member, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// �̸��� ������ �������� ���
		if(manager.approval_member(member) == 0) {
			out.println("<script>");
			out.println("alert('�߸��� �����Դϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		// �̸��� ������ �������� ���
		else{
			out.println("<script>");
			out.println("alert('������ �Ϸ�Ǿ����ϴ�. �α��� �� �̿��ϼ���.');");
			out.println("location.href='../index.jsp';");
			out.println("</script>");
			out.close();
		}
	}
	
	// �α���
	@Override
	public MemberDTO login(MemberDTO member, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// ��ϵ� ���̵� ������
		if(manager.check_id(member.getId()) == 0) {
			out.println("<script>");
			out.println("alert('��ϵ� ���̵� �����ϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		} else {
			String pw = member.getPw();
			member = manager.login(member.getId());
			// ��й�ȣ�� �ٸ� ���
			if(!member.getPw().equals(pw)) {
				out.println("<script>");
				out.println("alert('��й�ȣ�� �ٸ��ϴ�.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			// �̸��� ������ ���� ���� ���
			}else if(!member.getApproval_status().equals("true")) {
				out.println("<script>");
				out.println("alert('�̸��� ���� �� �α��� �ϼ���.');");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
            // �α��� ���� ������Ʈ �� ȸ������ ����			
			}else {
				manager.update_log(member.getId());
				return member;
			}
		}
	}
}