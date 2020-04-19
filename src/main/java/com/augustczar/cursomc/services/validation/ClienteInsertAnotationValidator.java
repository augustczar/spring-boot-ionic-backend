package com.augustczar.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.augustczar.cursomc.domain.enums.TipoCliente;
import com.augustczar.cursomc.dto.ClienteNewDTO;
import com.augustczar.cursomc.resources.exceptions.FieldMessage;
import com.augustczar.cursomc.services.validation.utils.BR;

public class ClienteInsertAnotationValidator implements ConstraintValidator<ClienteInsertAnotation, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsertAnotation ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		// Inserindo erros customizados na lista
		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
