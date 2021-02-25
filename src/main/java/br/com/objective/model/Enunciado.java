package br.com.objective.model;

import br.com.objective.application.setup.Constants;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe modelo para manutenção das informações exibidas para o usuário.
 *
 * @author Jonny
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="texto")
public class Enunciado implements Serializable {

    private String texto;
    private TipoPrato tipoPrato;
    private Prato prato;
    private TipoEnunciado tipoEnunciado;

    public String getTexto() {
        switch (tipoEnunciado) {
            case INICIO:
                texto = Constants.INITIAL_STATEMENT;
                break;
            case PERGUNTA_PADRAO:
            case PERGUNTA_ADICIONADA:
                texto = Constants.DEFAULT_ASK
                        + (tipoPrato != null
                                ? tipoPrato.getNome()
                                : prato.getNome()) + "?";
                break;
            case SOLICITAR_NOME_PRATO:
                texto = Constants.GIVE_UP_MSG;
                break;
            case SOLICITAR_TIPO_PRATO:
                texto = Constants.COMPLETE_MSG;
                break;
            case ACERTO:
                texto = Constants.I_GOT_RIGHT_AGAIN;
                break;
            default:
                texto = Constants.BLANK_STRING;
        }
        return texto;
    }
}
