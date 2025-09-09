package br.com.fiap3espf.spring_boot_project.aluno;

import br.com.fiap3espf.spring_boot_project.endereco.DadosEndereco;
import jakarta.validation.constraints.*;

public record DadosCadastroAluno(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String cpf,
        @NotNull DadosEndereco endereco
) { }
