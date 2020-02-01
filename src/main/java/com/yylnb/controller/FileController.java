package com.yylnb.controller;

import com.yylnb.entity.User;
import com.yylnb.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件上传
 *
 * @author RainLin
 * @date 2020/1/25 - 14:24
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    UserService userService;
    public final static String IMGINPUT = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + "/upload/";
    //  IMGINPUT=D:/Project/alinmama/target/classes/static/upload/

    /**
     * 上传图片返回地址(json)
     *
     * @param img     <input name="img">
     * @param path    需要在发送图片时附带存放的文件夹名
     * @param model
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/img")
    public Map<String, Object> fileUpload(@RequestParam(value = "img") MultipartFile img,
                                          @RequestParam(value = "path") String path,
                                          Model model, HttpSession session) {

        Map<String, Object> json = new HashMap<String, Object>();
        String fileName = img.getOriginalFilename();  // 获取图片的名字fileName
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 然后获取它的后缀名suffixName
        //根据不同的图片分类存到不同的文件夹
        String filePath = IMGINPUT + path + "/";
        fileName = UUID.randomUUID() + suffixName; // 生成一个随机的文件名拼接上后缀名fileName
        File dest = new File(filePath + fileName);// filePath+fileName
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            img.transferTo(dest);
            json.put("img", "/" + path + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果上传的头像则更新session数据
        if (path.equals("alinmama_avatar")) {
            //取出session信息，覆盖上新的头像地址，并存入数据库
            User userInfo = (User) session.getAttribute("userInfo");
            userInfo.setAvatar("/" + path + "/" + fileName);
            userService.updateUserAndUserInfoById(userInfo);
            session.setAttribute("userInfo", userInfo);
            //日志
            log.info("用户[" + userInfo.getNick_name() + "]:更新了头像,地址[" + "/" + path + "/" + fileName + "]");
        }

        return json;
    }

    /**
     * wangEditor插件 上传图片返回地址
     *
     * @param file
     * @return
     */
    @RequestMapping("/editor")
    @ResponseBody
    public Object editor(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//取后缀名
            String fileName = UUID.randomUUID() + suffix;//新文件名
            String saveFileName = IMGINPUT + "alinmama_commodity/" + fileName;//绝对路径
            File dest = new File(saveFileName);//创建文件
            if (!dest.getParentFile().exists()) { //判断父级文件夹是否存在
                dest.getParentFile().mkdir();//不存在则创建
            }
            try {
                file.transferTo(dest); //保存文件
                String imgUrl = "/alinmama_commodity/" + fileName;//定义路径
                return new WangEditorResponse("0", imgUrl);//返回路径
            } catch (Exception e) {
                e.printStackTrace();
                return new WangEditorResponse("1", "上传失败" + e.getMessage());
            }
        } else {
            return new WangEditorResponse("1", "上传出错");
        }

    }

    @Data
    private class WangEditorResponse {
        String errno;
        List<String> data;

        public WangEditorResponse(String errno, List<String> data) {
            this.errno = errno;
            this.data = data;
        }

        public WangEditorResponse(String errno, String data) {
            this.errno = errno;
            this.data = new ArrayList<>();
            this.data.add(data);
        }
    }


}

