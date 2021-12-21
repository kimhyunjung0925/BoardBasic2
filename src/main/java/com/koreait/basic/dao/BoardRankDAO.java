package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardRankDAO {
    private static List<BoardVO> procResultSet(String sql) {
        List<BoardVO> list = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int countcmt = BoardCmtDAO.countCmt(rs.getInt("iboard"));
                BoardVO vo = BoardVO.builder()
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .countCmt(countcmt)
                        .writer(rs.getInt("writer"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
                        .profileImg(rs.getString("profileImg"))
                        .cnt(rs.getInt("cnt"))
                        .build();
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }

    public static List<BoardVO> selBoardHitsRankList() { //조회수
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt " +
                " , B.nm AS writerNm, B.profileImg " +
                " , A.hit as cnt " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " WHERE A.hit > 0 " +
                " ORDER BY A.hit DESC, A.iboard DESC" +
                " LIMIT 10 ";
        return procResultSet(sql);
    }

    public static List<BoardVO> selBoardCmtRankList() { //댓글수
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt " +
                " , B.nm AS writerNm, B.profileImg " +
                " , C.cnt " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " INNER JOIN " +
                " (SELECT iboard, COUNT(icmt) AS cnt FROM t_board_cmt GROUP BY iboard) C " +
                " ON A.iboard = C.iboard " +
                " ORDER BY C.cnt DESC " +
                " LIMIT 10 ";
        return procResultSet(sql);
    }

    public static List<BoardVO> selBoardHeartRankList() { //좋아요수
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt " +
                " , B.nm AS writerNm, B.profileImg " +
                " , C.cnt " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " INNER JOIN " +
                " (SELECT iboard, COUNT(iuser) AS cnt FROM t_board_heart GROUP BY iboard) C " +
                " ON A.iboard = C.iboard " +
                " ORDER BY C.cnt DESC " +
                " LIMIT 10 ";
        return procResultSet(sql);
    }


}
