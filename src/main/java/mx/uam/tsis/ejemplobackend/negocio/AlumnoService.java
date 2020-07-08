/**
 * 
 */
package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * @author erick
 *
 */
@Service
@Slf4j
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return El alumno recien creado, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		log.info("Creando alumno con matricula "+nuevoAlumno.getMatricula()+" y nombre "+nuevoAlumno.getNombre());
		//Regla de negocio: no se puede crear mas de un alumno con la misma matricula
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		if(!alumnoOpt.isPresent()) {
			log.info("Creado el alumno con matricula "+nuevoAlumno.getMatricula()+" y nombre "+nuevoAlumno.getNombre());
			return alumnoRepository.save(nuevoAlumno);
		}else {
			log.info("El alumno ya existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los alumnos
	 */
	public Iterable <Alumno> retriveAll(){
		log.info("Regresando arreglo con alumnos");
		return alumnoRepository.findAll();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return Alumno al que le pertenece la matricula
	 */
	public Alumno retrive(Integer matricula){
		log.info("Llamado a regresar al alumno con matricula "+matricula);
		
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		
		if(alumnoOpt.isPresent()) {
			return alumnoOpt.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param alumnoActualizado
	 * @return Alumno actualizado, null en caso de no haberse encontrado
	 */
	public Alumno update(Alumno alumnoActualizado){ 
		
		log.info("Actualizando al alumno con matricula "+alumnoActualizado.getMatricula());
		
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(alumnoActualizado.getMatricula());
		
		if(alumnoOpt.isPresent()) {
			return alumnoRepository.save(alumnoActualizado);
		}else {
			log.info("El alumno no existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return verdadero si se borro bien o falso en caso contrario
	 */
	public boolean delete(Integer matricula){
		
		log.info("Borrando alumno con matricula "+matricula);
		try {
			alumnoRepository.deleteById(matricula);
			return true;
		}catch(Exception e){
			log.info("Algo salio mal");
			e.getMessage();
			return false;
		}	
	}
	
	/**
	 * 
	 * @param matricula
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(Integer matricula){
		log.info("Revisando existencia del alumno con matricula "+matricula);
		return alumnoRepository.existsById(matricula);
	}
	
}
