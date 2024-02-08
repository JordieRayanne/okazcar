package com.okazcar.okazcar.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okazcar.okazcar.details.UtilisateurDetails;
import com.okazcar.okazcar.models.Role;
import com.okazcar.okazcar.models.Users;
import com.okazcar.okazcar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseHandler {

    public static void showError(HttpServletResponse response, Exception exception, HttpStatus
                                  httpStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Error", exception.getMessage());
        map.put("Status", httpStatus.value());
        String toSend = mapper.writeValueAsString(map);
        response.setContentType("application/json");
        response.getWriter().write(toSend);
    }

    public static String showError(Exception exception, HttpStatus httpStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Error", exception.getMessage());
        map.put("Status", httpStatus.value());
        return mapper.writeValueAsString(map);
    }

    public static String generateResponse(String message, HttpStatus st, Object responseobj) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Message", message);
        map.put("Status", st.value());
        if (responseobj != null)
            map.put("Data", responseobj);
        return getString(map);
    }

    public static String generateErrorResponse(String message, HttpStatus st) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Error", message);
        map.put("Status", st.value());
        return getString(map);
    }

    public static String sendResponseData(Object object, HttpStatus st) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Data", object);
        map.put("Status", st.value());
        return getString(map);
    }

    public static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    public static String getString(Map<String, Object> map) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(map);
    }

    public static String prepareToBeSend(Users users, UtilisateurService utilisateurService) throws IOException {
        String token = utilisateurService.generateToken(users.getUtilisateur().getEmail());
        return prepareToBeSend(token, users, utilisateurService);
    }

    public static String prepareToBeSend(String token, Users users, UtilisateurService utilisateurService) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(utilisateurService.getAuthenticationToken(token, new UtilisateurDetails(users.getUtilisateur(), users.getUtilisateur().getRoles())));
        Map<String, Object> map = new HashMap<>();
        map.put("Token", token);
        map.put("UtilisateurId", users.getUtilisateur());
        map.put("Email", users.getUtilisateur().getEmail());
        map.put("Username", users.getUtilisateur().getUsername());
        if (users.getUserMongoDb() != null) {
            map.put("Image", users.getUserMongoDb().getImage());
        }
        return sendResponseData(map, HttpStatus.ACCEPTED);
    }
}
