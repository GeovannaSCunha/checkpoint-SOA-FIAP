package br.com.fiap3espf.spring_boot_project.instrutor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrutorService {
    private final InstrutorRepository repository;

    public Long cadastrar(@Valid DadosCadastroInstrutor dados) {
        var i = repository.save(new Instrutor(dados));
        return i.getId();
    }

    public Page<DadosListagemInstrutor> listar(int page) {
        var pageable = PageRequest.of(page, 10, Sort.by("nome").ascending());
        return repository.findByAtivoTrueOrderByNomeAsc(pageable).map(DadosListagemInstrutor::new);
    }

    public void atualizar(Long id, @Valid DadosAtualizacaoInstrutor dados) {
        var i = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        i.atualizar(dados);
        repository.save(i);
    }

    public void inativar(Long id) {
        var i = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        i.inativar();
        repository.save(i);
    }
}
