package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import com.yylnb.util.SessionUtil;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传
 *
 * @author RainLin
 * @date 2020/1/25 - 14:24
 */

@Controller
public class FileController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file,  Model model, HttpSession session) {
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://alinmam_avatar//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            model.addAttribute("msg", "上传出现错误，请稍后再试！");
            return "redirect:/user_info";
        }
//      //上传到数据库
        User user = new User();
        user.setAvatar("/alinmam_avatar/" + fileName);
        userService.updateUserInfoById(user,session);

        //取出session信息，覆盖上新的头像地址
        User new_user = (User) session.getAttribute("userInfo");
        new_user.setAvatar("/alinmam_avatar/" + fileName);
        session.setAttribute("userInfo",new_user);
        return "redirect:/user_info";
    }
}
