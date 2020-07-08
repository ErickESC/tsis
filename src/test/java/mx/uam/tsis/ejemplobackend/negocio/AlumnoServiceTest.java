/**
 * 
 */
package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito
@Slf4j
public class AlumnoServiceTest {
	
	@Mock
	private AlumnoRepository alumnoRepositoryMock; //Mock generado por mockito
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private AlumnoService alumnoService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(1234);
		alumno.setNombre("Erick");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(null));
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa un nuevo alumno para guardarlo
		when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
		
		//Unidad que quiero probar
		alumno = alumnoService.create(alumno);
		
		//Compruwbo el resultado
		assertNotNull(alumno); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulCreate() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(1234);
		alumno.setNombre("Erick");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que ya ha sido guardado
		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(alumno));
		
		//Unidad que quiero probar
		alumno = alumnoService.create(alumno);
		
		//Compruwbo el resultado
		assertNull(alumno); //Probar que la referencia a alumno es nula porque el alumno ya existia
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(1234);
		alumno.setNombre("Erick");
		
		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(alumno));
		
		//Unidad que quiero probar
		alumno = alumnoService.retrive(1234);
		
		//Compruwbo el resultado
		assertNotNull(alumno); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		Alumno alumno = new Alumno();

		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		alumno = alumnoService.retrive(1234);
		
		//Compruwbo el resultado
		assertNull(alumno); //Probar que la referencia a alumno es no nula
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(1234);
		alumno.setNombre("Erick");
		
		Alumno alumnoActualizado = new Alumno();
		alumnoActualizado.setCarrera("Computacion");
		alumnoActualizado.setMatricula(1234);
		alumnoActualizado.setNombre("Juanin");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(alumno));
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa un nuevo alumno para guardarlo
		when(alumnoRepositoryMock.save(alumnoActualizado)).thenReturn(alumnoActualizado);
		
		//Unidad que quiero probar
		alumno = alumnoService.update(alumnoActualizado);
		
		//Compruwbo el resultado
		assertEquals(alumno,alumnoActualizado);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Alumno alumno = new Alumno();
		
		Alumno alumnoActualizado = new Alumno();
		alumnoActualizado.setCarrera("Computacion");
		alumnoActualizado.setMatricula(1234);
		alumnoActualizado.setNombre("Juanin");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(alumnoRepositoryMock.findById(1234)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		alumno = alumnoService.update(alumnoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,alumno);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = alumnoService.delete(1234);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(alumnoRepositoryMock.existsById(1234)).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = alumnoService.exist(1234);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(alumnoRepositoryMock.existsById(1234)).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = alumnoService.exist(1234);
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}
