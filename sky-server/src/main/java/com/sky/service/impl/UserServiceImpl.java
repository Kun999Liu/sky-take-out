package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Author {liukun}
 * @e-mail:liukunjsj@163.com
 * @Date: 2026/01/12/ 21:17
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // 微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        // 调用微信接口服务，获得当前用户的openid
        String openid = getOpenId(userLoginDTO.getCode());
        // 判断openid 是否存在，如果为空表示登录失败，抛出业务异常
        if (openid == null) {
            throw new RuntimeException(MessageConstant.LOGIN_FAILED);
        }
        // 判断当前用户是否为新用户，如果是新用户则自动注册
        User user = userMapper.getByOpenId(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        // 返回用户对象
        return user;
    }

    /**
     * 调用微信接口服务，获得当前用户的openid
     * @param code 小程序端传递的code
     * @return openid
     */
    private String getOpenId(String code) {
        // 调用微信接口服务，获得当前用户的openid
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid()); // 小程序appid
        map.put("secret", weChatProperties.getSecret()); // 小程序秘钥
        map.put("js_code", code); // 小程序端传递的code
        map.put("grant_type", "authorization_code"); // 授权类型，
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        log.info("微信登录接口调用结果: {}",json);
        // 解析json字符串，获得openid
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getString("openid");
    }
}
