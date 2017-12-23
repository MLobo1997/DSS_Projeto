/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author diogoleitao
 */
public class UC {
    
    private String sigla;
    private Integer ano;
    private Integer semester;
    private String nome;

    public UC() {
        this.sigla = new String();
        this.ano = new Integer(0);
        this.semester = new Integer(0);
        this.nome = new String();
    }

    public UC(String sigla, Integer ano, Integer semester, String nome) {
        this.sigla = sigla;
        this.ano = ano;
        this.semester = semester;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
}
