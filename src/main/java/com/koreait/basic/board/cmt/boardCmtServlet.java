package com.koreait.basic.board.cmt;

import com.google.gson.Gson;
import com.koreait.basic.Utils;
import com.koreait.basic.board.cmt.model.BoardCmtDTO;
import com.koreait.basic.board.cmt.model.BoardCmtVO;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/board/cmt")
public class boardCmtServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //리스트(R)
        int iboard = Utils.getParameterInt(req,"iboard");
        BoardCmtDTO cmtParam = new BoardCmtDTO();
        cmtParam.setIboard(iboard);

        List<BoardCmtVO> cmtList = BoardCmtDAO.selBoardCmtList(cmtParam);

        Gson gson = new Gson(); // 객체 -> Json으로 바꾸기 위해 Gson 객체 생성
        String json = gson.toJson(cmtList);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.print(json);
        out.flush();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //등록(C) 수정(U) 삭제(D)

    }
}
