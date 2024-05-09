package com.ql.www.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author chocoh
 */
public interface FileService {
    String uploadImg(MultipartFile file);

    void deleteImg(String url);
}
