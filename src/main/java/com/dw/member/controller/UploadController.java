package com.dw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dw.member.service.UploadService;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //MultipartFile 클래스로 사진을 전송 받는다! (배열이다. 사진 여러장도 받을 수 있으니까~~!!)
    @PostMapping("/api/v1/image")
    public boolean uploadImage(@RequestParam("image") MultipartFile[] locationFile){

        System.out.println("사진 이름은 : "+locationFile[0].getName());
        System.out.println("사진 이름은 : "+locationFile[0].getSize());


        
        // return true;
        return uploadService.uploadImageToAwsS3(locationFile);
    }

}
