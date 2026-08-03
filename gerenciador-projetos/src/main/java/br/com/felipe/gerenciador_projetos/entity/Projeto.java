package br.com.felipe.gerenciador_projetos.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projetos")
@Getter
@Setter
@NoArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Usuario proprietario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "projeto_colaboradores",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> colaboradores = new HashSet<>();

    public void addColaborador(Usuario colaborador) {
        colaboradores.add(colaborador);
        colaborador.getProjetosColaborados().add(this);
    }

    public void removeColaborador(Usuario colaborador) {
        colaboradores.remove(colaborador);
        colaborador.getProjetosColaborados().remove(this);
    }
}
