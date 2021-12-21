package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.dao.BoardHeartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/heart")
public class BoardHeartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String proc = req.getParameter("proc");
        int iboard = Utils.getParameterInt(req,"iboard");
        int iuser = Utils.getLoginUserPk(req);

        BoardHeartEntity entity = new BoardHeartEntity();
        entity.setIboard(iboard);
        entity.setIuser(iuser);

        switch (proc){
            case "1": //좋아요 처리
                BoardHeartDAO.insBoardHeart(entity);
                break;
            case "2": //좋아요 취소
                BoardHeartDAO.delBoardHeart(entity);
                break;
        }
        res.sendRedirect("/board/detail?nothis=1&iboard=" + iboard);
    }

}
