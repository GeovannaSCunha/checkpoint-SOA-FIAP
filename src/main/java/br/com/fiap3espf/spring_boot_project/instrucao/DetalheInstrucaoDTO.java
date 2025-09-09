package br.com.fiap3espf.spring_boot_project.instrucao;

import java.time.LocalDateTime;

public record DetalheInstrucaoDTO(
        Long id, Long alunoId, Long instrutorId, LocalDateTime inicio, StatusInstrucao status
) { }
