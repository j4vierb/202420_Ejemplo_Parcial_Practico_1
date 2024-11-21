package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MedicoEspecialidadService {
  @Autowired
  MedicoRepository medicoRepository;

  @Autowired
  EspecialidadRepository especialidadRepository;

  @Transactional
  public MedicoEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException, IllegalOperationException {
    log.info("añadiendo la especialidad con id {} al medico con id {}", especialidadId, medicoId);

    Optional<MedicoEntity> medico = medicoRepository.findById(medicoId);
    if(medico.isEmpty())
      throw new EntityNotFoundException("El medico no se encontro");

    Optional<EspecialidadEntity> especialidad = especialidadRepository.findById(especialidadId);
    if(especialidad.isEmpty())
      throw new EntityNotFoundException("El especialidad no se encontro");

    MedicoEntity medicoEntity = medico.get();
    EspecialidadEntity especialidadEntity = especialidad.get();

    if(medicoEntity.getEspecialidades().contains(especialidadEntity))
      throw new IllegalOperationException("La especialidad ya se encuentra");

    medicoEntity.getEspecialidades().add(especialidadEntity);
    especialidadEntity.getMedicos().add(medicoEntity);

    log.info("se añadio la especialidad al medico");
    return medicoEntity;
  }

  @Transactional
  public List<EspecialidadEntity> getAllEspecialidades(Long medicoId) throws IllegalOperationException {
    if(medicoRepository.findById(medicoId).isEmpty())
      throw new IllegalOperationException("el medico no existe");

    return medicoRepository.findById(medicoId).get().getEspecialidades();
  }
}
