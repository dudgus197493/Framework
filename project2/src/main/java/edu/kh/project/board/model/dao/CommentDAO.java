package edu.kh.project.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.vo.Comment;

/**
 * @author Tonic
 *
 */
@Repository
public class CommentDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	public List<Comment> selectCommentList(int boardNo) {
		return sqlSession.selectList("boardMapper.selectCommentList", boardNo);
	}

	/** 댓글 동록 DAO
	 * @param comment
	 * @return
	 */
	public int selectCommentList(Comment comment) {
		return sqlSession.insert("commentMapper.insertComment", comment);
	}

	/** 댓글 삭제 DAO
	 * @param comment
	 * @return result
	 */
	public int deleteComment(Comment comment) {
		return sqlSession.update("commentMapper.deleteComment", comment);
	}

	/** 댓글 수정 DAO
	 * @param comment
	 * @return result
	 */
	public int updateComment(Comment comment) {
		return sqlSession.update("commentMapper.updateComment", comment);
	}
	
	
}
