package br.com.fiap3espf.spring_boot_project.aluno;

import br.com.fiap3espf.spring_boot_project.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "alunos")
@Entity(name = "Aluno")
@Getter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    private boolean ativo = true;

    public Aluno(DadosCadastroAluno dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    public void atualizar(DadosAtualizacaoAluno dados) {
        if (dados.nome() != null && !dados.nome().isBlank()) this.nome = dados.nome();
        if (dados.telefone() != null && !dados.telefone().isBlank()) this.telefone = dados.telefone();
        if (dados.endereco() != null) this.endereco.atualizar(dados.endereco());
    }

    public void inativar() { this.ativo = false; }
}
