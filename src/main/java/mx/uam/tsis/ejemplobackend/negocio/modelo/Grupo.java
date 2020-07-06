package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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
public class Grupo {
	
	@ApiModelProperty(notes = "ID del grupo", required = true)
	@Id //Indica que es la llave primaria
	@GeneratedValue //Genera la clave de manera automatica
	private Integer id;
	
	@ApiModelProperty(notes = "Clave del grupo", required = true)
	@NotBlank
	private String clave;
	
	@ApiModelProperty(notes = "Lista de alumnos", required = true)
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY)
	private List <Alumno> alumnos = new ArrayList <> ();
	
	public boolean addAlumno(Alumno alumno) {
		return alumnos.add(alumno);
	}
}
