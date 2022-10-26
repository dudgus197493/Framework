package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.vo.Member;

@Repository // 퍼시스턴스 레이어, 영속성 (파일, DB)을 가지는 클래스
public class MemberDAO {
	private int number = 0;
	// DBCP + 마이바이스 이용 객체 DI(의존성 주입)
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public MemberDAO() {
		System.out.println("MemberDAO 객체 생성");
	}
	
	/** 로그인 DAO
	 * @param memberEmail
	 * @return loginMember
	 */
	public Member login(String memberEmail) {
		this.number++;
		// return sqlSession.selectOne("매퍼이름.태그id", SQL작성 시 필요한 값);
		return sqlSession.selectOne("memberMapper.login", memberEmail);
	}
	public int getNumber() {
		return this.number;
	}

	/** 회원가입 DAO
	 * @param inputMember
	 * @return result
	 */
	public int signUp(Member inputMember) {
//		return sqlSession.insert("memberMapper.signUp", inputMember);
		return 0;    // 실패를 가정한 코드
	}
}
