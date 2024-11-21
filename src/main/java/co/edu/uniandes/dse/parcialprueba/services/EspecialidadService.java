package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EspecialidadService {
  @Autowired
  private EspecialidadRepository especialidadRepository;

  public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) {
    log.info("Creando especialidad con id {}", especialidad.getId());

    if(especialidad.getDescripcion().length() < 10)
      throw new IllegalArgumentException("La descripcion no puede ser menor que 10 caracteres");

    EspecialidadEntity createdEspecialidad = especialidadRepository.save(especialidad);
    log.info("Especialidad creada con exito");

    return createdEspecialidad;
  }

  public List<EspecialidadEntity> getAllEspecialidades() {
    return especialidadRepository.findAll();
  }
}
