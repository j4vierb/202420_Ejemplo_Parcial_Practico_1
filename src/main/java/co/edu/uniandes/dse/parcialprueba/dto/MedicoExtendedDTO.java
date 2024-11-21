package co.edu.uniandes.dse.parcialprueba.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicoExtendedDTO extends MedicoDTO {
  private List<EspecialidadDTO> especialidades = new ArrayList<>();
}
