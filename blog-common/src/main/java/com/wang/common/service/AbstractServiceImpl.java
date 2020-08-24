package com.wang.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.wang.common.common.base.BaseException;
import com.wang.common.common.base.Pair;
import com.wang.common.common.enums.ResultEnum;
import com.wang.common.common.utils.CollectionUtil;
import com.wang.common.common.utils.DateUtil;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.BaseEntity;
import com.wang.common.repository.BaseJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @classname: AbstractServiceImpl
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/11/bilibili-22 16:44
 */
@Service
@Slf4j
public class AbstractServiceImpl<E extends BaseEntity, J extends BaseJpa<E>> implements AbstractService<E> {

    protected static final String[]  IGNORE_FIELDS = new String[]{"id", "createTime", "createBy", "delFlag"};
    protected static final String START_TIME = "startTime";
    protected static final String END_TIME = "endTime";

    @Autowired
    @PersistenceContext
    protected EntityManager em;

    /**
     * 一天的毫秒数
     */
    public final Long ONE_DAY_TIME_STAMP = 24 * 60 * 60 * 1000L;

    @Autowired(required = false)
    protected J jpa;

    @Override
    public E findById(String id) {
        Optional<E> optional = jpa.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public E findLatest(E example) {
        Specification<E> specification = getLastSpec(example);
        List<E> rst = jpa.findAll(specification);
        if(!CollectionUtil.isEmpty(rst)){
            //如果存在多个，获取第一个
            return rst.get(0);
        }
        em.clear();
        return null;
    }

    @Override
    public List<E> findLatestList(E example) {
        Specification<E> specification = getLastSpec(example);
        List<E> rst = jpa.findAll(specification);
        if(!CollectionUtil.isEmpty(rst)){
            return rst;
        }
        em.clear();
        return Lists.newArrayList();
    }



    @Override
    public E findOne(E example) {

        Optional<E> optional = jpa.findOne(Example.of(example));
        if(optional.isPresent()){
            return optional.get();
        }
        em.clear();
        return null;
    }

    @Override
    public E findOneNotCheckCreate(E example) {
        Optional<E> optional = jpa.findOne(Example.of(example));
        if(optional.isPresent()){
            return optional.get();
        }
        em.clear();
        return null;
    }

    @Override
    public Page<E> pageList(E example, Pageable pageable) {
        example.setSearch(StringUtils.trimAllWhitespace(example.getSearch()));
        Page<E> page = jpa.findAll(getWhere(example), pageable);
        em.clear();
        return page;
    }

    @Override
    public Page<E> findAllByPage(Pageable pageable) {
        Page page = jpa.findAll(pageable);
        return page;
    }

    @Override
    public List<E> findListIdIn(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        Specification<E> specification = (root, query, cb) -> {
            CriteriaBuilder.In<String> in = cb.in(root.get("id"));
            ids.forEach(v -> in.value(v));
            return cb.and(in);
        };
        List<E> rst = jpa.findAll(specification, sort());
        return rst;
    }

    @Override
    public List<E> findListAll() {
        List<E> rst = jpa.findAll(sort());
        return rst;
    }

    @Override
    public List<E> findListAll(E example) {
        List<E> rst = jpa.findAll(Example.of(example,
                ExampleMatcher.matching()
                        .withStringMatcher(ExampleMatcher.StringMatcher.EXACT))
                , sort());
        em.clear();
        return rst;
    }

    @Override
    public List<E> findAllWhere(E example) {
        List<E> list = jpa.findAll(getWhere(example), sort());
        em.clear();
        return list;
    }

    @Override
    public List<E> findAllWhere(E example, Sort sort) {
        List<E> list = jpa.findAll(getWhere(example), sort);
        em.clear();
        return list;
    }

    @Override
    public Long count(E example) {
        return jpa.count(Example.of(example));
    }

    @Override
    public Long timeRangeCount(E example){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<E> root = cq.from(defineDoType());
        cq.select(cb.countDistinct(root)).where(getWhere(example).toPredicate(root, cq, cb));
        Long count = em.createQuery(cq).getSingleResult();
        return count;
    }

    @Override
    public Object dataSum(E example, String col) {
        if(StringUtil.isEmpty(col)) {
            return 0L;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //查询结果
        CriteriaQuery<Object> cq = cb.createQuery(Object.class);
        Root<E> root = cq.from(defineDoType());
        cq.select(cb.sum( root.get(col))).where(getWhere(example).toPredicate(root, cq, cb));
        TypedQuery<Object> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public E save(E e) {
        if(Optional.ofNullable(e).isPresent()){
            jpa.saveAndFlush(e);
            return e;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<E> save(List<E> entities) {
        if(!CollectionUtils.isEmpty(entities)){
            jpa.saveAll(entities);
            return entities;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(E e) {
        validIdExist(e.getId());
        E target = findById(e.getId());
        if (!Optional.ofNullable(target).isPresent()) {
            throw new BaseException(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
        BeanUtils.copyProperties(e, target, IGNORE_FIELDS);
        jpa.saveAndFlush(target);
    }

    /**
     * 查询最新记录条件组合
     * @param example
     * @return
     */
    protected Specification<E> getLastSpec(E example){
        return (Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(example));
            map = changeFilter(defineDoType(), map);
            map.entrySet().stream()
                    .filter(e -> !ObjectUtils.isEmpty(e.getValue()))
                    .filter(e -> !START_TIME.equals(e.getKey()))
                    .filter(e -> !END_TIME.equals(e.getKey()))
                    .forEach(e -> {
                        Predicate predicate;
                        predicate = cb.equal(root.get(e.getKey()), e.getValue());
                        predicates.add(predicate);
                    });
            predicates.add(cb.equal(root.get(getLastTimeType()), getLastTime(defineDoType(), map)));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 查询最新时间
     * @param clazz
     * @return
     */
    protected Date getLastTime(Class<E> clazz, Map<String, Object> map){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //查询结果
        CriteriaQuery<Date> cq = cb.createQuery(Date.class);
        Root<E> root = cq.from(clazz);
        List<Predicate> predicates = new ArrayList<>();
        map.entrySet().stream()
                .forEach(e -> {
                    Predicate predicate;
                    predicate = cb.equal(root.get(e.getKey()), e.getValue());
                    predicates.add(predicate);
                });
        cq.select(cb.greatest((Path)root.get(getLastTimeType())));
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Date> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult();

    }
    /**
     * 查询时间最新，默认createTime
     * @return
     */
    protected String getLastTimeType(){
        return "createTime";
    }

    /**
     * 校验是否存在
     * @param id
     */
    public void validIdExist(String id) {
        if (!Optional.ofNullable(id).isPresent()) {
            throw new BaseException(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    /**
     * 排序
     * @return
     */
    public Sort sort() {
        List<Sort.Order> orders = new ArrayList();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        return Sort.by(orders);
    }

    @Override
    public Class<E> defineDoType() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 组装查询条件
     * @param example
     * @return
     */
    public Specification<E> getWhere(E example) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //startTime,endTime判断的时间，默认是createTime
            String timeType = getTimeType();
            getTimeParam(timeType, example, format, root, cb, predicates);
            //转换成map时允许value为null的key存在
            Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(example, SerializerFeature.WriteMapNullValue));
            //调整查询条件map，以适应对应业务查询要求
            map = changeFilter(defineDoType(), map);

            map.entrySet().stream()
                    .filter(e -> !ObjectUtils.isEmpty(e.getValue()))
                    .filter(e -> !START_TIME.equals(e.getKey()))
                    .filter(e -> !END_TIME.equals(e.getKey()))
                    .forEach(e -> {
                        Predicate predicate;
                        //针对单个查询条件进行子类或父类的覆盖
                        Pair<Boolean, Predicate> overrideSingleWherePredicatePair = overrideSingleWhere(e, root, cb);
                        Boolean isOverloadFlag = overrideSingleWherePredicatePair.getFirst();
                        if (isOverloadFlag) {
                            //如果覆盖，则采用新的查询规则
                            predicate = overrideSingleWherePredicatePair.getSecond();
                        } else {
                            //如果没有覆盖，则使用下面规则
                            predicate = cb.equal(root.get(e.getKey()), e.getValue());
                        }
                        predicates.add(predicate);
                    });
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * start，end 时间查询，默认createTime
     * @return
     */
    protected String getTimeType(){
        return "createTime";
    }

    protected void getTimeParam(String time, E example, SimpleDateFormat format, Root<E> root, CriteriaBuilder cb, List<Predicate> predicates) {
        try {
            if (StringUtils.hasText(example.getStartTime())) {
                Long startTime = format.parse(example.getStartTime() + " 00:00:00").getTime();
                predicates.add(cb.greaterThanOrEqualTo(root.get(time), new Date(startTime)));
            }
            if (StringUtils.hasText(example.getEndTime())) {
                //多加一天时间的毫秒数
                Long endTime = format.parse(example.getEndTime() + " 00:00:00").getTime() + ONE_DAY_TIME_STAMP;
                predicates.add(cb.lessThan(root.get(time), new Date(endTime)));
            }
        } catch (ParseException e) {
            throw new BaseException("时间格式转换错误");
        }
    }

    /**
     * 可重写的条件改动
     * @param clz
     * @param filterMap
     * @return
     */
    protected Map<String, Object> changeFilter(Class<E> clz, Map<String, Object> filterMap) {
        return filterMap;
    }

    /**
     * 可重写的查询条件
     * @param whereEntry
     * @param root
     * @param cb
     * @return
     */
    protected Pair<Boolean, Predicate> overrideSingleWhere(Map.Entry<String, ?> whereEntry, Root<E> root, CriteriaBuilder cb) {
        Pair pair = Pair.of(false, "");
        return pair;
    }

    /**
     * 根据月设置startTime,endTime
     * @param e
     * @param monthDate
     */
    protected void setMonthStartAndEnd(E e, String monthDate){
        Date measureTime = DateUtil.getMonth(monthDate);
        Optional.ofNullable(measureTime).orElseThrow(() -> new BaseException(ResultEnum.PARAM_ERROR.getCode(), "时间格式错误"));
        e.setStartTime(DateUtil.formatDateTime(measureTime));
        e.setEndTime(DateUtil.formatDateTime(DateUtil.lastDayMonth(measureTime)));
    }


    /**
     * delete相关的为逻辑删除
     * @param e
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(E e) {
        List<E> list = findListAll(e);
        if(!CollectionUtil.isEmpty(list)) {
            delete(list);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        jpa.deleteById(id);
        jpa.flush();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<String> ids) {
        List<E> list = findListIdIn(ids);
        if(!CollectionUtil.isEmpty(list)) {
            jpa.deleteAll(list);
            jpa.flush();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<E> list) {
        jpa.deleteAll(list);
        jpa.flush();
    }


    /**
     * remove相关的为物理删除(慎用)
     * @param e
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(E e) {
        List<E> list = findListAll(e);
        if(!CollectionUtil.isEmpty(list)) {
            jpa.deleteInBatch(list);
            jpa.flush();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(String id) {
        List<E> list = Arrays.asList(findById(id));
        jpa.deleteInBatch(list);
        jpa.flush();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByIds(List<String> ids) {
        List<E> list = findListIdIn(ids);
        if(!CollectionUtil.isEmpty(list)) {
            jpa.deleteInBatch(list);
            jpa.flush();
        }
    }
}
