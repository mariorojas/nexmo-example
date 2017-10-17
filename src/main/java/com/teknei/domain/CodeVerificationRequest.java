package com.teknei.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Entidad para verificación de códigos 2FA(Two Factor Authentication)
 * 
 * @author marojas
 *
 */
@Data
public class CodeVerificationRequest {

	/**
	 * Identificador de la petición
	 */
	@JsonProperty("request_id")
	private String requestId;
	
	/**
	 * Código de autenticación
	 */
	private String code;
}
