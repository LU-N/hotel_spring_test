package com.itcast.controller;

import com.itcast.domain.MyRoom;
import com.itcast.service.MyRoomService;
import com.itcast.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author JinLu
 * @date 2019/10/28
 */
@Controller
public class MyRoomController {
    @Autowired
    private MyRoomService myRoomService;

    @Value("${upload.dir}")
    private String uploadDir;


    @RequestMapping("/room/add.do")
    public String add(MyRoom room, MultipartFile file) throws IOException {
        //获取文件后缀
        String ext = UploadUtils.ext(file.getOriginalFilename());
        //将上传文件，存入磁盘，并返回md5的文件名
        String filename = UploadUtils.toMd5File(file.getInputStream(), uploadDir, ext);
        //将所有信息存入数据库
        room.setImage(filename);
        myRoomService.add(room);
        //重定向
        return "redirect:/room/findById.do?id=" + room.getId();
    }

    @RequestMapping("/room/findById.do")
    public String findById(int id, Model model) {
        MyRoom myRoom = myRoomService.findById(id);
        model.addAttribute("myRoom", myRoom);
        return "update";
    }

    @RequestMapping("/room/update.do")
    public String update(MyRoom myRoom, MultipartFile file) throws IOException {
        if (file != null && file.getSize() > 0) {
            //获取文件后缀
            String ext = UploadUtils.ext(file.getOriginalFilename());
            //将上传文件，存入磁盘，并返回md5的文件名
            String filename = UploadUtils.toMd5File(file.getInputStream(), uploadDir, ext);
            //更新文件名
            myRoom.setImage(filename);
        }
        //更新数据库
        myRoomService.update(myRoom);
        //重定向
        return "redirect:/room/findById.do?id=" + myRoom.getId();
    }
}
