package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.lang.EntityStatus;
import com.wang.blog.repository.SecurityCodeRepository;
import com.wang.blog.service.SecurityCodeService;
import com.wang.common.common.base.BaseException;
import com.wang.common.entity.user.SecurityCodeEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author wjx
 * @date 2015/8/14
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService {
    @Autowired
    private SecurityCodeRepository securityCodeRepository;


    /**
     * 验证码存活时间 单位：分钟
     */
    private int survivalTime = 30;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String generateCode(String key, int type, String target) {
        SecurityCodeEntity po = securityCodeRepository.findByKey(key);

        String code = RandomStringUtils.randomNumeric(6);
        Date now = new Date();

        if (po == null) {
            po = new SecurityCodeEntity();
            po.setKey(key);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
            po.setStatus(EntityStatus.ENABLED);
        } else {

            long interval = ( now.getTime() - po.getCreateTime().getTime() ) / 1000;

            if (interval <= 60) {
                throw new BaseException("发送间隔时间不能少于1分钟");
            }

            // 把 验证位 置0
            po.setStatus(EntityStatus.ENABLED);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
        }

        securityCodeRepository.save(po);

        return code;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean verify(String key, int type, String code) {
        Assert.hasLength(code, "验证码不能为空");
        SecurityCodeEntity po = securityCodeRepository.findByKeyAndType(key, type);
        Assert.notNull(po, "您没有进行过类型验证");

        Date now = new Date();

        Assert.state(now.getTime() <= po.getExpired().getTime(), "验证码已过期");
        Assert.isTrue(po.getType() == type, "验证码类型错误");
        Assert.isTrue(po.getStatus() == Consts.CODE_STATUS_INIT, "验证码已经使用过");
        Assert.state(code.equals(po.getCode()), "验证码不对");

        po.setStatus(Consts.CODE_STATUS_CERTIFIED);
        securityCodeRepository.save(po);
        return true;
    }

}
