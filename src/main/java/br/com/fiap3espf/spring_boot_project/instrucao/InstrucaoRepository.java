package br.com.fiap3espf.spring_boot_project.instrucao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface InstrucaoRepository extends JpaRepository<Instrucao, Long> {

    boolean existsByInstrutor_IdAndInicioAndStatus(Long instrutorId, LocalDateTime inicio, StatusInstrucao status);

    @Query("""
        select count(i) from Instrucao i
        where i.aluno.id = :alunoId
          and date(i.inicio) = :data
          and i.status = br.com.fiap3espf.spring_boot_project.instrucao.StatusInstrucao.AGENDADA
        """)
    long countAgendadasNoDia(Long alunoId, LocalDate data);
}
