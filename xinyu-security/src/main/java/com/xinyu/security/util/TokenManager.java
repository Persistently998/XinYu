package com.xinyu.security.util;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

/**
 * jwt token生成工具类
 */
@Component
public class TokenManager {


    //设置token的有效时长
    private Date tokenEcpiration(){
        return new Date(System.currentTimeMillis() + 60 * 1000);
    }
    //编码密钥
    private String tokenSignKey="123456";


    //使用jwt根据用户名生成token
    public String createToken(Map<String, Object> username){
        String token = Jwts.builder()
                .setClaims(username) //保存一些信息
                .setExpiration(tokenEcpiration())//设置过期时间
                .signWith(SignatureAlgorithm.HS512,secret);;

        return token;
    }

    //根据toke字符串得到用户信息
    public String getUserInfoFromToken(String token){
        String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }


}
