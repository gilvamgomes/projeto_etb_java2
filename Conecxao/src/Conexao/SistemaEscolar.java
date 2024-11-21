package Conexao;

import Conexao.Disciplina;
import Conexao.Aluno;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaEscolar {
    private static final int NUM_BIMESTRES = 4;
    private ArrayList<Aluno> alunos;
    private ArrayList<Disciplina> disciplinas;
    private Scanner scanner;

    public SistemaEscolar() {
        alunos = new ArrayList<>();
        disciplinas = new ArrayList<>();
        scanner = new Scanner(System.in);
        inicializarDisciplinas();
    }

    private void inicializarDisciplinas() {
        disciplinas.add(new Disciplina("Matemática"));
        disciplinas.add(new Disciplina("Português"));
        disciplinas.add(new Disciplina("Ciências"));
        disciplinas.add(new Disciplina("História"));
        disciplinas.add(new Disciplina("Geografia"));
    }
// cadastra o aluno com nome serie e turma//
    public void cadastrarAlunos() {
        System.out.println("=== Cadastro de Alunos ===");
        System.out.print("Quantos alunos deseja cadastrar? ");
        int numAlunos = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numAlunos; i++) {
            System.out.println("\nAluno " + (i + 1));
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Série: ");
            String serie = scanner.nextLine();
            System.out.print("Turma: ");
            String turma = scanner.nextLine();

            alunos.add(new Aluno(nome, serie, turma, disciplinas.size(), NUM_BIMESTRES));
        }
    }

    public void inserirNotas() {
        System.out.println("\n=== Inserção de Notas ===");
        for (Aluno aluno : alunos) {
            System.out.println("\nInserindo notas para: " + aluno.getNome());
            for (int i = 0; i < disciplinas.size(); i++) {
                System.out.println("Disciplina: " + disciplinas.get(i).getNome());
                for (int bimestre = 0; bimestre < NUM_BIMESTRES; bimestre++) {
                    System.out.print((bimestre + 1) + "º Bimestre: ");
                    double nota = lerNota();
                    aluno.setNota(i, bimestre, nota);
                }
            }
        }
    }

    private double lerNota() {
        while (true) {
            try {
                double nota = Double.parseDouble(scanner.nextLine());
                if (nota >= 0 && nota <= 10) {
                    return nota;
                } else {
                    System.out.print("Nota inválida. Digite novamente (0-10): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }

    public void mostrarRelatorio() {
        System.out.println("\n=== RELATÓRIO FINAL ===");
        for (Aluno aluno : alunos) {
            System.out.println("\nAluno: " + aluno.getNome());
            System.out.println("Série: " + aluno.getSerie());
            System.out.println("Turma: " + aluno.getTurma());

            for (int i = 0; i < disciplinas.size(); i++) {
                System.out.println("\nDisciplina: " + disciplinas.get(i).getNome());
                double media = aluno.calcularMedia(i);
                System.out.printf("Média: %.2f\n", media);
            }
        }
    }

    public void executar() {
        cadastrarAlunos();
        inserirNotas();
        mostrarRelatorio();
    }
}
