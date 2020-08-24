package com.wang.blog.base.utils.image;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.wang.blog.base.utils.SpringUtil;
import com.wang.blog.vo.FileDocument;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/***
 * @classname: ImageMongoManageUtils
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/31 8:45
 */
@Data
public class MongoManageUtils {

    /** 文件操作工具 **/
    private static MongoTemplate fileMongoTemplate = SpringUtil.getBean( "fileMongoTemplate", MongoTemplate.class);

    private static GridFsTemplate fileGridFsTemplate = SpringUtil.getBean( "fileGridFsTemplate", GridFsTemplate.class);

    private static GridFSBucket fileGridFSBucket = SpringUtil.getBean("fileGridFSBucket", GridFSBucket.class);


    /** --------------------------------------------------  图片操作          -------------------------------**/
    /**
     * 获取缩略图列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<FileDocument> listFilesByPage(String collectionName, Integer pageIndex, Integer pageSize) {
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "uploadDate"));
        long skip = (pageIndex -1) * pageSize;
        query.skip(skip);
        query.limit(pageSize);
        Field field = query.fields();
        field.exclude("content");
        List<FileDocument> files = fileMongoTemplate.find(query , FileDocument.class , collectionName);
        return files;
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    public static FileDocument saveImage(MultipartFile file, String collectionName) {
        if(StringUtils.isEmpty(collectionName)){
            collectionName = "image";
        }
        return save(file,collectionName,fileMongoTemplate,fileGridFsTemplate);
    }


    /**
     * 查询图片
     * @param id 文件id
     * @return
     */
    public static Optional<FileDocument> getImageById(String id, String collectionName){
        Optional<FileDocument> fileDocumentOptional = getById(id,collectionName, fileMongoTemplate, fileGridFsTemplate, fileGridFSBucket);
        return fileDocumentOptional;
    }


    /**
     * 删除图片
     * @param id 文件id
     * @param isDeleteFile 是否删除文件
     */
    public static void removeImage(String id, String collectionName, Boolean isDeleteFile) {
        remove(id,collectionName,isDeleteFile, fileMongoTemplate, fileGridFsTemplate);
    }

    /**-----------------------------------------------------------     操作方法    ------------------------------------**/
    /**
     * 上传
     * @param file
     * @return
     */
    private static FileDocument save(MultipartFile file, String collectionName, MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate) {
        String md5 = null;
        try {
            md5 = SecureUtil.md5(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //已存在该文件，则实现秒传
        FileDocument fileDocument = getByMd5(md5, collectionName, mongoTemplate);
        if(fileDocument != null){
            return fileDocument;
        }
        fileDocument = getDocumentByFile(file);
        fileDocument.setMd5(md5);
        try {
            String gridFSId = uploadFileToGridFS(file.getInputStream() , file.getContentType(), gridFsTemplate);
            fileDocument.setGridfsId(gridFSId);
            fileDocument = mongoTemplate.save(fileDocument , collectionName);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return fileDocument;
    }

    /**
     * 删除
     * @param id 文件id
     * @param isDeleteFile 是否删除文件
     */
    private static void remove(String id, String collectionName, Boolean isDeleteFile, MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate) {
        FileDocument fileDocument = mongoTemplate.findById(id , FileDocument.class , collectionName);
        if(fileDocument != null){
            Query query = new Query().addCriteria(Criteria.where("_id").is(id));
            mongoTemplate.remove(query , collectionName);
            if(isDeleteFile){
                Query deleteQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridfsId()));
                gridFsTemplate.delete(deleteQuery);
            }
        }
    }

    /**
     * 查询
     * @param id 文件id
     * @return
     */
    private static Optional<FileDocument> getById(String id, String collectionName, MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate, GridFSBucket gridFSBucket){
        FileDocument fileDocument = mongoTemplate.findById(id , FileDocument.class , collectionName);
        if(fileDocument != null){
            Query gridQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridfsId()));
            try {
                GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
                GridFSDownloadStream in = gridFSBucket.openDownloadStream(fsFile.getObjectId());
                if(in.getGridFSFile().getLength() > 0){
                    GridFsResource resource = new GridFsResource(fsFile, in);
                    fileDocument.setContent(IoUtil.readBytes(resource.getInputStream()));
                    return Optional.of(fileDocument);
                }else {
                    return Optional.empty();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * 上传文件到Mongodb的GridFs中
     * @param in
     * @param contentType
     * @return
     */
    private static String uploadFileToGridFS(InputStream in , String contentType, GridFsTemplate gridFsTemplate){
        String gridFSId = IdUtil.simpleUUID();
        //文件，存储在GridFS中
        gridFsTemplate.store(in, gridFSId , contentType);
        return gridFSId;
    }

    /**
     * 根据md5获取文件对象
     * @param md5
     * @return
     */
    private static FileDocument getByMd5(String md5, String collectionName, MongoTemplate mongoTemplate) {
        Query query = new Query().addCriteria(Criteria.where("md5").is(md5));
        FileDocument fileDocument = mongoTemplate.findOne(query , FileDocument.class , collectionName);
        return fileDocument;
    }

    /**
     * 获取fileDocument
     * @param file
     * @return
     */
    public static FileDocument getDocumentByFile(MultipartFile file){
        FileDocument fileDocument = new FileDocument();
        fileDocument.setName(file.getOriginalFilename());
        fileDocument.setSize(file.getSize());
        fileDocument.setContentType(file.getContentType());
        fileDocument.setUploadDate(new Date());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        fileDocument.setSuffix(suffix);
        return fileDocument;
    }

}
