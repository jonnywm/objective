package br.com.objective.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe modelo para manutenção dos pratos exibidos para o usuário.
 *
 * @author Jonny
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"nome", "tipoPrato"})
public class Prato implements Serializable {

    private String nome;
    private Integer ordem;
    private TipoPrato tipoPrato;
}
