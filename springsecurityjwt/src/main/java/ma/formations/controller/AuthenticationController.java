package ma.formations.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.formations.domaine.TokenVo;
import ma.formations.domaine.UserVo;
import ma.formations.jwt.JwtUtils;
import ma.formations.service.exception.BusinessException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<TokenVo> authenticateUser(@Valid @RequestBody UserVo userVo) {

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			TokenVo tokenVo = new TokenVo();
			tokenVo.setJwttoken(jwt);
			tokenVo.setUsername(userVo.getUsername());
			Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
			list.forEach(authorite -> tokenVo.getRoles().add(authorite.getAuthority()));
			return ResponseEntity.ok(tokenVo);
		} catch (Exception e) {
			throw new BusinessException("Login ou mot de passe incorrect");
		}

	}
}
