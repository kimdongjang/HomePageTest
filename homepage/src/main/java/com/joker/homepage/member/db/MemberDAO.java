package com.joker.homepage.member.db;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// �ش� Ŭ������ �����ͺ��̽��� �����ϴ� Ŭ�������� ���
// DAO Ŭ������ �����ͺ��̽��� ������ �ϴ� Ŭ������, DML�� ó���ϸ� ���ʿ��� ������ Ŀ�ؼ��� ��ü��
@Repository
public class MemberDAO {
	
	// �������� ���� �䱸 ���װ� ��Ī �Ǵ� ���ø����̼� ���ؽ�Ʈ�󿡼� �ٸ� ���� ã�� �� ���� �������� �ڵ����� ������Ű���� �ϴ� ������
	@Autowired
	SqlSession sqlsession = null;

	// ���̵� �ߺ� �˻�
	public int check_id(String id) throws Exception{
		return sqlsession.selectOne("member.check_id", id);
	}

	// �̸��� �ߺ� �˻�
	public int check_email(String email) throws Exception{
		return sqlsession.selectOne("member.check_email", email);
	}
	
	// ȸ������
	// �� �޼��忡 Ʈ����� ����� ����� ���Ͻ� ��ü�� ������(������ ����-���Ͻ� ���� ����)
	// �� �޼��尡 ȣ��� ��� PlatformTransactionManager�� ����� Ʈ����� ����, ���� ���ο� ���� commit or Rollback
	@Transactional
	public int join_member(MemberDTO member) throws Exception{
		return sqlsession.insert("member.join_member", member);
	}
	
	// �̸��� ����
	@Transactional
	public int approval_member(MemberDTO member) throws Exception{
		return sqlsession.update("member.approval_member", member);
	}
	
	// �α��� �˻�
	public MemberDTO login(String id) throws Exception{
		return sqlsession.selectOne("member.login", id);
	}
	
	// �α��� �������� ����
	@Transactional
	public int update_log(String id) throws Exception{
		return sqlsession.update("member.update_log", id);
	}
}
