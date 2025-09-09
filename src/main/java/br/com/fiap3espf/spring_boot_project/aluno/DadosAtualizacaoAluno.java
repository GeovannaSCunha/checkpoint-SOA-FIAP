package br.com.fiap3espf.spring_boot_project.aluno;

import br.com.fiap3espf.spring_boot_project.endereco.DadosEndereco;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoAluno(
        @NotBlank String nome,
        String telefone,
        DadosEndereco endereco
) { }
