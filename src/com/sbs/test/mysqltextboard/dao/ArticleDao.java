package com.sbs.test.mysqltextboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.dto.Reply;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;

public class ArticleDao {

	public ArticleDao() {
	}

	// ############ 게시판 #################

	public boolean getBoardWithName(String name) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE name = ?", name);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		if (!map.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean getBoardWithCode(String code) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE code = ?", code);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		if (!map.isEmpty()) {
			return true;
		}
		return false;
	}

	public int makeBoard(String boardName, String code) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO board (name, code)");
		sql.append("values ( ?, ? )", boardName, code);

		int id = MysqlUtil.update(sql);

		return id;
	}

	public Board getBoard(String code) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE code = ?", code);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		Board board = new Board(map);

		return board;
	}

	public Board getBoard(int id) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		Board board = new Board(map);

		return board;
	}

	public List<Board> getBoards() {

		SecSql sql = new SecSql();
		sql.append("SELECT B.*, COUNT(A.boardId) AS articleCount");
		sql.append("FROM board AS B");
		sql.append("INNER JOIN article AS A");
		sql.append("ON B.id = A.boardId");
		sql.append("GROUP BY A.boardId");

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);
		List<Board> boards = new ArrayList<>();

		for (Map<String, Object> map : maps) {
			Board board = new Board(map);
			boards.add(board);
		}
		return boards;
	}

	// ############ 게시물 #################

	public int add(String title, String body, int memberId, int boardId) {

		SecSql sql = new SecSql();
		sql.append("INSERT into article (regdate, updateDate, title, `body`, memberId, boardId, view)");
		sql.append("VALUES (NOW(), NOW(), ?, ?, ?, ?, 0)", title, body, memberId, boardId);

		int id = MysqlUtil.insert(sql);
		return id;
	}

	public int update(int index) {

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append("WHERE id = ?", index);

		int result = MysqlUtil.update(sql);

		return result;
	}

	public int modify(int index, String title, String body, int memberId) {

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET title = ?,", title);
		sql.append("`body` = ?,", body);
		sql.append("updateDate = NOW()");
		sql.append("WHERE id = ? and memberId = ?", index, memberId);

		int result = MysqlUtil.update(sql);
		return result;
	}

	public int delete(int index) {
		SecSql sql = new SecSql();
		sql.append("DELETE from article");
		sql.append("WHERE id = ?", index);

		int id = MysqlUtil.delete(sql);
		return id;
	}

	public void setArticleCount(int id, int count) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET view = ?", count);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);
	}

	public Article getArticle(int id) {

		SecSql sql = new SecSql();
		sql.append("SELECT A.*, B.memberId AS writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS B");
		sql.append("ON A.memberId = B.id");
		sql.append("WHERE A.id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (!map.isEmpty()) {
			return new Article(map);
		}
		return null;
	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append("SELECT article.*, member.memberId AS writer");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("ORDER BY id desc");

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : maps) {
			Article article = new Article(map);
			articles.add(article);
		}

		return articles;
	}

	public List<Article> getArticles(int boardId) {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();
		sql.append("SELECT article.*, member.memberId AS writer");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE boardId = ?", boardId);
		sql.append("ORDER BY id desc");

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : maps) {
			Article article = new Article(map);
			articles.add(article);
		}

		return articles;
	}

	public int Recommand(int articleId, int memberId) {

		SecSql sql = new SecSql();
		sql.append("UPDATE");
		sql.append("INSERT INTO");
		return 0;
	}

	// ############ 댓글 #################

	public int writeReply(String body, int articleId, int memberId) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO articleReply (regDate, body, articleId, memberId)");
		sql.append("values ( NOW(), ?, ?, ? )", body, articleId, memberId);

		int id = MysqlUtil.insert(sql);

		return id;
	}

	public List<Reply> getReply(int articleId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE articleId = ?", articleId);

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		List<Reply> replys = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			Reply reply = new Reply();
			reply.id = (int) map.get("id");
			reply.regDate = (String) map.get("regDate");
			reply.body = (String) map.get("body");
			reply.articleId = (int) map.get("articleId");
			reply.memberId = (int) map.get("memberId");

			replys.add(reply);
		}
		return replys;
	}

	public int modifyReply(int id, String body, int memberId) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO articleReply ( body )");
		sql.append("VALUES ( ? )", body);
		sql.append("WHERE id = ? AND memberId = ?", id, memberId);

		int index = MysqlUtil.insert(sql);

		return index;
	}

	public int deleteReply(int id, int memberId) {

		SecSql sql = new SecSql();
		sql.append("DELETE FROM articleReply");
		sql.append("WHERE id = ? AND memberId = ?", id, memberId);

		int result = MysqlUtil.delete(sql);
		return result;
	}
}
