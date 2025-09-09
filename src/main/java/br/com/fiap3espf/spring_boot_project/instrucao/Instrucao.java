package br.com.fiap3espf.spring_boot_project.instrucao;

import br.com.fiap3espf.spring_boot_project.aluno.Aluno;
import br.com.fiap3espf.spring_boot_project.instrutor.Instrutor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(
        name = "instrucoes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_instrutor_inicio",
                columnNames = {"instrutor_id", "inicio"}
        )
)
@Entity(name = "Instrucao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Instrucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    @Column(nullable = false)
    private LocalDateTime inicio; // duração fixa de 1h

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInstrucao status = StatusInstrucao.AGENDADA;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivo;

    private LocalDateTime canceladaEm;

    public void cancelar(MotivoCancelamento m, LocalDateTime quando) {
        this.status = StatusInstrucao.CANCELADA;
        this.motivo = m;
        this.canceladaEm = quando;
    }
}
