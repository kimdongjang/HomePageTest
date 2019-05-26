<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$(function(){
		$('#find_id_btn').click(function(){
			location.href='/homepage/member/find_id_form.do';
		});
		$('#find_pw_btn').click(function(){
			location.href='/homepage/member/find_pw_form.do';
		});
	})
</script>

<title>Insert title here</title>
</head>
<body>
	<input type="button" value="ȸ������" onclick="location.href='/homepage/member/memberJoinForm.do'">
	<c:if test="${ member == null }">
		<input type="button" value="�α���" onclick="location.href='/homepage/member/login_form.do'">
	</c:if>
	<c:if test="${member !=null }">
		<a href="/homepage/member/mypage.do">����������(${member.id })</a>
		<input type="button" value="�α׾ƿ�" onclick="location.href='/homepage/member/logout.do'">
	</c:if>
	<span class="w3-button w3-black w3-ripple w3-round" title="���̵� ã��" id="find_id_btn">���̵� ã��</span>
	<span class="w3-button w3-black w3-ripple w3-round" title="��й�ȣ ã��" id="find_pw_btn">��й�ȣ ã��</span>
	<a href="/homepage/board/board_list.do">�Խ��� ����Ʈ</a>
	<a href="/homepage/board/board_write_form.do">�Խ��� �ۼ�</a>
</body>
</html>