package kr.co.assemble.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.assemble.dao.MemberSub_DAO;

@Controller
public class Member_controller {
	/*
	 * @Inject MemberSub_DAO dao;
	 */
	
	//멤버 첫 홈 - 														===> 첫 홈에서 누르면 load 해놓음
	@RequestMapping(value = "/members")
	public String members() {
		return "jisoo/members";
	}
	
	
}
