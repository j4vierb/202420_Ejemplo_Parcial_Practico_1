package co.edu.uniandes.dse.parcialprueba.controllers;

import co.edu.uniandes.dse.parcialprueba.dto.EspecialidadDTO;
import co.edu.uniandes.dse.parcialprueba.dto.EspecialidadExtendedDTO;
import co.edu.uniandes.dse.parcialprueba.dto.MedicoDTO;
import co.edu.uniandes.dse.parcialprueba.dto.MedicoExtendedDTO;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.services.EspecialidadService;
import co.edu.uniandes.dse.parcialprueba.services.MedicoEspecialidadService;
import co.edu.uniandes.dse.parcialprueba.services.MedicoService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SuperController {
  @Autowired
  MedicoService medicoService;
  @Autowired
  EspecialidadService especialidadService;
  @Autowired
  MedicoEspecialidadService medicoEspecialidadService;
  @Autowired
  ModelMapper modelMapper;

  @GetMapping("/especialidades/")
  public List<EspecialidadExtendedDTO> getEspecialidades() {
    return modelMapper.map(especialidadService.getAllEspecialidades(), new TypeToken<List<EspecialidadExtendedDTO>>() {}.getType());
  }

  @GetMapping("/medicos/")
  public List<MedicoExtendedDTO> getMedicos() {
    return modelMapper.map(medicoService.getMedicos(), new TypeToken<List<MedicoExtendedDTO>>() {}.getType());
  }

  @PostMapping("/medicos/")
  public MedicoExtendedDTO create(@RequestBody MedicoDTO medicoDTO) throws IllegalOperationException {
    MedicoEntity medico = modelMapper.map(medicoDTO, MedicoEntity.class);
    MedicoEntity medicoEntity = medicoService.createMedico(medico);

    return modelMapper.map(medicoEntity, MedicoExtendedDTO.class);
  }

  @PostMapping("/especialidades/")
  public EspecialidadExtendedDTO create(@RequestBody EspecialidadDTO especialidadDTO) throws IllegalOperationException {
    EspecialidadEntity especialidad = modelMapper.map(especialidadDTO, EspecialidadEntity.class);
    EspecialidadEntity especialidadEntity = especialidadService.createEspecialidad(especialidad);
    return modelMapper.map(especialidadEntity, EspecialidadExtendedDTO.class);
  }

  @PostMapping("/medico/{medicoId}/especialidades/{especialidadId}")
  public MedicoExtendedDTO addEspecialidad(@PathVariable Long medicoId, @PathVariable Long especialidadId) throws IllegalOperationException, EntityNotFoundException {
    MedicoEntity medico = medicoEspecialidadService.addEspecialidad(medicoId, especialidadId);

    return modelMapper.map(medico, MedicoExtendedDTO.class);
  }
}
