/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johanForero
 */
@XmlRootElement(name = "consultaIdMerRequest")
public class ConsultaIdMerRequestDto {

	@XmlElement
	private String idRR;

	public String getIdRR() {
		return idRR;
	}

	public void setIdRR(String idRR) {
		this.idRR = idRR;
	}

}
