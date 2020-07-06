package mx.uam.tsis.ejemplobackend.servicios;

import java.util.Optional;

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
import mx.uam.tsis.ejemplobackend.negocio.GrupoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class GrupoController {
	
	
	@Autowired
	private GrupoService grupoService;
	
	/**
	 * 
	 * @param nuevoGrupo
	 * @return status creado y Grupo creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear Grupo",
			notes = "Permite crear un nuevo Grupo"
			)
	@PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo) {
		
		log.info("Recibí llamada a create con "+ nuevoGrupo);
		
		Grupo grupo = grupoService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de grupos
	 */
	@ApiOperation(
			value = "Regresa todos los Grupos",
			notes = "Regresa un json con una lista de los Grupos en la BD"
			)
	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Grupo> result = grupoService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param id
	 * @return status ok y grupo solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa Grupo",
			notes = "Regresa un json con los datos del grupo solicitado"
			)
	@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") @Valid Integer id) {
		log.info("Buscando al alumno con matricula "+id);
		
		Optional<Grupo> grupo = grupoService.retrive(id);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro grupo");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status ok y grupo actualizado, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza grupo",
			notes = "Permite actualizar los datos de un grupo existente en la DB"
			)
	@PutMapping(path = "/grupos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Grupo grupoActualizado) {
		
		log.info("Recibí llamada a update con "+ grupoActualizado);
		
		Grupo grupo;
		
		//Revisa que exista en el repositorio de grupos
		if(grupoService.exist(grupoActualizado.getId())) {
			try {
				
				grupo = grupoService.update(grupoActualizado);
				
				log.info("Se actualizo el alumno con matricula: "+ grupo.getId()+" Datos: "+grupo);
				
				return ResponseEntity.status(HttpStatus.OK).body(grupo);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar alumno");
			}
		}else {
			log.info("No se encontro al alumno con matricula: "+ grupoActualizado.getId());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro al alumno con matricula: "+ grupoActualizado.getId());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar el grupo
	 */
	@ApiOperation(
			value = "Borra Grupo",
			notes = "Permite borrar un grupo de la BD"
			)
	@DeleteMapping(path = "/grupos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("matricula") @Valid Integer id) {
		
		log.info("Recibí llamada a delete con "+ id);
		
		//Revisa que exista en el repositorio de alumnos
		if(grupoService.delete(id)) {
			try {
				
				log.info("Se elimino el alumno con matricula: "+ id);
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				
			}catch(Exception e){
				e.fillInStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}else {
			log.info("No se encontro al alumno con matricula: "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/**
	 * POST /grupos/id del grupo/alumnos?matricula={1234}
	 * 
	 * @param nuevoGrupo
	 * @return 
	 */
	@ApiOperation(
			value = "Agregar alumno a grupo",
			notes = "Permite Agregar un Alumno a un Grupo"
			)
	@PostMapping(path = "/grupos/{id}/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addStudentToGroup(@PathVariable("id") Integer id, @PathVariable("matricula") Integer matricula) {
		
		log.info("Recibí llamada a addStudent con grupo"+ id +" y matricula: "+matricula);
		
		boolean result = grupoService.addStudentToGroup(id, matricula);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
