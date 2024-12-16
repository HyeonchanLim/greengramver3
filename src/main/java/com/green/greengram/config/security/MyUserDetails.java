package com.green.greengram.config.security;

import com.green.greengram.config.jwt.JwtUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class MyUserDetails implements UserDetails {

    private JwtUser jwtUser;

    @Override // security 권한 확인하는 부분
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(jwtUser.getRoles().size());
        for (String role : jwtUser.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
    // name , password 는 직접 처리할꺼라서 return null; 썻음
    // security 쓸꺼면 return 해줘야함
}