
package model.entities;

public class Produto {
    private String descricao;
    private String quantidade;
    private String valorUnitario;
    private String valorTotal;

    public Produto(String descricao, String quantidade, String valorUnitario, String valorTotal) {
        if(descricao ==null || quantidade == null || valorUnitario == null || valorTotal == null){
            throw new NullPointerException();
        }
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public String getValorTotal() {
        return valorTotal;
    }
    
    @Override
    public String toString(){
        String x;
        x = "desc: " + descricao + "qtd: " + quantidade + "val un: " 
                + valorUnitario + "val tot: " + valorTotal;
        return x;
    }
    
}
