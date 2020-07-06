package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * @author erick
 *
 */
public interface AlumnoRepository extends CrudRepository <Alumno, Integer> {//Entidad, Tipo de la llave primaria

}
