package com.sbs.textboard.container;

import com.sbs.textboard.dao.ArticleDAO;
import com.sbs.textboard.dao.MemberDAO;
import com.sbs.textboard.service.ArticleService;
import com.sbs.textboard.service.MemberService;
import com.sbs.textboard.session.Session;

public class Container {
	public static Session session;

	public static MemberService memberService;
	public static MemberDAO memberDAO;

	public static ArticleService articleService;
	public static ArticleDAO articleDAO;

	static {
		session = new Session();

		memberService = new MemberService();
		memberDAO = new MemberDAO();

		articleService = new ArticleService();
		articleDAO = new ArticleDAO();
	}
}
