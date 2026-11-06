package com.semi.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/member/findId")
public class FindIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindIdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/member/findId.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// 클라이언트에서 전달된 이름과 핸드폰 번호 받기
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        // 서비스 호출하여 DB에서 사용자 정보 검색
        MemberService memberService = new MemberService();
        Member member = memberService.findId(name, phone);
        
        // JSON 형태로 응답 준비
        Map<String, Object> result = new HashMap<>();
        if (member != null) {
            result.put("success", true );
            result.put("id", member.getMemberId()); // 사용자 아이디 전달
        } else {
            result.put("success", false);
        }

        // JSON 응답 전송
        response.setContentType("application/json; charset=UTF-8");
        new Gson().toJson(result, response.getWriter());
    }
}