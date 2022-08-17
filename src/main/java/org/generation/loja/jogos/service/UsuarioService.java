package org.generation.loja.jogos.service;



import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.loja.jogos.model.UsuarioModel;
import org.generation.loja.jogos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.generation.loja.jogos.model.UsuarioLoginModel;



	@Service
	public class UsuarioService {

	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {

	        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
	            return Optional.empty();

	        usuario.setSenha(criptografarSenha(usuario.getSenha()));

	        return Optional.of(usuarioRepository.save(usuario));
	    
	    }

	    public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {
	        
	        if(usuarioRepository.findById(usuario.getId()).isPresent()) {

	            Optional<UsuarioModel> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

	            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
	                throw new ResponseStatusException(
	                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

	            usuario.setSenha(criptografarSenha(usuario.getSenha()));

	            return Optional.ofNullable(usuarioRepository.save(usuario));
	            
	        }

	        return Optional.empty();
	    
	    }   

	    public Optional<UsuarioLoginModel> autenticarUsuario(Optional<UsuarioLoginModel> usuarioLoginModel) {

	        Optional<UsuarioModel> usuario = usuarioRepository.findByUsuario(usuarioLoginModel.get().getUsuario());

	        if (usuario.isPresent()) {

	            if (compararSenhas(usuarioLoginModel.get().getSenha(), usuario.get().getSenha())) {

	                usuarioLoginModel.get().setId(usuario.get().getId());
	                usuarioLoginModel.get().setNome(usuario.get().getNome());
	                usuarioLoginModel.get().setFoto(usuario.get().getFoto());
	                usuarioLoginModel.get().setToken(gerarBasicToken(usuarioLoginModel.get().getUsuario(),        usuarioLoginModel.get().getSenha()));
	                usuarioLoginModel.get().setSenha(usuario.get().getSenha());

	                return usuarioLoginModel;

	            }
	        }   

	        return Optional.empty();
	        
	    }

	    private String criptografarSenha(String senha) {

	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        
	        return encoder.encode(senha);

	    }
	    
	    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
	        
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        
	        return encoder.matches(senhaDigitada, senhaBanco);

	    }

	    private String gerarBasicToken(String usuario, String senha) {

	        String token = usuario + ":" + senha;
	        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
	        return "Basic " + new String(tokenBase64);

	    }

	}
	

