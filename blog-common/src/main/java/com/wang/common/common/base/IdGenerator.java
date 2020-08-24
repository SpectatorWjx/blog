package com.wang.common.common.base;

import cn.hutool.core.util.IdUtil;
import com.wang.common.entity.blog.OptionsEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/***
 * @classname IdGengerator
 * @description
 * @author wjx zhijiu
 * @date 2019/10/31 11:19
 */
@Slf4j
public class IdGenerator extends UUIDGenerator {//Long型继承IdentityGenerator类，String类型继承 UUIDGenerator 或者 UUIDGenerator
     @Override
     public Serializable generate(SharedSessionContractImplementor session, Object object){
                Object id =   generateId(object.getClass());
                if (id != null) {
                         return (Serializable) id;
                }
                return super.generate(session, object);
     }

    public static final String generateId(Class clazz) {
        TableIdPrefix codePrefix = (TableIdPrefix) clazz.getDeclaredAnnotation(TableIdPrefix.class);
        if (null == codePrefix) {
            return "TABLEID_" + IdUtil.simpleUUID();
        } else {
            String prefix = codePrefix.value();
            if (null == prefix) {
                return "TABLEID_" + IdUtil.simpleUUID();
            } else {
                return prefix.toUpperCase()+ "_" + IdUtil.simpleUUID();
            }
        }
    }

    public static void main(String[] args){
         for (int i =0 ; i<22; i++) {
             System.out.println(generateId(OptionsEntity.class));
         }
    }
}
