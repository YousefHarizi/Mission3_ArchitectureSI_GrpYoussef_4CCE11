package tn.esprit.elharizi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity


public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaintenance ;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    @ManyToOne
    Vehicule vehicule;


}
