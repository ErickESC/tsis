/**
 * 
 */
package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito
public class GrupoServiceTest {
	
	@Mock
	private GrupoRepository grupoRepositoryMock; //Mock generado por mockito
	
	@Mock
	private AlumnoService alumnoServiceMock; 
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private GrupoService grupoService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA ADD STUDENT TO GROUP
	 */
	
	@Test
	public void testSuccesfulAddStudentToGroup (){
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");

		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.retrive(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(true,result);
		
		assertEquals(grupo.getAlumnos().get(0),alumno);
		
	}
	
	@Test
	public void testUnsuccesfulAddStudentToGroup (){
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.retrive(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.addStudentToGroup(anyInt(), 12345678);
		
		assertEquals(false,result);
	}
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

		grupo = grupoService.create(grupo);
		
		assertNotNull(grupo); 
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		
		//Unidad que quiero probar
		Optional <Grupo> grupoRetrived = grupoService.retrive(1);
		
		//Compruwbo el resultado
		assertNotNull(grupoRetrived); 
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		Grupo grupo = new Grupo();

		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		Optional <Grupo> grupoRetrived = grupoService.retrive(1);
		
		//Compruwbo el resultado
		assertNull(grupoRetrived);
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		Grupo grupoActualizado = new Grupo();
		grupoActualizado.setId(1);
		grupoActualizado.setClave("TST-UPD");
		
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));

		when(grupoRepositoryMock.save(grupoActualizado)).thenReturn(grupoActualizado);
		
		//Unidad que quiero probar
		grupo = grupoService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(grupo,grupoActualizado);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Grupo grupo = new Grupo();
		
		Grupo grupoActualizado = new Grupo();
		grupoActualizado.setId(1);
		grupoActualizado.setClave("TST01");

		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));

		grupo = grupoService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,grupo);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = grupoService.delete(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(grupoRepositoryMock.existsById(1)).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = grupoService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(grupoRepositoryMock.existsById(1)).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = grupoService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}
