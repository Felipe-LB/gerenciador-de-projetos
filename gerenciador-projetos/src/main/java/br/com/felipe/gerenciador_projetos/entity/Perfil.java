package br.com.felipe.gerenciador_projetos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfis")
@Getter @Setter
@NoArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String biografia;

    @Column(name = "url_foto")
    private String urlFoto;
}
