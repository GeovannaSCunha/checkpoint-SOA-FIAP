package br.com.fiap3espf.spring_boot_project.controller;

import br.com.fiap3espf.spring_boot_project.aluno.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @PostMapping
    @Transactional
    public Long cadastrar(@RequestBody @Valid DadosCadastroAluno dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosListagemAluno> listar(@RequestParam(defaultValue = "0") int page) {
        return service.listar(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoAluno dados) {
        service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }
}
