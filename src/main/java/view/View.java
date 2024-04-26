package view;

import controller.Controller;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame {

    private JTextField nota;
    private JComboBox notaTipo;
    private JTextField data;
    private JTextField nome;
    private JTextField telefone;
    private JTextField CpfCnpj;
    private JTextField rua;
    private JTextField bairro;
    private JTextField numero;
    private JTextField cidade;
    private DefaultTableModel tableModel;

    public View() {
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);
        setTitle("Gerador de EDI");
        nota = new JTextField();
        notaTipo = new JComboBox(new String[]{"Nota de compra", "Nota de venda"});
        data = new JTextField();
        nome = new JTextField();
        telefone = new JTextField();
        CpfCnpj = new JTextField();
        rua = new JTextField();
        bairro = new JTextField();
        numero = new JTextField();
        cidade = new JTextField();
        tableModel = new DefaultTableModel(new String[]{"Descricao", "Quantidade", "Valor Unitario", "Valor Total"}, 4);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 1 || e.getColumn() == 2) {
                    String valor1 = (String) tableModel.getValueAt(e.getLastRow(), 1);
                    String valor2 = (String) tableModel.getValueAt(e.getLastRow(), 2);
                    if (valor1 != null && valor2 != null) {
                        try {
                            double valorTotal = Double.parseDouble(valor1) * Double.parseDouble(valor2);
                            tableModel.setValueAt(valorTotal, e.getLastRow(), 3);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Célula preenchida com valor errado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(5, 5, 5, 5);
        add(getNotaPanel(), gc);

        gc.gridy++;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(getInformacoesPanel(), gc);

        gc.gridy++;
        add(getTablePanel(), gc);
        
        gc.gridy++;
        JButton gerarEDI = new JButton("Gerar EDI");
        gerarEDI.addActionListener((ActionEvent e) -> {
            Controller controller = new Controller(this);
            controller.gerarEDI();
        });
        add(gerarEDI, gc);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel getCampo(String nome, int tamanho, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel(nome));
        field.setColumns(tamanho);
        panel.add(field);
        return panel;
    }

    private JPanel getNotaPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Informações da nota"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(5, 5, 5, 5);

        panel.add(getCampo("Numero da nota: ", 7, nota), gc);

        gc.gridx++;
        panel.add(getNotaTipoPanel(), gc);

        gc.gridx++;
        panel.add(getCampo("Data (dd/mm/AAAA): ", 7, data));

        return panel;
    }

    private JPanel getNotaTipoPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Tipo de nota: "));
        panel.add(notaTipo);
        return panel;
    }

    private JPanel getInformacoesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Informações Destinatário"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(5, 5, 5, 5);

        panel.add(getCampo("Nome: ", 15, nome), gc);

        gc.gridx++;
        panel.add(getCampo("Telefone: ", 9, telefone), gc);

        gc.gridx++;
        panel.add(getCampo("CPF / CNPJ: ", 9, CpfCnpj), gc);

        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy++;
        panel.add(getCampo("Cidade: ", 13, cidade), gc);

        gc.gridx++;
        panel.add(getCampo("Bairro: ", 13, bairro), gc);

        gc.gridx = 0;
        gc.gridy++;
        panel.add(getCampo("Rua: ", 15, rua), gc);

        gc.gridx++;
        panel.add(getCampo("Numero: ", 3, numero), gc);

        return panel;
    }

    private JPanel getTablePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Produtos"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 4;
        gc.insets = new Insets(5, 5, 5, 5);

        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        panel.add(scrollPane, gc);

        gc.gridheight = 1;
        gc.gridx++;
        JButton adicionarItem = new JButton("Adicionar item");
        adicionarItem.addActionListener((ActionEvent e) -> {
            tableModel.addRow(new String[]{"", "", "", ""});
        });
        panel.add(adicionarItem, gc);

        gc.gridy++;
        JButton removerItem = new JButton("Remover item");
        removerItem.addActionListener((ActionEvent e) -> {
            if (table.getSelectedRow() != -1) {
                tableModel.removeRow(table.getSelectedRow());
            }
        });
        panel.add(removerItem, gc);

        return panel;
    }

    public JTextField getNota() {
        return nota;
    }

    public JComboBox getNotaTipo() {
        return notaTipo;
    }

    public JTextField getData() {
        return data;
    }

    public JTextField getNome() {
        return nome;
    }

    public JTextField getTelefone() {
        return telefone;
    }

    public JTextField getCpfCnpj() {
        return CpfCnpj;
    }

    public JTextField getRua() {
        return rua;
    }

    public JTextField getBairro() {
        return bairro;
    }

    public JTextField getNumero() {
        return numero;
    }

    public JTextField getCidade() {
        return cidade;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
    public void mostrarEDI(String EDI){
        JPanel panel = new JPanel();
        JTextArea ta = new JTextArea(EDI);
        JScrollPane scroll = new JScrollPane(ta);
        panel.add(scroll);
        JDialog dialog = new JDialog(this);
        dialog.setMinimumSize(new Dimension(900, 600));
        dialog.setPreferredSize(new Dimension(900, 600));
        dialog.add(panel);
        
        dialog.setVisible(true);
    }
    
}
