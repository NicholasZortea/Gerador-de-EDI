package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.entities.Produto;
import model.entities.Nota;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.entities.Endereco;
import model.entities.Individuo;

public class Model {

    private Nota nota;
    private Endereco endereco;
    private Individuo individuo;
    private ArrayList<Produto> produtos = new ArrayList<>();

    public Model(Nota nota, Endereco endereco, Individuo individuo) {
        this.nota = nota;
        this.endereco = endereco;
        this.individuo = individuo;
    }

    public void addProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public String getEDI() {
        StringBuilder EDI = new StringBuilder();
        EDI.append("ST*")
                .append("850*")
                .append(nota.getCodigoNota());

        EDI.append("\nBG*")
                .append(nota.getNumeroNota())
                .append("*")
                .append(nota.getData());

        EDI.append("\nN1*BRG SISTEMAS*RUA DOS ALFENEIROS 4. BAIRRO CAMOBI*SANTA MARIA "
                + "- RS*54999999999*000000000-38");
        EDI.append("\nN2*")
                .append(individuo.getNome())
                .append("*")
                .append(endereco.getEndereco())
                .append("*")
                .append(endereco.getCidade())
                .append("*")
                .append(individuo.getTelefone())
                .append("*")
                .append(individuo.getCpfCnpj());

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            EDI.append("\nPO").append(i + 1).append("*")
                    .append(p.getQuantidade())
                    .append("*")
                    .append(p.getDescricao())
                    .append("*")
                    .append(p.getValorUnitario())
                    .append("*")
                    .append(p.getValorTotal());
        }

        double valorTotal = 0;
        double qntdTotal = 0;
        for (Produto p : produtos) {
            qntdTotal += Double.parseDouble(p.getQuantidade());
            valorTotal += Double.parseDouble(p.getValorTotal());
        }

        EDI.append("\nCTT")
                .append("*")
                .append(qntdTotal)
                .append("*")
                .append(valorTotal);

        EDI.append("\nSE")
                .append("*")
                .append(nota.getNumeroNota());

        return EDI.toString();
    }

    public void printProdutos() {
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    public void salvarEDI() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o diretório para salvar o arquivo");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            String filePath = selectedDirectory.getAbsolutePath() + "/EDI.txt";
            salvarArquivo(filePath);
        }
    }

    private void salvarArquivo(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(getEDI());
            writer.close();
            System.out.println("Arquivo salvo em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
