package com.xinyu.security.util;


import com.xinyu.common.utils.RedisUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出登录
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisUtils redisUtils;

    public TokenLogoutHandler(TokenManager tokenManager, RedisUtils redisUtils) {
        this.tokenManager = tokenManager;
        this.redisUtils = redisUtils;
    }

    /**
     * 退出登录
     * @param request 响应
     * @param response 返回
     * @param authentication
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //1.从header种获取去token
        //2.token不为空，则移除token，从redis删除token
        String token = request.getHeader("token");
        if (token!=null){
            //从tokn中获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            redisUtils.del(username);

        }
    }
}
