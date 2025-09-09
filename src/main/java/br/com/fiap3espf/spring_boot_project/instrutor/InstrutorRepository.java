package br.com.fiap3espf.spring_boot_project.instrutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Page<Instrutor> findByAtivoTrueOrderByNomeAsc(Pageable pageable);

    // para escolher instrutor disponível num horário
    @Query("""
        select i from Instrutor i
        where i.ativo = true
          and i.id not in (
            select ins.instrutor.id
            from br.com.fiap3espf.spring_boot_project.instrucao.Instrucao ins
            where ins.inicio = :inicio
              and ins.status = br.com.fiap3espf.spring_boot_project.instrucao.StatusInstrucao.AGENDADA
          )
        """)
    List<Instrutor> findDisponiveis(LocalDateTime inicio);
}
