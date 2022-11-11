package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.vo.Member;

// 서비스 인터페이스를 만든 이유
// 설계, 유지보수성 향상, AOP 때문에
public interface AjaxService {

	/** 이메일 중복검사 서비스
	 * @param memberEmail
	 * @return result
	 */
	int emailDupCheck(String memberEmail);

	/** 닉네임 중복검사 서비스
	 * @param memberNickname
	 * @return result
	 */
	int nicknameDupCheck(String memberNickname);

	/** 이메일 일치하는 회원 조회 서비스
	 * @param email
	 * @return member
	 */
	Member selectEmail(String email);

	/** 회원 목록 조회 서비스
	 * @return memberList
	 */
	List<Member> selectMemberList();

}
