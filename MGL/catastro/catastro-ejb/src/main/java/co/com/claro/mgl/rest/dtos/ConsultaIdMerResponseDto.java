package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johanForero
 */
@XmlRootElement(name = "consultaIdMerResponse")
public class ConsultaIdMerResponseDto extends CmtDefaultBasicResponse {

	@XmlElement
	private int idMER;
	
	@XmlElement
	private String tipoUbicacion;

	public String getTipoUbicacion() {
		return tipoUbicacion;
	}

	public void setTipoUbicacion(String tipoUbicacion) {
		this.tipoUbicacion = tipoUbicacion;
	}

	public int getIdMER() {
		return idMER;
	}

	public void setIdMER(int idMER) {
		this.idMER = idMER;
	}


}
