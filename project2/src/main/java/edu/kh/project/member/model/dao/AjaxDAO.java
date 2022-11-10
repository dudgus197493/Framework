package edu.kh.project.member.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // DB 연결하는 역할 + bean 등록
public class AjaxDAO {
	
	// mabatis : SqlSession
	// mabatis + spring TX 제어 : SqlSessionTemplate
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int emailDupCheck(String memberEmail) {
		return sqlSession.selectOne("ajaxMapper.emailDupCheck", memberEmail);
	}	
}
