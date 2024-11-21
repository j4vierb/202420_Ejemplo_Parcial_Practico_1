package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MedicoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String nombre;
  private String apellido;
  private List<String> registro = new ArrayList<>();

  @ManyToMany
  @JoinTable(
    name = "medico_especialidad",
    joinColumns = @JoinColumn(name = "id_medico"),
    inverseJoinColumns = @JoinColumn(name = "id_especialidad")
  ) // dueña de la relación
  private List<EspecialidadEntity> especialidades = new ArrayList<>();
}
