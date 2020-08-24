package com.wang.blog.web.controller.site.posts;

import com.wang.blog.service.mongo.MongoService;
import com.wang.blog.vo.FileDocument;
import com.wang.blog.vo.ImageVo;
import com.wang.common.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/***
 * @classname: MongoController
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/28 8:48
 */
@RequestMapping("image")
@Controller
public class MongoController {

    @Autowired
    private MongoService mongoService;

    /**
     * 列表数据
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<FileDocument> list(Integer pageIndex, Integer pageSize){
        return mongoService.listFilesByPage(pageIndex,pageSize);
    }

    /**
     * 在线显示缩略图片
     * @param id 文件id
     * @return
     */
    @GetMapping("/thumbnail/{id}")
    @ResponseBody
    public Object serveImageOnline(@PathVariable String id, HttpServletResponse response) {
        return mongoService.serveImageOnline(id, response, false);
    }

    /**
     * 在线显示原图图片
     * @param id 文件id
     * @return
     */
    @GetMapping("/original/{id}")
    @ResponseBody
    public Object serveOriginalImageOnline(@PathVariable String id, HttpServletResponse response) {
        return mongoService.serveImageOnline(id, response, true);
    }

    /**
     * 表单上传文件
     * 当数据库中存在该md5值时，可以实现秒传功能
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result formUpload(MultipartFile file){
        ImageVo imageVo = mongoService.saveFile(file, null);
        return Result.success(imageVo);
    }
}
