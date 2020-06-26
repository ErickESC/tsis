/**
 * 
 */
package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;

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
		if(!(alumnoRepository.existByMAtricula(nuevoAlumno.getMatricula()))) {
			return alumnoRepository.saveALumno(nuevoAlumno);
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los alumnos
	 */
	public List<Alumno> retriveAll(){
		return alumnoRepository.find();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return Alumno
	 */
	public Alumno retrive(Integer matricula){
		return alumnoRepository.findByMatricula(matricula);
	}
	
	/**
	 * 
	 * @param alumnoActualizado
	 * @return Alumno actualizado, null en caso de no haberse encontrado
	 */
	public Alumno update(Alumno alumnoActualizado){
		return alumnoRepository.updateAlumno(alumnoActualizado);
	}
	
	
	public boolean delete(Integer matricula){
		
		Alumno result = alumnoRepository.deleteByMatricula(matricula);
		
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(Integer matricula){
		return alumnoRepository.existByMAtricula(matricula);
	}
	
}
