package com.dh.digitalbooking.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dh.digitalbooking.dto.UserDTORequest;
import com.dh.digitalbooking.dto.UserDTOResponse;
import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.security.JWTAuthenticationFilter;
import com.dh.digitalbooking.services.impl.RoleServiceImpl;
import com.dh.digitalbooking.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class UserController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTOResponse> findAll(Pageable pageable){
        Page<UserEntity> usersList = userService.findAll(pageable);
        return usersList.map(UserDTOResponse::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody @Valid UserDTORequest userDTO) throws RoleNotFoundException {
        RoleEntity role = (userDTO.getRoleId() == null) ? roleService.findById(2) : roleService.findById(userDTO.getRoleId());
        UserEntity user = new UserEntity();
        switch (role.getName()) {
            case "ROLE_CLIENT":
                user = new ClientEntity(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                        userDTO.getPassword(), role);
                break;
            case "ROLE_PROPRIETOR":
                user = new ProprietorEntity(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                        userDTO.getPassword(), role);
                break;
            case "ROLE_ADMIN":
                user = new AdministratorEntity(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                        userDTO.getPassword(), role);
                break;
        }
        return userService.save(user);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTOResponse findById(@PathVariable Integer id) throws UserNotFoundException {
        return new UserDTOResponse(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws UserNotFoundException {
        userService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTOResponse update(@RequestBody @Valid UserDTORequest userDTO) throws UserNotFoundException, RoleNotFoundException {
        UserEntity user = userService.findById(userDTO.getId());
        RoleEntity role = (userDTO.getRoleId() == null) ? user.getRole() : roleService.findById(userDTO.getRoleId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(role);
        return new UserDTOResponse(userService.update(user));
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public UserDTOResponse getUserInfo(Authentication authentication) throws UserNotFoundException {
        UserEntity userInfo = userService.findByEmail(authentication.getName());
        return new UserDTOResponse(userInfo);
    }

    @GetMapping("/refresh/token")
    @ResponseStatus(HttpStatus.OK)
    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ATTRIBUTE_PREFIX = "Bearer ";
        String attribute = request.getHeader(AUTHORIZATION);
        if(attribute==null || !attribute.startsWith(ATTRIBUTE_PREFIX)){
            return;
        }

        try {
            String refreshToken = attribute.replace(ATTRIBUTE_PREFIX, "");

            DecodedJWT decoderJWT = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD))
                    .build()
                    .verify(refreshToken);
            String username = decoderJWT.getSubject();
            UserEntity user = userService.findByEmail(username);

            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTAuthenticationFilter.TOKEN_EXPIRATION))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", List.of(user.getRole().getName()))
                    .sign(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD));

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);


        }catch (Exception exception){
            response.setHeader("error_message", exception.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> errors = new HashMap<>();
            errors.put("error", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
    }
}
