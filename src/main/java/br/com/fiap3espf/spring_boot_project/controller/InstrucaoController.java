package br.com.fiap3espf.spring_boot_project.controller;

import br.com.fiap3espf.spring_boot_project.instrucao.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrucoes")
@RequiredArgsConstructor
public class InstrucaoController {

    private final InstrucaoService service;

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity<DetalheInstrucaoDTO> agendar(@RequestBody @Valid AgendarInstrucaoDTO dto){
        return ResponseEntity.ok(service.agendar(dto));
    }

    @PostMapping("/cancelar")
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid CancelarInstrucaoDTO dto){
        service.cancelar(dto);
        return ResponseEntity.noContent().build();
    }
}
