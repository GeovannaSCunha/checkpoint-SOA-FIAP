package br.com.fiap3espf.spring_boot_project.instrucao;

import java.time.LocalDateTime;

public record AgendarInstrucaoDTO(
        Long alunoId,
        Long instrutorId, // se null -> escolher disponível
        LocalDateTime inicio
) { }
