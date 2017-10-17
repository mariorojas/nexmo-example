package com.teknei.domain;

import lombok.Data;

/**
 * Entidad para gestión de envío de mensajes
 * 
 * @author marojas
 *
 */
@Data
public class TextMessage {

	/**
	 * Destinatario del mensaje
	 */
	private String to;
	
	/**
	 * Contenido del mensaje a enviar
	 */
	private String body;
}
