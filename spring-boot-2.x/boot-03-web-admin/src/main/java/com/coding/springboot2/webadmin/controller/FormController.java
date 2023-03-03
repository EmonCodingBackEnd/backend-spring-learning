package com.coding.springboot2.webadmin.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.coding.springboot2.webadmin.bean.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormController {

    @GetMapping(value = {"/form_layouts"})
    public String formLayouts() {
        return "form/form_layouts";
    }

    /**
     * MultipartFile自动封装上传文件
     * 
     * @param email
     * @param username
     * @param headerImg
     * @param photos
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "username", required = false) String username,
        @RequestPart(value = "user", required = false) User user, @RequestPart("headerImg") MultipartFile headerImg,
        @RequestPart("photos") MultipartFile[] photos) throws IOException {
        log.info("上传的信息：email={},username={},headerImg={},photos={}", email, username, headerImg.getSize(),
            photos.length);
        if (!headerImg.isEmpty()) {
            // 保存到文件服务器，OSS服务器
            headerImg.transferTo(new File("D:\\tmp\\" + headerImg.getOriginalFilename()));
        }

        if (photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    photo.transferTo(new File("D:\\tmp\\" + photo.getOriginalFilename()));
                }
            }
        }
        return "main";
    }

}