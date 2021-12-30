package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req,"iboard");
        int writer = Utils.getLoginUserPk(req);

        BoardEntity param = new BoardEntity();
        param.setIboard(iboard);
        param.setWriter(writer);

        int result = BoardDAO.delBoard(param);
        switch(result) {
            case 1:
                res.sendRedirect("/board/list");
                //삭제성공 했을 때 원래 디테일로 들어가기 전 리스트에서 나타내는 행수랑 같게 돌아게는 못하나..?
                return;
            default:
                req.setAttribute("err", "글 삭제를 실패하였습니다.");
                req.getRequestDispatcher("/board/detail?iboard=" + iboard).forward(req, res);
                return;
        }
    }
}

//삭제를 했을 때 원래 디테일로 들어가기 전 리스트에서 나타내는 행수랑 같게 돌아게게는 못하나..?