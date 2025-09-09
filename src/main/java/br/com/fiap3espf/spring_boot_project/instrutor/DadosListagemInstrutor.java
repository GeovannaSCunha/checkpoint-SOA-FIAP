package br.com.fiap3espf.spring_boot_project.instrutor;

public record DadosListagemInstrutor(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade
) {
    public DadosListagemInstrutor(Instrutor i) {
        this(i.getId(), i.getNome(), i.getEmail(), i.getCnh(), i.getEspecialidade());
    }
}
