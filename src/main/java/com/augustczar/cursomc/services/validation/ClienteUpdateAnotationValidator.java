package com.augustczar.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.augustczar.cursomc.domain.Cliente;
import com.augustczar.cursomc.domain.enums.TipoCliente;
import com.augustczar.cursomc.dto.ClienteDTO;
import com.augustczar.cursomc.dto.ClienteNewDTO;
import com.augustczar.cursomc.repositories.ClienteRepository;
import com.augustczar.cursomc.resources.exceptions.FieldMessage;
import com.augustczar.cursomc.services.validation.utils.BR;

public class ClienteUpdateAnotationValidator implements ConstraintValidator<ClienteUpdateAnotation, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdateAnotation ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		// passando pro map os atribudos que vem na requisicao, (uri)
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer uriId =  Integer.parseInt(map.get("id"));
				
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente auxCliente = clienteRepository.findByEmail(objDto.getEmail());
		
		if (auxCliente != null && auxCliente.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email ja existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
