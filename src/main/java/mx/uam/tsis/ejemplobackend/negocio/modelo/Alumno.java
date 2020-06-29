package mx.uam.tsis.ejemplobackend.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity //Indica que hay que persistir en BD
public class Alumno {
	
	@ApiModelProperty(notes = "Matricula del alumno", required = true)
	@NotNull
	@Id //Indica que es la llave primaria
	private Integer matricula;
	
	@ApiModelProperty(notes = "Nombre del alumno", required = true)
	@NotBlank
	private String nombre;
	
	@ApiModelProperty(notes = "Carrera del alumno", required = true)
	@NotBlank
	private String carrera;
}
