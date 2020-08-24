package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.lang.EntityStatus;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.base.utils.MD5;
import com.wang.blog.param.UserParam;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.BadgesCount;
import com.wang.blog.vo.UserVO;
import com.wang.blog.repository.UserRepository;
import com.wang.blog.service.MessageService;
import com.wang.blog.service.UserService;
import com.wang.common.common.base.BaseException;
import com.wang.common.entity.user.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author wjx
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public Map<String, UserVO> findMapByIds(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<UserEntity> list = userRepository.findAllById(ids);
        Map<String, UserVO> ret = new HashMap<>();

        list.forEach(po -> ret.put(po.getId(), BeanMapUtils.copy(po)));
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile login(String username, String password) {
        UserEntity po = userRepository.findByUsername(username);
        AccountProfile u = null;

        Assert.notNull(po, "账户不存在");

        Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

        po.setLastLogin(Calendar.getInstance().getTime());
        userRepository.save(po);
        u = BeanMapUtils.copyPassport(po);
        String avatarStart = "/image";
        if(po.getAvatar().startsWith(avatarStart)){
            String avatar = po.getAvatar();
            u.setAvatar(avatar);
        }
        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));
        u.setBadgesCount(badgesCount);

        return u;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile loginOther(String username, String openId) {
        UserEntity po = userRepository.findByUsername(username);
        AccountProfile u = null;

        Assert.notNull(po, "账户不存在");

        List<String> openIds = Arrays.asList(po.getOpenId().split(","));
        Boolean passwordEqual = false;
        for (String userOpenId: openIds){
            if(StringUtils.equals(userOpenId, openId)){
                passwordEqual = true;
                break;
            } else {
                continue;
            }
        }
        Assert.state(passwordEqual, "密码错误");

        po.setLastLogin(Calendar.getInstance().getTime());
        userRepository.save(po);
        u = BeanMapUtils.copyPassport(po);

        String avatarStart = "/image";
        if(po.getAvatar().startsWith(avatarStart)){
            String avatar = po.getAvatar();
            u.setAvatar(avatar);
        }

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));

        u.setBadgesCount(badgesCount);
        return u;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile findProfile(String id) {
        UserEntity po = userRepository.findById(id).get();
        AccountProfile u = null;

        Assert.notNull(po, "账户不存在");
        po.setLastLogin(Calendar.getInstance().getTime());

        u = BeanMapUtils.copyPassport(po);

        String avatarStart = "/image";
        if(po.getAvatar().startsWith(avatarStart)){
            String avatar = po.getAvatar();
            u.setAvatar(avatar);
        }
        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));
        u.setBadgesCount(badgesCount);

        return u;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity register(UserParam user, Boolean active) {
        Assert.notNull(user, "Parameter user can not be null!");

        Assert.hasLength(user.getUsername(), "用户名不能为空!");
        Assert.hasLength(user.getPassword(), "密码不能为空!");

        UserEntity checkUserName = userRepository.findByUsername(user.getUsername());
        Assert.isNull(checkUserName, "用户名已经存在!");

        if(!StringUtils.isEmpty(user.getEmail())) {
            UserEntity checkEmail = userRepository.findByEmail(user.getEmail());
            Assert.isNull(checkEmail, "邮箱已注册!");
        }

        UserEntity po = new UserEntity();

        BeanUtils.copyProperties(user, po);

        if (StringUtils.isBlank(po.getName())) {
            po.setName(user.getUsername());
        }

        po.setPassword(MD5.md5(user.getPassword()));
        if(active) {
            po.setStatus(EntityStatus.ENABLED);
        }else {
            po.setStatus(EntityStatus.DISABLED);
        }
        po.setComments(0);
        po.setPosts(0);

        String state = MD5.md5(user.getUsername()+user.getEmail()+user.getPassword());
        po.setState(state);
        po.setAvatar(Consts.AVATAR);

        userRepository.save(po);

        return po;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity activation(String userId, String state) {
        UserEntity check = userRepository.findByIdAndState(userId, state);
        if(!Optional.ofNullable(check).isPresent()) {
            return null;
        }
        if (check.getStatus() == 0){
            return check;
        }
        check.setStatus(Consts.ACTIVE);
        userRepository.saveAndFlush(check);
        return check;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile update(UserVO user) {
        UserEntity po = userRepository.findById(user.getId()).get();
        po.setName(user.getName());
        po.setSignature(user.getSignature());
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile updateEmail(String id, String email) {
        UserEntity po = userRepository.findById(id).get();

        if (email.equals(po.getEmail())) {
            throw new BaseException("邮箱地址没做更改");
        }

        UserEntity check = userRepository.findByEmail(email);

        if (check != null && check.getId() != po.getId()) {
            throw new BaseException("该邮箱地址已经被使用了");
        }
        po.setEmail(email);
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    public UserVO get(String userId) {
        Optional<UserEntity> optional = userRepository.findById(userId);
        return optional.map(BeanMapUtils::copy).orElse(null);
    }

    @Override
    public UserVO getByUsername(String username) {
        return BeanMapUtils.copy(userRepository.findByUsername(username));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserVO getByEmail(String email) {
        return BeanMapUtils.copy(userRepository.findByEmail(email));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountProfile updateAvatar(String id, String path) {
        UserEntity po = userRepository.findById(id).get();
        po.setAvatar(path);
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String id, String newPassword) {
        UserEntity po = userRepository.findById(id).get();

        Assert.hasLength(newPassword, "密码不能为空!");

        po.setPassword(MD5.md5(newPassword));
        userRepository.save(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String id, String oldPassword, String newPassword) {
        UserEntity po = userRepository.findById(id).get();

        Assert.hasLength(newPassword, "密码不能为空!");

        Assert.isTrue(MD5.md5(oldPassword).equals(po.getPassword()), "当前密码不正确");
        po.setPassword(MD5.md5(newPassword));
        userRepository.save(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserEntity user) {
        userRepository.saveAndFlush(user);
    }

}