package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Service
public class UserDetailsCustom implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.findByEmail(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário e/ou senha inválidos");
		} else {
			return usuario;
		}
	}
}

