package com.wang.common.repository;

import com.wang.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/***
 * @classname
 * @description
 * @author wjx zhijiu
 * @date 2020/04/28
 */
public interface BaseJpa<Entity extends BaseEntity> extends JpaRepository<Entity , String>, JpaSpecificationExecutor<Entity> {
}
