package tn.esprit.elharizi.domain;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
//@fieldDefault(level = AccessLevel.Private) : iptimisation bch tokoodch kol mara tkoul eli howa private

public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    @Enumerated(EnumType.STRING)
    private StatutVehicule statutVehicule;
    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorieVehicule;
    private BigDecimal tarifJournal;
    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;
    @ManyToOne
    private Agence agence;
    @ManyToMany
    Set<Equipement> equipements = new HashSet<>();
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.PERSIST)
    private List<Maintenance> maintenances = new ArrayList<>();

    public Vehicule orElseThrow(Object o) {
        return null;
    }
}
