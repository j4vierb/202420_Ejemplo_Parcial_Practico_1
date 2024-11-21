package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MedicoService {
  @Autowired
  private MedicoRepository medicoRepository;

  @Transactional
  public MedicoEntity createMedico(MedicoEntity medico) throws IllegalOperationException {
    log.info("Creando un medico con el id {}", medico.getId());

    if(!medico.getNombre().startsWith("RM"))
      throw new IllegalOperationException("El nombre del medico no es valido, debe iniciar con RM");

    MedicoEntity createdMedico = medicoRepository.save(medico);
    log.info("Medico creado");

    return createdMedico;
  }

  @Transactional
  public List<MedicoEntity> getMedicos() {
    return medicoRepository.findAll();
  }
}
