package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class EspecialidadEntity {
  @Id
  @PodamExclude
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String nombre;
  private String descripcion;

  @PodamExclude
  @ManyToMany() // no dueña de la relación
  // mappedBy = "especialidades"
  private List<MedicoEntity> medicos = new ArrayList<>();
}
