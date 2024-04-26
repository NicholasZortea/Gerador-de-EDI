
package model.entities;

public class Individuo {
    private String nome;
    private String telefone;
    private String CpfCnpj;

    public Individuo(String nome, String telefone, String CpfCnpj) {
        this.nome = nome;
        this.telefone = telefone;
        this.CpfCnpj = CpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpfCnpj() {
        return CpfCnpj;
    }
    
    
}
