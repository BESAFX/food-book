package com.besafx.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String type;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
