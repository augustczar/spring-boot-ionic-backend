package com.augustczar.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augustczar.cursomc.domain.Categoria;
import com.augustczar.cursomc.repositories.CategoriaRepository;
import com.augustczar.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired	
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
	}
}
