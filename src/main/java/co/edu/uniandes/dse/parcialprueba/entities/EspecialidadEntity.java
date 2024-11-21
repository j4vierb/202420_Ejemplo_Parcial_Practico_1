package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class EspecialidadEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String nombre;
  private String descripcion;

  @ManyToMany() // no dueña de la relación
  // mappedBy = "especialidades"
  private List<MedicoEntity> medicos = new ArrayList<>();
}
