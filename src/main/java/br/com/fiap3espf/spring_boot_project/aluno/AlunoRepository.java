package br.com.fiap3espf.spring_boot_project.aluno;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findByAtivoTrueOrderByNomeAsc(Pageable pageable);
}
