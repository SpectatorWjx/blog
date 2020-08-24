package com.wang.common.service;

import com.wang.common.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/***
 * @classname: AbstractService
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/11/8 14:09
 */
public interface AbstractService<E extends BaseEntity> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    E findById(String id);

    /**
     * 查询最新的一个
     * @param example
     * @return
     */
    E findLatest(E example);

    /**
     * 查询最新的list
     * @param example
     * @return
     */
    List<E> findLatestList(E example);

    /**
     * 根据entity查询,check createBy
     * @param example
     * @return
     */
    E findOne(E example);

    /**
     * 根据entity查询
     * @param example
     * @return
     */
    E findOneNotCheckCreate(E example);

    /**
     * 根据entity分页
     * @param example
     * @param pageable
     * @return
     */
    Page<E> pageList(E example, Pageable pageable);


    /**
     * 无条件分页查询
     * @param pageable
     * @return
     */
    Page<E> findAllByPage(Pageable pageable);

    /**
     * 根据id集合查询
     * @param ids
     * @return
     */
    List<E> findListIdIn(List<String> ids);

    /**
     * 查询所有
     * @return
     */
    List<E> findListAll();

    /**
     * 只根据entity查询所有
     * @param example
     * @return
     */
    List<E> findListAll(E example);

    /**
     * 根据entity的条件重写查询所有
     * @param example
     * @return
     */
    List<E> findAllWhere(E example);

    /**
     * 根据entity的条件重写查询所有(自定义sort)
     * @param example
     * @return
     */
    List<E> findAllWhere(E example, Sort sort);

    /**
     * 根据entity查询数量
     * @param example
     * @return
     */
    Long count(E example);

    /**
     * 查询时间范围内记录数
     * @param example
     * @return
     */
    Long timeRangeCount(E example);

    /**
     * 某一项数值求和
     * @param example
     * @param col
     * @return
     */
    Object dataSum(E example, String col);

    /**
     * 保存
     * @param example
     * @return
     */
    E save(E example);

    /**
     * 批量保存
     * @param entities
     * @return
     */
    List<E> save(List<E> entities);

    /**
     * 修改
     * @param example
     */
    void update(E example);
//
//    /**
//     *  选择性替换,忽略不需要替换的字段
//     **/
//    void updateWithIgnore(E example);
//

    /**----------------------逻辑删除----------------**/
    /**
     * 根据entity删除
     * @param example
     */
    void delete(E example);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 根据id列表删除
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 根据List删除
     * @param example
     */
    void delete(List<E> example);

    /**--------------------------物理删除--------------------------------**/
    /**
     * 根据id删除
     * @param id
     */
    void remove(String id);


    /**
     * 根据ids删除
     * @param ids
     */
    void removeByIds(List<String> ids);

    /**
     * 根据entity删除
     * @param example
     */
    void remove(E example);

    /**
     * 获取泛型clazz
     * @return
     */
    Class<E> defineDoType();
}
