package br.com.objective.application.setup;

import br.com.objective.model.Enunciado;
import br.com.objective.model.Prato;
import br.com.objective.model.TipoEnunciado;
import br.com.objective.model.TipoPrato;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jonny
 */
public class Init implements Serializable {

    public static List<Enunciado> getDefaultEnunciados() {
        List enunciados = new ArrayList(6);
        enunciados.addAll(Arrays.asList(
                Enunciado.builder().tipoEnunciado(TipoEnunciado.INICIO).build(),
                Enunciado.builder().tipoEnunciado(TipoEnunciado.SOLICITAR_NOME_PRATO).build(),
                Enunciado.builder().tipoEnunciado(TipoEnunciado.SOLICITAR_TIPO_PRATO).build(),
                Enunciado.builder().tipoEnunciado(TipoEnunciado.ACERTO).build(),
                Enunciado.builder().tipoEnunciado(TipoEnunciado.PERGUNTA_PADRAO)
                        .tipoPrato(getDefaultTipoPrato()).build(),
                Enunciado.builder().tipoEnunciado(TipoEnunciado.PERGUNTA_PADRAO)
                        .prato(getDefaultPrato()).build()
        ));
        return enunciados;
    }

    private static TipoPrato getDefaultTipoPrato() {
        TipoPrato tipoMassa = TipoPrato.builder()
                .nome("Massa")
                .ordem(1)
                .build();
        tipoMassa.addPrato(
                Prato.builder()
                        .nome("Lasanha")
                        .tipoPrato(tipoMassa).build());
        return tipoMassa;
    }

    private static Prato getDefaultPrato() {
        return Prato.builder()
                .nome("Bolo de chocolate")
                .ordem(1).build();
    }

}
