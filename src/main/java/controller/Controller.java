package controller;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.entities.Endereco;
import model.entities.Individuo;
import model.entities.Nota;
import model.entities.Produto;
import view.View;

public class Controller {

    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void gerarEDI() {
        Endereco endereco = new Endereco(view.getRua().getText(),
                view.getNumero().getText(),
                view.getBairro().getText(),
                view.getCidade().getText());

        Individuo individuo = new Individuo(view.getNome().getText(),
                view.getTelefone().getText(),
                view.getCpfCnpj().getText());

        Nota nota = new Nota(view.getNota().getText(),
                (String) view.getNotaTipo().getSelectedItem(),
                view.getData().getText());

        Model novoModel = new Model(nota, endereco, individuo);
        addProdutosAoModel(novoModel, view.getTableModel());
        novoModel.salvarEDI();
        view.mostrarEDI(novoModel.getEDI());
    }

    private void addProdutosAoModel(Model model, DefaultTableModel tableModel) {
        int rows = tableModel.getRowCount();

        for (int r = 0; r < rows; r++) {
            Produto novoProduto;
            try {
                novoProduto = new Produto((String) tableModel.getValueAt(r, 0),
                        (String) tableModel.getValueAt(r, 1),
                        (String) tableModel.getValueAt(r, 2),
                        String.valueOf(tableModel.getValueAt(r, 3)));
                model.addProduto(novoProduto);
            } catch(NullPointerException ex){
                JOptionPane.showMessageDialog(view, "Célula não preenchida!" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
