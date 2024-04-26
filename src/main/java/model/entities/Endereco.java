
package model.entities;

public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;

    public Endereco(String rua, String numero, String bairro, String cidade) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public String getCidade() {
        return cidade;
    }
    
    public String getEndereco(){
        String endereco = rua + " " + numero + ". " + bairro;
        return endereco;
    }
}
