package com.ql.www.controller;

import com.ql.www.domain.model.Response;
import com.ql.www.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chocoh
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadImg")
    public Response uploadImg(MultipartFile file) {
        return Response.success(fileService.uploadImg(file));
    }
}
