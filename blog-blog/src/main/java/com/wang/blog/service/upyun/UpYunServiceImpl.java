package com.wang.blog.service.upyun;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.UpYun;
import com.upyun.UpException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/***
 * @classname: TestUpYunServiceImpl
 * @description:
 * @author: wjx
 * @date: 2020/4/13 16:27
 */
@Service
public class UpYunServiceImpl implements UpYunService {

    @Override
    public String upload(MultipartFile file, String bucketName, String operator, String operator_key, String src) {
        // 创建实例
        UpYun upyun = new UpYun(bucketName, operator, operator_key);
        upyun.setDebug(false);
        upyun.setTimeout(30);

        // 定义保存路径
        String date = DateUtil.formatDate(new Date());
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String savePath = src +"/"+ date + "/" + UUID.randomUUID()+"."+suffix;
        Boolean result = false;
        // 上传文件
        try {
             result = upyun.writeFile(savePath, file.getBytes(), true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UpException e) {
            e.printStackTrace();
        }
        if(!result){
            return "";
        }
        return savePath;
    }
}
