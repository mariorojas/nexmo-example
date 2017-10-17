package com.teknei.util;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;

/**
 * Gestión de mensajes de texto a través del API de Nexmo
 * 
 * @author marojas
 *
 */
public class NexmoSingleton {
	
	/**
	 * Instancia singleton de la clase
	 */
	private static NexmoSingleton instance;
	
	/**
	 * Cliente de API Nexmo
	 */
	private NexmoClient client;
	
	/**
	 * Constructor
	 */
	private NexmoSingleton() {}
	
	/**
	 * Autenticación con API
	 * 
	 * @param apiKey Llave de usuario
	 * @param apiSecret Clave de usuario
	 * @return Instancia singleton
	 */
	public NexmoSingleton authenticate(String apiKey, String apiSecret) {
		AuthMethod auth = new TokenAuthMethod(apiKey, apiSecret);
		client = new NexmoClient(auth);
		return instance;
	}
	
	/**
	 * Obtiene la instancia singleton de la clase
	 * 
	 * @return Instancia singleton
	 */
	public static NexmoSingleton getInstance() {
		if (instance == null) {
			instance = new NexmoSingleton();
		}
		return instance;
	}
	
	/**
	 * Enviar mensaje a teléfono móvil
	 * 
	 * @param from Número de teléfono del remitente
	 * @param to Número de teléfono del destinatario
	 * @param body Mensaje
	 * @return Resultados con los envíos de mensajes a cada número
	 */
	public SmsSubmissionResult[] sendSmsMessage(String from, String to, String body) {
		try {
			TextMessage textMessage = new TextMessage(from, to, body);
			SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(textMessage);
			return responses;
		} catch (IOException | NexmoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Envía un código para autenticación por dos pasos (2FA)
	 * 
	 * @param to Número de teléfono del destinatario
	 * @return Estado de envío del código
	 */
	public VerifyResult sendTwoFactorAuthenticationCode(String to) {
		try {
			return client.getVerifyClient().verify(to, "TEKNEI");
		} catch (IOException | NexmoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Verifica un código de autenticación por dos pasos
	 * 
	 * @param requestId Identificador de la petición
	 * @param code Código de autenticación 2FA
	 * @return
	 */
	public CheckResult checkTwoFactorAuthenticationCode(String requestId, String code) {
		try {
			return client.getVerifyClient().check(requestId, code);
		} catch (IOException | NexmoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
}
