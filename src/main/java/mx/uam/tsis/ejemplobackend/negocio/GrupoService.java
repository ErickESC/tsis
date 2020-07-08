package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

/**
 * @author erick
 *
 */
@Slf4j
@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private AlumnoService alumnoService;
	
	public Grupo create(Grupo nuevoGrupo) {
		
		//Ya hay garantia de que no habra dos grupos con el mismo ID gracias al autogenerado
		try {
			return grupoRepository.save(nuevoGrupo);
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los grupos
	 */
	public Iterable <Grupo> retriveAll(){
		return grupoRepository.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return grupo
	 */
	public Optional<Grupo> retrive(Integer id){
		
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		
		if(grupoOpt.isPresent()) {
			return grupoOpt;
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param grupoActualizado
	 * @return Grupo actualizado, null en caso de no haberse encontrado
	 */
	public Grupo update(Grupo grupoActualizado){ 
		log.info("Llamado a update");
		Optional <Grupo>grupoOpt = grupoRepository.findById(grupoActualizado.getId());

		if(grupoOpt.isPresent()) {
			return grupoRepository.save(grupoActualizado);
		}else {
			log.info("El grupo no existe");
			return null;
		}
	}
	
	
	public boolean delete(Integer matricula){

		try {
			grupoRepository.deleteById(matricula);
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
	public boolean exist(Integer id){
		return grupoRepository.existsById(id);
	}
	
	/**
	 * Permite agregar un alumno a un grupo
	 * 
	 * @param id del grupo
	 * @param matricula del alumno
	 * @return true si se agrego con exito, false en caso contrario
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		
		log.info("Agregando alumno con matricula "+matricula+" al grupo "+groupId);
		
		// 1.- Recuperamos primero al alumno
		Alumno alumno = alumnoService.retrive(matricula);
		
		// 2.- Recuperamos el grupo
		Optional <Grupo> grupoOpt = grupoRepository.findById(groupId);
		
		// 3.- Verificamos que el alumno y el grupo existan
		if(!grupoOpt.isPresent() || alumno == null) {
			
			log.info("No se encontró alumno o grupo");
			
			return false;
		}
		log.info("Agregado el alumno con matricula "+matricula+" al grupo "+groupId);
		// 4.- Agrego el alumno al grupo
		Grupo grupo = grupoOpt.get();
		grupo.addAlumno(alumno);
		
		// 5.- Persistir el cambio
		grupoRepository.save(grupo);
		
		return true;
	}
}
