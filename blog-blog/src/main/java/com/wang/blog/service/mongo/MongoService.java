package com.wang.blog.service.mongo;

import com.wang.blog.vo.FileDocument;
import com.wang.blog.vo.ImageVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/***
 * @classname MongoService
 * @description
 * @author wjx
 * @date 2019/10/28
 */
public interface MongoService {
    /**
     * 保存图片
     * @param file
     * @param height
     * @param width
     * @return
     */
    ImageVo saveFile(MultipartFile file, Integer height, Integer width);

    /**
     * 保存图片
     * @param file
     * @param scale
     * @return
     */
    ImageVo saveFile(MultipartFile file, Double scale);


    /**
     * 根据id获取文件
     * @param id
     * @param collectionName
     * @return
     */
    FileDocument getById(String id, String collectionName);

    /**
     * 分页查询，按上传时间降序
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<FileDocument> listFilesByPage(Integer pageIndex, Integer pageSize);

    /**
     * 图片预览
     * @param id
     * @param response
     * @param isOriginal
     * @return
     */
    Object serveImageOnline(String id, HttpServletResponse response, Boolean isOriginal);
}
