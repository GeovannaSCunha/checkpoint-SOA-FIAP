package br.com.fiap3espf.spring_boot_project.instrutor;

public record DadosListagemInstrutor(
        String nome,
        String email,
        String chn,
        Especialidade especialidade) {

    public DadosListagemInstrutor(Instrutor dados) {
        this(dados.getNome(), dados.getEmail(), dados.getCnh(), dados.getEspecialidade());
    }
}
