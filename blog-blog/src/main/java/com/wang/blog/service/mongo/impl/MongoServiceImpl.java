package com.wang.blog.service.mongo.impl;

import com.wang.blog.base.utils.RandomEnumUtil;
import com.wang.blog.base.utils.image.MongoManageUtils;
import com.wang.blog.base.utils.image.ThumbnailImageUtil;
import com.wang.blog.vo.FileDocument;
import com.wang.blog.vo.ImageVo;
import com.wang.blog.repository.ImageRepository;
import com.wang.blog.service.mongo.MongoService;
import com.wang.common.common.base.BaseException;
import com.wang.common.common.enums.MongoCollectionEnum;
import com.wang.common.entity.other.ImageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Optional;


/***
 * @classname: MongoServiceImpl
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/28 8:52
 */
@Service
@Slf4j
public class MongoServiceImpl implements MongoService {


    @Autowired
    private ImageRepository imageJpa;

    private static Integer collection = 0;

    /**
     * 表单上传图片并返回缩略图信息
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImageVo saveFile(MultipartFile file, Integer height, Integer width) {
        if(file == null || file.isEmpty()){
            throw new BaseException(306,"文件不能为空");
        }
        //检查是否是图片
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bi == null){
           throw new BaseException(300,"请上传图片文件");
        }

        ImageEntity imageEntity = saveImageToMongo(file, height, width, null, true);
        imageJpa.saveAndFlush(imageEntity);

        ImageVo imageVo = new ImageVo();
        BeanUtils.copyProperties(imageEntity,imageVo);
        return imageVo;
    }

    /**
     * 表单上传图片并返回缩略图信息
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImageVo saveFile(MultipartFile file, Double scale) {
        if(file == null || file.isEmpty()){
            throw new BaseException(306,"文件不能为空");
        }
        //检查是否是图片
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bi == null){
            throw new BaseException(300,"请上传图片文件");
        }

        ImageEntity imageEntity = saveImageToMongo(file, null, null, scale, false);
        imageJpa.saveAndFlush(imageEntity);

        ImageVo imageVo = new ImageVo();
        BeanUtils.copyProperties(imageEntity,imageVo);
        return imageVo;
    }

    private String getCollectionName(){
        String collectionName = "collection1";
        if(collection<10){
            collectionName = RandomEnumUtil.num(MongoCollectionEnum.class,collection).toString();
        } else {
            collection = 0;
            collectionName = RandomEnumUtil.num(MongoCollectionEnum.class,collection).toString();
        }
        collection++;
        return collectionName;
    }


    private ImageEntity saveImageToMongo(MultipartFile file, Integer height, Integer width, Double scale, Boolean fixed){
        String collectionName = getCollectionName();
         /*
        获取图片参数
         */
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        FileDocument masterDocument = null;
        FileDocument compressDocument = null;
        OutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            FileDocument fileDocument = MongoManageUtils.getDocumentByFile(file);
            String fileName;
            if(fixed){
                fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+"_1.0"+fileDocument.getSuffix();
                outputStream = ThumbnailImageUtil.generateThumbnailDirectory(inputStream, height, width);
            } else {
                fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+"_0.5"+fileDocument.getSuffix();
                outputStream = ThumbnailImageUtil.generateThumbnailDirectory(inputStream, scale);
            }
            baos = (ByteArrayOutputStream) outputStream;
            byte[] bytes = baos.toByteArray();
            InputStream compressStream = new ByteArrayInputStream(bytes);
            MultipartFile compressFile = new MockMultipartFile(file.getName(), fileName, fileDocument.getContentType(), compressStream);
            /*
            原图存入mongo
             */
            masterDocument = MongoManageUtils.saveImage(file, collectionName);
            /*
            压缩图存入mongo
             */
            compressDocument = MongoManageUtils.saveImage(compressFile, collectionName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setCompressImageId(compressDocument.getId());
        imageEntity.setMasterImageId(masterDocument.getId());
        imageEntity.setMongoCollectionName(collectionName);
        imageEntity.setName(masterDocument.getName());
        imageEntity.setSize(masterDocument.getSize());
        imageEntity.setSuffix(masterDocument.getSuffix());
        imageEntity.setType(masterDocument.getContentType());
        return imageEntity;
    }

    @Override
    public Object serveImageOnline(String id, HttpServletResponse response, Boolean isOriginal) {
        Optional<ImageEntity> imageEntityOptional = imageJpa.findById(id);
        ImageEntity imageEntity;
        if(imageEntityOptional.isPresent()){
            imageEntity = imageEntityOptional.get();
        } else {
            return null;
        }

        String collectionName = imageEntity.getMongoCollectionName();
        FileDocument file;
        if(isOriginal){
            file = getById(imageEntity.getMasterImageId(), collectionName);
        } else {
            file = getById(imageEntity.getCompressImageId(), collectionName);
        }

        if(file == null){
            return null;
        }
        //读取要下载的文件，保存到文件输入流
        OutputStream out = null;
        try {
            response.setHeader("content-Type", file.getContentType());
            response.setContentType(file.getContentType()+";charset=utf-8");
            out = response.getOutputStream();
            out.write(file.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询附件
     * @param id 文件id
     * @return
     * @throws IOException
     */
    @Override
    public FileDocument getById(String id, String collectionName){
        Optional<FileDocument> fileDocument = MongoManageUtils.getImageById(id, collectionName);
        if(fileDocument.isPresent()){
            return fileDocument.get();
        }
        return null;
    }

    @Override
    public List<FileDocument> listFilesByPage(Integer pageIndex, Integer pageSize) {
//        Query query = new Query().with(new Sort(Sort.Direction.DESC, "uploadDate"));
//        long skip = (pageIndex -1) * pageSize;
//        query.skip(skip);
//        query.limit(pageSize);
//        Field field = query.fields();
//        field.exclude("content");
//        List<FileDocument> files = mongoTemplate.find(query , FileDocument.class , collectionName);
        return null;
    }
}
