package com.wang.blog.service.upyun;

import org.springframework.web.multipart.MultipartFile;

/***
 * @classname: TestUpYunService
 * @description:
 * @author: wjx
 * @date: 2020/4/13 16:26
 */
public interface UpYunService {
    /**
     * 上传图片
     * @param file
     * @param bucketName
     * @param operator
     * @param operator_key
     * @param src
     * @return
     */
    String upload(MultipartFile file, String bucketName, String operator, String operator_key, String src);
}
