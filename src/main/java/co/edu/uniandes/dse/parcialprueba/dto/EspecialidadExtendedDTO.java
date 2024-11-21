package co.edu.uniandes.dse.parcialprueba.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EspecialidadExtendedDTO extends EspecialidadDTO {
  private List<MedicoDTO> medicos = new ArrayList<>();
}
