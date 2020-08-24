package com.wang.blog.base.utils;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.vo.*;
import com.wang.common.entity.blog.*;
import com.wang.common.entity.user.UserEntity;
import org.springframework.beans.BeanUtils;

/**
 * @author wjx
 */
public class BeanMapUtils {
    private static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

    public static UserVO copy(UserEntity po) {
        if (po == null) {
            return null;
        }
        UserVO ret = new UserVO();
        BeanUtils.copyProperties(po, ret, USER_IGNORE);
        return ret;
    }

    public static AccountProfile copyPassport(UserEntity po) {
        AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
        passport.setName(po.getName());
        passport.setEmail(po.getEmail());
        passport.setAvatar(po.getAvatar());
        passport.setLastLogin(po.getLastLogin());
        passport.setStatus(po.getStatus());
        return passport;
    }

    public static CommentVO copy(CommentEntity po) {
        CommentVO ret = new CommentVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostVO copy(PostEntity po) {
        PostVO d = new PostVO();
        BeanUtils.copyProperties(po, d);
        return d;
    }

    public static MessageVO copy(MessageEntity po) {
        MessageVO ret = new MessageVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static FavoriteVO copy(FavoriteEntity po) {
        FavoriteVO ret = new FavoriteVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostTagVO copy(PostTagEntity po) {
        PostTagVO ret = new PostTagVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static TagVO copy(TagEntity po) {
        TagVO ret = new TagVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static String[] postOrder(String order) {
        String[] orders;
        switch (order) {
            case Consts.order.HOTTEST:
                orders = new String[]{"comments", "views", "createTime"};
                break;
            case Consts.order.FAVOR:
                orders = new String[]{"favors", "createTime"};
                break;
            default:
                orders = new String[]{"weight", "createTime"};
                break;
        }
        return orders;
    }
}
