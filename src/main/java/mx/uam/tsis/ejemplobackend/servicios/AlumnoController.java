package mx.uam.tsis.ejemplobackend.servicios;




import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return status creado y alumno crado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear Alumno",
			notes = "Permite crear un nuevo alumno y la matricula debe ser unica"
			)
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) {
		
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de alumnos
	 */
	@ApiOperation(
			value = "Regresa todos los Alumnos",
			notes = "Regresa un json con una lista de los Alumnos en la BD"
			)
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Alumno> result = alumnoService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param matricula
	 * @return status ok y alumno solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa Alumno",
			notes = "Regresa un json con los datos del alumno solicitado"
			)
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") @Valid Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrive(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro alumno");
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return status ok y alumno actualizado, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza alumno",
			notes = "Permite actualizar los datos de un alumno existente en la DB"
			)
	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Alumno alumnoActualizado) {
		
		log.info("Recibí llamada a update con "+ alumnoActualizado);
		
		Alumno alumno;
		
		//Revisa que exista en el repositorio de alumnos
		if(alumnoService.exist(alumnoActualizado.getMatricula())) {
			try {
				
				alumno = alumnoService.update(alumnoActualizado);
				
				log.info("Se actualizo el alumno con matricula: "+ alumno.getMatricula()+" Datos: "+alumno);
				
				return ResponseEntity.status(HttpStatus.OK).body(alumno);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar alumno");
			}
		}else {
			log.info("No se encontro al alumno con matricula: "+ alumnoActualizado.getMatricula());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro al alumno con matricula: "+ alumnoActualizado.getMatricula());
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar al alumno
	 */
	@ApiOperation(
			value = "Borra Alumno",
			notes = "Permite borrar un alumno de la BD"
			)
	@DeleteMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("matricula") @Valid Integer matricula) {
		
		log.info("Recibí llamada a delete con "+ matricula);
		
		//Revisa que exista en el repositorio de alumnos
		if(alumnoService.delete(matricula)) {
			try {
				
				log.info("Se elimino el alumno con matricula: "+ matricula);
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				
			}catch(Exception e){
				e.fillInStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}else {
			log.info("No se encontro al alumno con matricula: "+matricula);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
