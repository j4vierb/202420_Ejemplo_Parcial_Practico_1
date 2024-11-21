package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ MedicoService.class, EspecialidadService.class, MedicoEspecialidadService.class })
public class MedicoServiceTest {
  @Autowired
  private MedicoService medicoService;
  @Autowired
  private EspecialidadService especialidadService;
  @Autowired
  private MedicoEspecialidadService medicoEspecialidadService;

  @Autowired
  private TestEntityManager entityManager;

  private PodamFactory factory = new PodamFactoryImpl();
  private List<MedicoEntity> medicos = new ArrayList<>();;
  private List<EspecialidadEntity> especialidades = new ArrayList<>();;

  @BeforeEach
  void setup() {
    clearData();
    insertData();
  }

  void clearData() {
    entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    entityManager.getEntityManager().createQuery("delete from MedicoEntity ").executeUpdate();
  }

  void insertData() {
    for(int i = 0; i < 3; i++) {
      MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
      entityManager.persist(medico);
      medicos.add(medico);
      System.out.println("MEDICO ID: " + medicos.get(i).getId());
    }

    for(int i = 0; i < 3; i++) {
      EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
      entityManager.persist(especialidad);
      especialidades.add(especialidad);
      System.out.println("ESPECIALIDAD ID: " + especialidades.get(i).getId());
    }
  }

  @Test
  void createMedicoWithIncorrectName() {
    MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
    medico.setNombre("doctor x");

    assertThrows(IllegalOperationException.class, () -> {
      medicoService.createMedico(medico);
    });
  }

  @Test
  void createMedico() throws IllegalOperationException {
    MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
    medico.setNombre("RMDoctor X");
    medico.setApellido("Perez");
    medico.setRegistro(Arrays.asList("a", "b", "c"));

    MedicoEntity createdMedico = medicoService.createMedico(medico);
    assertEquals(createdMedico.getNombre(), medico.getNombre());
    assertEquals(createdMedico.getApellido(), medico.getApellido());
    assertEquals(createdMedico.getRegistro(), medico.getRegistro());
  }

  @Test
  void getMedicos() {
    List<MedicoEntity> entityMedicos = medicoService.getMedicos();
    for(int i = 0; i < entityMedicos.size(); i++) {
      assertEquals(entityMedicos.get(i).getNombre(), medicos.get(i).getNombre());
      assertEquals(entityMedicos.get(i).getApellido(), medicos.get(i).getApellido());
      assertEquals(entityMedicos.get(i).getRegistro(), medicos.get(i).getRegistro());
    }
  }

  @Test
  void createEspecialidadWithInvalidadDescription() {
    EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
    especialidad.setDescripcion("menosde12");
    assertThrows(IllegalOperationException.class, () -> {
      especialidadService.createEspecialidad(especialidad);
    });
  }

  @Test
  void createEspecialidad() throws IllegalOperationException {
    EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
    especialidad.setNombre("especialidad");
    especialidad.setDescripcion("descripcion para");

    EspecialidadEntity createdEspecialidad = especialidadService.createEspecialidad(especialidad);

    assertEquals(createdEspecialidad.getNombre(), especialidad.getNombre());
    assertEquals(createdEspecialidad.getDescripcion(), especialidad.getDescripcion());
  }

  @Test
  void getEspecialidades() {
    List<EspecialidadEntity> especialidadesEntities = especialidadService.getAllEspecialidades();
    for(int i = 0; i < especialidadesEntities.size(); i++) {
      assertEquals(especialidades.get(i).getNombre(), especialidadesEntities.get(i).getNombre());
      assertEquals(especialidades.get(i).getDescripcion(), especialidadesEntities.get(i).getDescripcion());
    }
  }

  @Test
  void addEspecialidadWithInvalidMedico() {
    assertThrows(EntityNotFoundException.class, () -> {
      medicoEspecialidadService.addEspecialidad(-1L, 1L);
    });
  }

  @Test
  void addEspecialidadWithInvalidEspecialidad() {
    assertThrows(EntityNotFoundException.class, () -> {
      medicoEspecialidadService.addEspecialidad(medicos.getFirst().getId(), -1L);
    });
  }

  @Test
  void addEspecialidad() throws EntityNotFoundException, IllegalOperationException {
    MedicoEntity medico = medicos.getFirst();
    EspecialidadEntity especialidad = especialidades.getFirst();

    MedicoEntity medicoE = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());

    assertEquals(medicoE.getEspecialidades().getFirst().getNombre(), especialidad.getNombre());
    assertEquals(medicoE.getEspecialidades().getFirst().getDescripcion(), especialidad.getDescripcion());

    EspecialidadEntity especialidadE = entityManager.find(EspecialidadEntity.class, especialidad.getId());

    assertEquals(especialidadE.getMedicos().getFirst().getNombre(), medico.getNombre());
    assertEquals(especialidadE.getMedicos().getFirst().getApellido(), medico.getApellido());
    assertEquals(especialidadE.getMedicos().getFirst().getRegistro(), medico.getRegistro());
  }

  @Test
  void addEspecialidadWhereEspecialidadExists() throws EntityNotFoundException, IllegalOperationException {
    MedicoEntity medico = medicos.getFirst();
    EspecialidadEntity especialidad = especialidades.getFirst();

    MedicoEntity medicoE = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());

    assertThrows(IllegalOperationException.class, () -> {
      medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());
    });
  }
}
