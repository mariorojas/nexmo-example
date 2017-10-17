package com.teknei.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;
import com.teknei.domain.CodeVerificationRequest;
import com.teknei.domain.TextMessage;
import com.teknei.util.NexmoSingleton;

/**
 * Controlador para envío de mensajes hacia móviles
 * 
 * @author marojas
 *
 */
@RestController
@RequestMapping("/v1")
public class MessageController {

	@Value("${nexmo.api.key}")
	private String apiKey;
	
	@Value("${nexmo.api.secret}")
	private String apiSecret;
	
	@PostConstruct
	private void postConstruct() {
		NexmoSingleton.getInstance().authenticate(apiKey, apiSecret);
	}
	
	/**
	 * Envío de un mensaje de texto a un número de teléfono
	 * 
	 * @param request Número de teléfono y mensaje a enviar
	 * @return Estado de envio de cada mensaje
	 */
	@PostMapping("/messages")
	public ResponseEntity<?> sendTextMessage(@RequestBody TextMessage request) {
		String from = "5525241022";		
		SmsSubmissionResult[] responses = NexmoSingleton.getInstance()
				.sendSmsMessage(from, request.getTo(), request.getBody());
		return ResponseEntity.ok(responses);
	}
	
	/**
	 * Envía un código 2FA (Two Factor Authentication)
	 * 
	 * @param request Número de teléfono del destinatario
	 * @return Estado de envío del código
	 */
	@PostMapping("/auth/code")
	public ResponseEntity<?> sendTwoFactorAuthenticationCode(@RequestBody TextMessage request) {
		VerifyResult response = NexmoSingleton.getInstance()
				.sendTwoFactorAuthenticationCode(request.getTo());
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Verifica la validez de un código 2FA
	 * @param request Identificador de la petición y código 2FA
	 * @return
	 */
	@PostMapping("/auth/check")
	public ResponseEntity<?> checkTwoFactorAuthenticationCode(@RequestBody CodeVerificationRequest request) {
		CheckResult response = NexmoSingleton.getInstance()
				.checkTwoFactorAuthenticationCode(request.getRequestId(), request.getCode());
		return ResponseEntity.ok(response);
	}
}
