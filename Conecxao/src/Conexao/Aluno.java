package Conexao;

public class Aluno {
    private String nome;
    private String serie;
    private String turma;
    private double[][] notas; // [disciplina][bimestre]

    public Aluno(String nome, String serie, String turma, int numDisciplinas, int numBimestres) {
        this.nome = nome;
        this.serie = serie;
        this.turma = turma;
        this.notas = new double[numDisciplinas][numBimestres];
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public String getSerie() {
        return serie;
    }

    public String getTurma() {
        return turma;
    }

    public double[][] getNotas() {
        return notas;
    }

    public void setNota(int disciplina, int bimestre, double nota) {
        notas[disciplina][bimestre] = nota;
    }

    public double calcularMedia(int disciplina) {
        double soma = 0;
        for (double nota : notas[disciplina]) {
            soma += nota;
        }
        return soma / notas[disciplina].length;
    }
}
