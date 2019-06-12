package com.besafx.app.controller;

import com.besafx.app.dto.JwtResponse;
import com.besafx.app.dto.LoginForm;
import com.besafx.app.jwt.JwtProvider;
import com.besafx.app.dao.RoleDao;
import com.besafx.app.dao.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
@Api(
        value = "/api/auth",
        description = "Authorization REST API with JSON Web Token",
        tags = {"Authorization (Using JSON Web Token)"}
)
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    @ApiOperation(
            nickname = "authenticateUser",
            value = "Generating Token",
            notes = "You must use this call before other calls to generate token required for authentication, where token type is Bearer.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public JwtResponse authenticateUser(
            @ApiParam(value = "Try filling required fields with login credentials.", required = true)
            @Valid @RequestBody LoginForm loginRequest
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, "Bearer", userDetails.getUsername(), userDetails.getAuthorities());
    }


}
