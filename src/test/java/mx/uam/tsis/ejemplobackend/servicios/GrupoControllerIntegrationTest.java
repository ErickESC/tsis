package mx.uam.tsis.ejemplobackend.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

/**
 * 
 * Prueba de integración para el endpoint alumnos
 * 
 * @author erick
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@BeforeEach
	public void prepare() {
		log.info("COMIENZA PREPARE");
		// Aqui se puede hacer cosas para preparar sus casos de prueba, incluyendo
		// agregar datos a la BD
		// Creo el alumno que voy a agregar
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(1234);
		alumno.setNombre("PerryElOrnitorringo");
		
		//Creo el grupo en donde agregare al alumno
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Alumno> request = new HttpEntity <> (alumno, headers);

		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Grupo> request2 = new HttpEntity <> (grupo, headers);
		
		restTemplate.exchange("/alumnos", HttpMethod.POST, request, Alumno.class);
		
		restTemplate.exchange("/grupos", HttpMethod.POST, request2, Grupo.class);
	}
	
	@AfterEach
	public void destroy() {
		log.info("COMIENZA DESTROY");
		// Aqui se puede hacer cosas para deshacer lo que se realizo antes de los casos de prueba
		// Elimino al grupo y al alumno
		int matricula = 1234;
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Integer> request = new HttpEntity <> (matricula, headers);
		
		restTemplate.exchange("/alumnos/"+matricula, HttpMethod.DELETE, request, Integer.class);
	}
	
	/*
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testAddStudentToGroup200() {
		
		// Escribo la matricula del alumno creado en prepare()
		int matricula = 1234;
		// Escribo el id del grupo creado en prepare()
		int id = 1;

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Integer> request = new HttpEntity <> (matricula, headers);
		
		ResponseEntity <Integer> responseEntity = restTemplate.exchange("/grupos/"+id+"/alumnos/"+matricula, HttpMethod.POST, request, Integer.class);
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos se actualizo el grupo con el nuevo alumno
		Optional <Grupo> optGrupo = grupoRepository.findById(id);
		Optional <Alumno> optAlumno = alumnoRepository.findById(matricula);
		
		//Si la lista de alumnos del grupo contiene al alumno entonces la prueba es exitosa
		// NOTA: Se tuvo que utilizar una recuperacion EAGER ya que si se ocupaba LAZY no funcionaria
		assertEquals(true,optGrupo.get().getAlumnos().contains(optAlumno.get()));
	}
	
	@Test
	public void testAddStudentToGroup204() {
		/**
		 * CAMBIANDO CUALQUIERA DE LOS VALORES FUNCIONA
		 */
		// Escribo una matricula del alumno creado en prepare()
		int matricula = 1234;
		// Escribo el id distinto al del grupo creado en prepare()
		int id = 10;
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Integer> request = new HttpEntity <> (matricula, headers);
		
		ResponseEntity <Integer> responseEntity = restTemplate.exchange("/grupos/"+id+"/alumnos/"+matricula, HttpMethod.POST, request, Integer.class);
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos no actualizo el grupo con el alumno o viceversa
		Optional <Grupo> optGrupo = grupoRepository.findById(1);
		Optional <Alumno> optAlumno = alumnoRepository.findById(matricula);
		
		//Si la lista de alumnos del grupo no contiene al alumno, entonces la prueba es exitosa
		// NOTA: Se tuvo que utilizar una recuperacion EAGER ya que si se ocupaba LAZY no funcionaria
		assertEquals(false,optGrupo.get().getAlumnos().contains(optAlumno.get()));
	}
}
