/**
 * 
 */
package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * @author erick
 *
 */
@Service
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return El alumno recien creado, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		//Regla de negocio: no se puede crear mas de un alumno con la misma matricula
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		if(!alumnoOpt.isPresent()) {
			return alumnoRepository.save(nuevoAlumno);
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los alumnos
	 */
	public Iterable <Alumno> retriveAll(){
		return alumnoRepository.findAll();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return Alumno
	 */
	public Alumno retrive(Integer matricula){
		return alumnoRepository.findById(matricula).get();
	}
	
	/**
	 * 
	 * @param alumnoActualizado
	 * @return Alumno actualizado, null en caso de no haberse encontrado
	 */
	public Alumno update(Alumno alumnoActualizado){ 
		
		Alumno alumnoOpt = alumnoRepository.save(alumnoActualizado);
		
		return alumnoOpt;
	}
	
	
	public boolean delete(Integer matricula){
		
		try {
			alumnoRepository.deleteById(matricula);
			return true;
		}catch(Exception e){
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
		return alumnoRepository.existsById(matricula);
	}
	
}
