package br.com.objective.application;

import br.com.objective.application.setup.Constants;
import br.com.objective.application.setup.Init;
import br.com.objective.model.Enunciado;
import br.com.objective.model.Prato;
import br.com.objective.model.TipoEnunciado;
import br.com.objective.model.TipoPrato;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jonny
 */
public class Main {

    private static final JFrame FRAME = new JFrame();
    private static Enunciado INICIO;
    private static Enunciado SOLICITAR_NOME_PRATO;
    private static Enunciado SOLICITAR_TIPO_PRATO;
    private static Enunciado ACERTO;
    private static Enunciado ENUNCIADO_ULTIMO_PRATO;
    private static boolean tipoAcertado;
    private static boolean pratoAcertado;
    private static boolean continuarPorTipo = true;
    private static Icon ICON;

    public static void main(String[] args) {
        List<Enunciado> enunciados = Init.getDefaultEnunciados();
        INICIO = enunciados.stream().filter(e -> e.getTipoEnunciado().equals(TipoEnunciado.INICIO)).findFirst().get();
        SOLICITAR_NOME_PRATO = enunciados.stream().filter(e -> e.getTipoEnunciado().equals(TipoEnunciado.SOLICITAR_NOME_PRATO)).findFirst().get();
        SOLICITAR_TIPO_PRATO = enunciados.stream().filter(e -> e.getTipoEnunciado().equals(TipoEnunciado.SOLICITAR_TIPO_PRATO)).findFirst().get();
        ACERTO = enunciados.stream().filter(e -> e.getTipoEnunciado().equals(TipoEnunciado.ACERTO)).findFirst().get();
        ENUNCIADO_ULTIMO_PRATO = enunciados.stream()
                .filter(e -> e.getTipoEnunciado().equals(TipoEnunciado.PERGUNTA_PADRAO))
                .filter(e1 -> e1.getPrato() != null).findFirst().get();
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        while (true) {
            iniciarPerguntas(enunciados);
        }
    }

    public static void iniciarPerguntas(List<Enunciado> enunciados) {

        Integer resp = JOptionPane.showConfirmDialog(FRAME, INICIO.getTexto(), Constants.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resp == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        continuarPorTipo = true;
        tipoAcertado = false;
        pratoAcertado = false;

        //Filtrando e ordenando os enunciados apenas com tipo de prato.
        Stream<Enunciado> streamTipoPrato = enunciados.stream()
                .filter(e -> e.getTipoPrato() != null)
                .sorted(Comparator
                        .comparing(Enunciado::getTipoPrato,
                                (tipoPrato1, tipoPrato2) -> {
                                    return tipoPrato2.getOrdem()
                                            .compareTo(tipoPrato1.getOrdem());
                                }
                        ));

        //Iterando sobre os tipos de prato
        streamTipoPrato.forEach(enunciadoTipoPrato -> {
            if (continuarPorTipo) {
                Integer resposta = JOptionPane.showConfirmDialog(FRAME, enunciadoTipoPrato.getTexto(), Constants.CONFIRM_TITTLE, JOptionPane.YES_NO_OPTION);
                switch (resposta) {
                    case JOptionPane.YES_OPTION:
                        continuarPorTipo = false;
                        tipoAcertado = true;

                        //Iterando sobre os pratos do tipo selecionado
                        enunciadoTipoPrato.getTipoPrato().getPratos()
                                .stream()
                                .sorted(Comparator.comparing(Prato::getOrdem, Comparator.reverseOrder()))
                                .forEachOrdered(prato -> {
                                    if (!pratoAcertado) {
                                        Integer resposta2 = JOptionPane.showConfirmDialog(FRAME, Constants.DEFAULT_ASK + prato.getNome() + "?",
                                                Constants.CONFIRM_TITTLE, JOptionPane.YES_NO_OPTION);
                                        switch (resposta2) {
                                            case JOptionPane.YES_OPTION:
                                                //Prato acertado
                                                JOptionPane.showConfirmDialog(FRAME, ACERTO.getTexto(), Constants.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                                                pratoAcertado = true;
                                                break;
                                            case JOptionPane.CLOSED_OPTION:
                                                System.exit(0);
                                                break;
                                        }
                                    }
                                });
                        if (!pratoAcertado) {
                            adicionarEnunciados(enunciados, enunciadoTipoPrato);
                        }
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                }
            }

        });

        if (!tipoAcertado) {
            Integer resposta = JOptionPane.showConfirmDialog(FRAME, ENUNCIADO_ULTIMO_PRATO.getTexto(), Constants.CONFIRM_TITTLE, JOptionPane.YES_NO_OPTION);
            switch (resposta) {
                case JOptionPane.YES_OPTION:
                    JOptionPane.showConfirmDialog(FRAME, ACERTO.getTexto(), Constants.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    break;
                case JOptionPane.NO_OPTION:
                    adicionarEnunciados(enunciados, null);
                    break;
                case JOptionPane.CLOSED_OPTION:
                    System.exit(0);
                    break;
            }
        }

    }

    public static void adicionarEnunciados(List<Enunciado> enunciados, Enunciado enunciadoTipoPrato) {
        //Recebendo prato pensado
        String nomePrato = JOptionPane.showInputDialog(FRAME, SOLICITAR_NOME_PRATO.getTexto(), Constants.GIVE_UP_TITTLE, JOptionPane.QUESTION_MESSAGE);
        if (Objects.nonNull(nomePrato) && !nomePrato.trim().isEmpty()) {
            //Recebendo tipo do prato pensado se o tipo já não tiver sido acertado
            if (Objects.isNull(enunciadoTipoPrato)) {
                String nomeTipoPrato = JOptionPane.showInputDialog(FRAME, nomePrato + SOLICITAR_TIPO_PRATO.getTexto(), Constants.COMPLETE_TITTLE, JOptionPane.QUESTION_MESSAGE);
                if (Objects.nonNull(nomeTipoPrato) && !nomeTipoPrato.trim().isEmpty()) {
                    //Início: Criando novo enunciado para o tipo de prato e adicionando o prato pensado.
                    Integer proximoNaOrdem = enunciados.stream().filter(e1 -> e1.getTipoPrato() != null).mapToInt(e -> e.getTipoPrato().getOrdem()).max().getAsInt();
                    enunciadoTipoPrato = enunciados.stream()
                            .filter(e1 -> e1.getTipoPrato() != null)
                            .filter(enunciado -> enunciado.getTipoPrato().getNome().equalsIgnoreCase(nomeTipoPrato.trim()))
                            .findFirst()
                            .orElse(Enunciado.builder()
                                    .tipoEnunciado(TipoEnunciado.PERGUNTA_ADICIONADA)
                                    .tipoPrato(TipoPrato.builder()
                                            .nome(nomeTipoPrato.trim())
                                            .ordem(proximoNaOrdem)
                                            .build())
                                    .build());

                }
            }
            //Fim: Criando novo enunciado para o tipo de prato

            //Adicionando o prato pensado.
            enunciadoTipoPrato.getTipoPrato()
                    .addPrato(Prato.builder()
                            .nome(nomePrato.trim())
                            .tipoPrato(enunciadoTipoPrato.getTipoPrato())
                            .build());
            if (!enunciados.contains(enunciadoTipoPrato)) {
                enunciados.add(enunciadoTipoPrato);
            }

        } else if (Objects.isNull(nomePrato)) {
            System.exit(0);
        }
    }
}
