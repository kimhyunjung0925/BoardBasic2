package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardHeartHateEntity;
import com.koreait.basic.dao.BoardHateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/hate")
public class BoardHateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String proc = req.getParameter("proc");
        int iboard = Utils.getParameterInt(req,"iboard");
        int iuser = Utils.getLoginUserPk(req);

        BoardHeartHateEntity entity = new BoardHeartHateEntity();
        entity.setIboard(iboard);
        entity.setIuser(iuser);

        switch (proc){
            case "1": //싫어요 처리
                BoardHateDAO.insBoardHate(entity);
                break;
            case "2": //싫어요 취소
                BoardHateDAO.delBoardHate(entity);
                break;
        }
        res.sendRedirect("/board/detail?nothis=1&iboard=" + iboard);
    }

}
