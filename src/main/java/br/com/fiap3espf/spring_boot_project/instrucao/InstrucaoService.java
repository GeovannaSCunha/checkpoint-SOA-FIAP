package br.com.fiap3espf.spring_boot_project.instrucao;

import br.com.fiap3espf.spring_boot_project.aluno.AlunoRepository;
import br.com.fiap3espf.spring_boot_project.instrutor.Instrutor;
import br.com.fiap3espf.spring_boot_project.instrutor.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InstrucaoService {

    private final InstrucaoRepository instrucaoRepo;
    private final AlunoRepository alunoRepo;
    private final InstrutorRepository instrutorRepo;

    private static final LocalTime OPEN = LocalTime.of(6, 0);
    private static final LocalTime CLOSE = LocalTime.of(21, 0);
    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    public DetalheInstrucaoDTO agendar(AgendarInstrucaoDTO dto) {
        var inicio = dto.inicio();

        var dow = inicio.getDayOfWeek();
        if (dow == DayOfWeek.SUNDAY) throw new IllegalArgumentException("Funcionamos de segunda a sábado.");
        if (inicio.toLocalTime().isBefore(OPEN) || !inicio.toLocalTime().isBefore(CLOSE))
            throw new IllegalArgumentException("Horário permitido: 06:00 até 21:00 (duração 1h).");
        if (inicio.isBefore(LocalDateTime.now(ZONE).plusMinutes(30)))
            throw new IllegalArgumentException("Agende com pelo menos 30 minutos de antecedência.");

        var aluno = alunoRepo.findById(dto.alunoId()).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        if (!aluno.isAtivo()) throw new IllegalStateException("Aluno inativo não pode agendar.");

        Instrutor instrutor;
        if (dto.instrutorId() != null) {
            instrutor = instrutorRepo.findById(dto.instrutorId()).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
            if (!instrutor.isAtivo()) throw new IllegalStateException("Instrutor inativo.");
            if (instrucaoRepo.existsByInstrutor_IdAndInicioAndStatus(instrutor.getId(), inicio, StatusInstrucao.AGENDADA))
                throw new IllegalStateException("Instrutor já possui instrução nesse horário.");
        } else {
            var disponiveis = instrutorRepo.findDisponiveis(inicio);
            if (disponiveis.isEmpty()) throw new IllegalStateException("Nenhum instrutor disponível no horário.");
            Collections.shuffle(disponiveis);
            instrutor = disponiveis.get(0);
        }

        var qtdDia = instrucaoRepo.countAgendadasNoDia(aluno.getId(), inicio.toLocalDate());
        if (qtdDia >= 2) throw new IllegalStateException("Aluno já possui 2 instruções neste dia.");

        var ins = new Instrucao();
        ins.setAluno(aluno);
        ins.setInstrutor(instrutor);
        ins.setInicio(inicio);
        ins.setStatus(StatusInstrucao.AGENDADA);

        var saved = instrucaoRepo.save(ins);
        return new DetalheInstrucaoDTO(saved.getId(), aluno.getId(), instrutor.getId(), saved.getInicio(), saved.getStatus());
    }

    public void cancelar(CancelarInstrucaoDTO dto) {
        var instr = instrucaoRepo.findById(dto.instrucaoId()).orElseThrow(EntityNotFoundException::new);
        if (instr.getStatus() == StatusInstrucao.CANCELADA) return;

        if (instr.getInicio().isBefore(LocalDateTime.now(ZONE).plusHours(24)))
            throw new IllegalStateException("Cancelamento só é permitido com 24h de antecedência.");

        if (dto.motivo() == null) throw new IllegalArgumentException("Motivo é obrigatório.");
        instr.cancelar(dto.motivo(), LocalDateTime.now(ZONE));
        instrucaoRepo.save(instr);
    }
}
