package mx.uam.tsis.ejemplobackend.servicios;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio_modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	// La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	/*
	 * Creacion de alumno
	 */
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
		
		// No se deben agregar dos alumnos con la misma matricula
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	/*
	 * Optencion de todos los alumnos
	 */
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRepository.values());
		
	}

	/*
	 * Optencion de un alumno por su matricula
	 */
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/*
	 * Actualizacion de un alumno
	 */
	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody Alumno alumnoActualizado) {
		
		log.info("Recibí llamada a update con "+ alumnoActualizado);
		
		//Revisa que exista en el repositorio de alumnos
		if(alumnoRepository.containsKey(alumnoActualizado.getMatricula())) {
			try {
				
				alumnoRepository.put(alumnoActualizado.getMatricula(), alumnoActualizado);
				
				log.info("Se actualizo el alumno con matricula: "+ alumnoActualizado.getMatricula()+" Datos: "+alumnoActualizado);
				
				return ResponseEntity.status(HttpStatus.OK).body(alumnoActualizado);
				
			}catch(Exception e){
				e.fillInStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}else {
			log.info("No se encontro al alumno con matricula: "+ alumnoActualizado.getMatricula());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	/*
	 * Borrado de un alumno
	 */
	@DeleteMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		
		log.info("Recibí llamada a delete con "+ matricula);
		
		//Revisa que exista en el repositorio de alumnos
		if(alumnoRepository.containsKey(matricula)) {
			try {
				
				alumnoRepository.remove(matricula);
				
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
