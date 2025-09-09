package br.com.fiap3espf.spring_boot_project.aluno;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;

    public Long cadastrar(@Valid DadosCadastroAluno dados) {
        var a = repository.save(new Aluno(dados));
        return a.getId();
    }

    public Page<DadosListagemAluno> listar(int page) {
        var pageable = PageRequest.of(page, 10, Sort.by("nome").ascending());
        return repository.findByAtivoTrueOrderByNomeAsc(pageable).map(DadosListagemAluno::new);
    }

    public void atualizar(Long id, @Valid DadosAtualizacaoAluno dados) {
        var a = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        a.atualizar(dados); // não altera email/cpf
        repository.save(a);
    }

    public void inativar(Long id) {
        var a = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        a.inativar();
        repository.save(a);
    }
}
