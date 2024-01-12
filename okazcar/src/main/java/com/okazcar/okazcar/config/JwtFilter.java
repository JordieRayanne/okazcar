package com.okazcar.okazcar.config;

import com.okazcar.okazcar.services.UtilisateurService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.okazcar.okazcar.handlers.ResponseHandler.showError;


@Component
public class JwtFilter extends OncePerRequestFilter {

	UtilisateurService utilisateurService;
	public JwtFilter(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return ;
		}
		String token;
		String userName = null;

		token = authorizationHeader.replace("Bearer ", "");

		try {
			userName = utilisateurService.extractUsername(token);
		} catch (ExpiredJwtException exception) {
			showError(response, new Exception("Your token is expired"), HttpStatus.FORBIDDEN);
			return;
		} catch (MalformedJwtException exception) {
			showError(response, new Exception("Your token is malformed"), HttpStatus.BAD_REQUEST);
			return;
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = utilisateurService.loadUserByUsername(userName);

			if (utilisateurService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = utilisateurService.getAuthenticationToken(token, userDetails);
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
