package br.com.objective.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe modelo para manutenção dos tipos de patros exibidos para o usuário.
 *
 * @author jonny
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nome")
public class TipoPrato implements Serializable {

    private String nome;
    private Integer ordem;
    private List<Prato> pratos;

    public void addPrato(Prato prato) {
        if (Objects.isNull(pratos)) {
            pratos = new ArrayList<>();
        }
        if (!pratos.contains(prato)) {
            Integer proximoNaOrdem = pratos.stream().mapToInt(e -> e.getOrdem()).max().orElse(0);
            proximoNaOrdem++;
            prato.setOrdem(proximoNaOrdem);
            pratos.add(prato);
        }
    }

}
