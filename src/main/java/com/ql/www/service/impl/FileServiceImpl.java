package com.ql.www.service.impl;

import com.ql.www.common.ResponseMsg;
import com.ql.www.exception.GlobalException;
import com.ql.www.service.FileService;
import com.ql.www.utils.OssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author chocoh
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OssClient ossClient;

    @Override
    public String uploadImg(MultipartFile file) {
        try {
            String url = ossClient.uploadImg(file);
            if (url == null) {
                throw new GlobalException(ResponseMsg.FILE_UPLOAD_FORBID);
            } else {
                return url;
            }
        } catch (IOException e) {
            throw new GlobalException(ResponseMsg.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void deleteImg(String url) {
        ossClient.deleteImg(url);
    }
}
