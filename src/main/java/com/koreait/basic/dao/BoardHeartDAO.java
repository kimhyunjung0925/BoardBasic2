package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardHeartEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardHeartDAO {
    public static int selIsHeart(BoardHeartEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT iuser FROM t_board_heart WHERE iuser = ? AND iboard = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1,entity.getIuser());
            ps.setInt(2,entity.getIboard());
            rs = ps.executeQuery();
            if(rs.next()){
                return 1; // 좋아요 함(값이 있음)
            } else {
                return 2; //좋아요 안함(값이 없음)
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return 0; // 에러터짐
    }

    public static int insBoardHeart(BoardHeartEntity entity){ // 좋아요 처리
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " INSERT INTO t_board_heart (iuser, iboard) VALUES(?, ?)";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIuser());
            ps.setInt(2,entity.getIboard());
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static int delBoardHeart(BoardHeartEntity entity){ // 좋아요 취소 처리
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " DELETE FROM t_board_heart WHERE iuser = ? AND iboard = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIuser());
            ps.setInt(2,entity.getIboard());
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    // 좋아요 수 출력
    public static int heartCount(int iboard){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT COUNT(*) FROM t_board_heart WHERE iboard = ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, iboard);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

}
