package mx.uam.tsis.ejemplobackend.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Alumno {
	
	@NotNull
	private Integer matricula;
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	private String carrera;
}
