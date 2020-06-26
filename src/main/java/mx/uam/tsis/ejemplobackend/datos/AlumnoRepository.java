/**
 * 
 */
package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * @author erick
 *
 */
@Component
public class AlumnoRepository {
	
	// La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	/*
	 * Guarda en la base de datos
	 * 
	 * @param nuevoAlumno
	 */
	public Alumno saveALumno(Alumno nuevoAlumno) {
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;
	}
	
	/*
	 * Recupera un alumno en la base de datos
	 * 
	 * @param matricula
	 */
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}
	
	/*
	 * Recupera todos los alumnos de la base de datos
	 * 
	 * @param nuevoAlumno
	 */
	public List <Alumno> find() {
		return new ArrayList <> (alumnoRepository.values());
	}
	
	/*
	 * Actualiza un alumno en la base de datos
	 * 
	 * @param alumnoActualizado
	 */
	public Alumno updateAlumno(Alumno alumnoActualizado) {
		alumnoRepository.put(alumnoActualizado.getMatricula(), alumnoActualizado);
		return alumnoActualizado;
	}
	
	/*
	 * Elimina un alumno en la base de datos
	 * 
	 * @param matricula
	 */
	public Alumno deleteByMatricula(Integer matricula) {
		
		Alumno result = alumnoRepository.remove(matricula);
		
		return result;
	}
	
	/*
	 * Verifica si existe un alumno en la BD
	 * 
	 * @param matricula
	 */
	public boolean existByMAtricula(Integer matricula) {
		return alumnoRepository.containsKey(matricula);
	}
}
