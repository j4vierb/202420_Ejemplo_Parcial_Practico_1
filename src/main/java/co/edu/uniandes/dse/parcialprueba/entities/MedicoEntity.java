package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MedicoEntity {
  @Id
  @PodamExclude
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String nombre;
  private String apellido;
  private List<String> registro = new ArrayList<>();

  @PodamExclude
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "medico_especialidad",
    joinColumns = @JoinColumn(name = "id_medico"),
    inverseJoinColumns = @JoinColumn(name = "id_especialidad")
  ) // dueña de la relación
  private List<EspecialidadEntity> especialidades = new ArrayList<>();
}
