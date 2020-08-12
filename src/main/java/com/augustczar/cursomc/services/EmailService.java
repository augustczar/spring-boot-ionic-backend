package com.augustczar.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.augustczar.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
