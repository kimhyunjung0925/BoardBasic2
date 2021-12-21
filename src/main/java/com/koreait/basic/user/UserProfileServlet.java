package com.koreait.basic.user;

import com.koreait.basic.FileUtils;
import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);
        UserEntity entity = new UserEntity();
        entity.setIuser(loginUserPk);
        req.setAttribute("data", UserDAO.selUser(entity));

        String title = "프로필";
        req.setAttribute("subPage","/user/profile");
        Utils.displayView(title,"user/myPage",req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);
        int fileMaxSize = 10_485_760;

        ServletContext application = req.getServletContext();
        String targetPath = application.getRealPath("/res/img/profile/" + loginUserPk);

        System.out.println("targetPath : " + targetPath);

        File targetFolder = new File(targetPath);
        if(targetFolder.exists()){ //폴더 존재했을 때
            FileUtils.delFolderFiles(targetPath,false); //폴더안 파일만 삭제
        } else { //폴더 존재 안했을 때
            targetFolder.mkdirs(); //새로운 폴더 생성
        }

        MultipartRequest mr
                = new MultipartRequest(req, targetPath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
        // req, 어디다 저장 하는지, 파일사이즈, 인코딩, 중복파일제거 아무숫자붙이기

        String changeFileNm = mr.getFilesystemName("profileImg");

        UserEntity entity = new UserEntity();
        entity.setIuser(loginUserPk);
        entity.setProfileImg(changeFileNm);

        int result = UserDAO.updUser(entity);
        //doGet(req,res);
        res.sendRedirect("/user/profile");
    }
}
