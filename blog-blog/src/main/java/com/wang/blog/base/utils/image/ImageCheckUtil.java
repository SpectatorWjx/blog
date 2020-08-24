package com.wang.blog.base.utils.image;

import cn.ucloud.common.pojo.Account;
import cn.ucloud.common.pojo.Param;
import cn.ucloud.common.util.Signature;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wang.common.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/***
 * @classname Test
 * @description
 * @author wjx
 * @date 2020/8/19 11:22
 */
@Slf4j
public class ImageCheckUtil extends Signature{

    private static String PRIVATE_KEY = "DOqBb6sDwaKTuaxgTDnriUG+HT55rG+S6oMmcx7kmaq9fOkvGZtDEXt47yymkfVk";

    private static String PUBLIC_KEY = "Efm+09kZHezQpbOFucYpRQnHiyN4tWF1Ydo4nixWN/+QKe28ylIWqwgwdXY=";


    private static String U_CLOUD_URL = "https://api.uai.ucloud.cn/v1/image/scan";

    private static String APP_ID = "uaicensor-ckwtxxz1";

    /**
     * @param imageUrl
     * @return pass-放行， forbid-封禁， check-人工审核
     * @throws Exception
     */
    public static String checkPornByUrl(String imageUrl) {
        String timestamp = System.currentTimeMillis() + "";
        String signature = getSignature(timestamp, imageUrl);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("Scenes", "porn");
        param.add("Method", "url");
        param.add("Url", imageUrl);
        RestTemplate rest = new RestTemplate();
        //headers 参数
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        headers.set("PublicKey", PUBLIC_KEY);
        headers.set("Signature", signature);
        headers.set("ResourceId", APP_ID);
        headers.set("Timestamp", timestamp);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = rest.exchange(U_CLOUD_URL, HttpMethod.POST, httpEntity, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        if (jsonObject.getInteger("RetCode") == 0) {
            log.info("鉴别成功");
            return jsonObject.getJSONObject("Result").getJSONObject("Porn").getString("Suggestion");
        }else{
            log.error("鉴别失败");
        }
        return "pass";
    }


    private static String getSignature(String timestamp, String imageUrl){
        /**
         * 生成signature，首字母排序
         */
        List<Param> list = new ArrayList<>();
        list.add(new Param("ResourceId", APP_ID));
        list.add(new Param("Timestamp", timestamp));
        list.add(new Param("PublicKey", PUBLIC_KEY));
        list.add(new Param("Url", imageUrl));
        String signature = null;
        try {
            signature = Signature.getSignature(list, new Account(PRIVATE_KEY, PUBLIC_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    public static void main(String[] args){
        String result1 = ImageCheckUtil.checkPornByUrl("");
        System.out.println(result1);
    }
}
