package br.com.fiap3espf.spring_boot_project.instrutor;

import br.com.fiap3espf.spring_boot_project.endereco.DadosEndereco;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoInstrutor(
        @NotBlank String nome,
        String telefone,
        DadosEndereco endereco
) { }
