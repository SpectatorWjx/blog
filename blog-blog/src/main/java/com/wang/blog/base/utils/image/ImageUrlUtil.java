package com.wang.blog.base.utils.image;

/***
 * @classname ImageUrlUtil
 * @description
 * @author wjx
 * @date 2020/3/2
 */
public class ImageUrlUtil {

    private static final String IMAGE_VIEW = "/image/thumbnail/";

    private static final String ORIGINAL_IMAGE_VIEW = "/image/original/";

    public static String getImageUrl(String imageId){
        return IMAGE_VIEW + imageId;
    }

    public static String getOriginalImageUrl(String imageId){
        return  ORIGINAL_IMAGE_VIEW + imageId;
    }
}
