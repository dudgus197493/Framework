package edu.kh.project.member.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// lombok 라이브러리
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member {
	private int memberNo;				// 회원 번호
	private String memberEmail;         // 회원 이메일
	private String memberPw;            // 회원 비밀번호
	private String memberNickname;      // 회원 닉네임
	private String memberTel;           // 회원 휴대폰 번호
	private String memberAddress;       // 회원 주소
	private String profileImage;        // 프로필 이미지 경로
	private String enrollDate;          // 회원 가입일
	private String memberDeleteFlag;    // 탈퇴 여부
	private int authority;              // 회원 권한
}
