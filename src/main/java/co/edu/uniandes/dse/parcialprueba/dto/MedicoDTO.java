package co.edu.uniandes.dse.parcialprueba.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicoDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private List<String> registro = new ArrayList<>();
}
