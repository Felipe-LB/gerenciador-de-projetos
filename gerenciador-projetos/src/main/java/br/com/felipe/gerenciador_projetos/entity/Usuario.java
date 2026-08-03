package br.com.felipe.gerenciador_projetos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "perfil_id", unique = true)
    private Perfil perfil;

    @OneToMany(
            mappedBy = "proprietario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Projeto> projetosProprios = new ArrayList<>();

    @ManyToMany(mappedBy = "colaboradores")
    private Set<Projeto> projetosColaborados = new HashSet<>();

    public void addProjetoProprio(Projeto projeto) {
        projetosProprios.add(projeto);
        projeto.setProprietario(this);
    }

    public void removeProjetoProprio(Projeto projeto) {
        projetosProprios.remove(projeto);
        projeto.setProprietario(null);
    }
}
