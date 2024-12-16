package com.green.greengram.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.config.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final SecretKey secretKey;

    //DI(Defendency Injection) - 외부에서 들어오는 데이터를 DI 라고 함
    // 스프링컨테이너 - 빈등록 되어서 가능 / @Service - @Component 최상위 어노테이션 으로 빈등록
    public TokenProvider(JwtProperties jwtProperties, ObjectMapper objectMapper) {
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecretKey()));
        // hamc 대칭 , sha 암호화
        // BASE64URL 을 인코딩 -> decode
    }
    // jackson 라이브러리 - json 객체 형태 직렬화 해주기 위해서 사용
    // di 받아올꺼임

    //jwt 토큰 생성 , Duration (기간 설정)
    public String generateToken(JwtUser jwtUser , Duration expiredAt){
        Date now = new Date();
        return makeToken(jwtUser , new Date(now.getTime() + expiredAt.toMillis()));
        // date 에서 현재시간 + 생성했을 당시 유효기간 = 종료 시간 설정
    }
    private String makeToken(JwtUser jwtUser , Date expiry) {
        return Jwts.builder()
                .header().add("typ","JWT")
                         .add("alg","hs256")
                .and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expiry)
                .claim("signedUser", makeClaimByUserToString(jwtUser))
                .signWith(secretKey) // jwt 암호화
                .compact();
    }
    private String makeClaimByUserToString(JwtUser jwtUser){
        // 객체 자체를 jwt 에 담고 싶어서 객체를 직렬화
        // jwtUser 에 담았는 데이터를 JSON 형태의 문자열로 변환
        try {
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean validToken (String token){
        // jwt 복호화
        try {
            getClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    // spring - security - Authentication 사용
    public Authentication getAuthentication(String token){
        UserDetails userDetails = getUserDeatailsFromToken(token);
        // LIST 는 중복 값 담기 가능 , SET 은 똑같은 데이터 담으면 중복 값 제외함
        // ROLE_USER 하드코딩 -> ROLE_ 통해서 권한 설정
        // return new UsernamePasswordAuthenticationToken();
        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        // 리턴 타입이 UsernamePasswordAuthenticationToken 인데 이거는 Authentication 의 자식이라서 담을 수 있음
    }
    public UserDetails getUserDeatailsFromToken (String token){
        Claims claims = getClaims(token);
        String json = (String)claims.get("signedUser"); // 직렬화

        // 객체화 과정
        JwtUser jwtUser = objectMapper.convertValue(json,JwtUser.class);
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setJwtUser(jwtUser);

        return userDetails;
    }
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
