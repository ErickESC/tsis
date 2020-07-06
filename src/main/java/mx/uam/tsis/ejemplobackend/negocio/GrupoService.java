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
		return grupoRepository.save(nuevoGrupo);
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
		return grupoRepository.findById(id);
	}
	
	/**
	 * 
	 * @param grupoActualizado
	 * @return Grupo actualizado, null en caso de no haberse encontrado
	 */
	public Grupo update(Grupo grupoActualizado){ 
		
		Grupo grupoOpt = grupoRepository.save(grupoActualizado);
		
		return grupoOpt;
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
	public boolean addStudentToGroup(Integer id, Integer matricula) {
		log.info("Agregando alumno con matricula: "+matricula+" al grupo: "+id);
		//Verificamos si existe alumno y grupo
		if(grupoRepository.existsById(id) && alumnoService.exist(matricula)) {
			//Recuperamos el grupo
			Grupo grupo = grupoRepository.findById(id).get();
			//Recuperamos al alumno
			Alumno alumno = alumnoService.retrive(matricula);
			//Agregamoms al alumno
			grupo.addAlumno(alumno);
			log.info("Se agrego alumno a grupo");
			//Persistimos en la BD el grupo
			grupoRepository.save(grupo);
			log.info("Se guardo el cambio en BD");
			return true;
		}else {
			log.info("No se encontro grupo o alumno");
			return false;
		}
		
		
	}
}
