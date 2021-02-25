package br.com.objective.model;

/**
 * Enum para identificação do tipo de enunciado exibido para o usuário.
 * @author Jonny
 */
public enum TipoEnunciado {
    INICIO("Início do jogo"),
    PERGUNTA_PADRAO("Pergunta padrão"),
    PERGUNTA_ADICIONADA("Pergunta adicionada durante o jogo"),
    SOLICITAR_NOME_PRATO("Solicitação do nome do prato pensado (desistência)"),
    SOLICITAR_TIPO_PRATO("Solicitação do tipo de prato do prato pensado (desistência)"),
    ACERTO("Acerto do nome do prato");
    
    private final String descricao;

    private TipoEnunciado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
