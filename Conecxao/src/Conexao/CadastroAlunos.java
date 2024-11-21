
package Conexao;

import java.sql.*;
import java.util.Scanner;




public class CadastroAlunos {
    
                //conexao BD
    private static final String URL = "jdbc:mysql://localhost:3306/escola"; // URL do banco
        private static final String USUARIO = "root"; // Usuário do MySQL
            private static final String SENHA = ""; // Senha do MySQL
             private static final String[] DISCIPLINAS = {"Matematica", "Portugues", "Ciencias", "Historia", "Geografia"};
                private static final int NUM_BIMESTRES = 4;

                //Main
                public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
                
                //criando scanner
             Scanner leia = new Scanner(System.in)) {

            System.out.println("Atenção! Você esta conectado ao Sistema Esolar");

            
            
           
//Recebendo dados de cadastro
            System.out.print("Digite a quantidade de alunos a serem cadastrados? ");
            int numAlunos = Integer.parseInt(leia.nextLine());
            
              //estrutura de repetição para cadastrar varios alunos
            for (int i = 0; i < numAlunos; i++) {
                System.out.println("\n----Cadastro do Aluno " + (i + 1) + " ----");

                
// Recebendo dados do aluno
                System.out.print("Nome: ");
                String nome = leia.nextLine().trim();

                System.out.print("Série: ");
                String serie = leia.nextLine().trim();

                System.out.print("Turma: ");
                String turma = leia.nextLine().trim();

// Inserir aluno no SistemaEscolar
                
String sqlAluno = "INSERT INTO aluno (nome, serie, turma) VALUES (?, ?, ?)";
                int alunoId;

                
                try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno, Statement.RETURN_GENERATED_KEYS)) {
                    stmtAluno.setString(1, nome);
                    stmtAluno.setString(2, serie);
                    stmtAluno.setString(3, turma);
                    stmtAluno.executeUpdate();

                    try (ResultSet rs = stmtAluno.getGeneratedKeys()) {
                        if (rs.next()) {
                            alunoId = rs.getInt(1);
                        } else {
                            throw new SQLException("Erro ao obter o ID do aluno.");
                        }
                    }
                }

// Inserir notas no banco
                System.out.println("\n----Cadastro de Notas para " + nome + " ----");
                String sqlNotas = "INSERT INTO notas (aluno_id, disciplina, nota1, nota2, nota3, nota4) VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmtNotas = conn.prepareStatement(sqlNotas)) {
                    
                    //estrutura de repetição para disciplinas e bimestres
                    for (String disciplina : DISCIPLINAS) {
                        System.out.println("\nNotas de " + disciplina + ":");

                        double[] notas = new double[NUM_BIMESTRES];
                        double somaNotas = 0;

                        for (int bimestre = 0; bimestre < NUM_BIMESTRES; bimestre++) {
                            System.out.print((bimestre + 1) + "º Bimestre: ");
                            notas[bimestre] = Double.parseDouble(leia.nextLine());
                            somaNotas += notas[bimestre];
                        }

// Calcular e mostrar a média da disciplina
                        double mediaDisciplina = somaNotas / NUM_BIMESTRES;
                        System.out.printf("Média de %s: %.2f\n", disciplina, mediaDisciplina);

// Inserir notas no banco
                        stmtNotas.setInt(1, alunoId);
                        stmtNotas.setString(2, disciplina);
                        stmtNotas.setDouble(3, notas[0]);
                        stmtNotas.setDouble(4, notas[1]);
                        stmtNotas.setDouble(5, notas[2]);
                        stmtNotas.setDouble(6, notas[3]);
                        stmtNotas.executeUpdate();
                    }
                }
            }

        
            
            System.out.println("\n-------RELATÓRIO DE NOTAS-------");
            String sqlRelatorio = "SELECT a.nome, a.serie, a.turma, n.disciplina, " +
                                  "n.nota1, n.nota2, n.nota3, n.nota4, " +
                                  "((n.nota1 + n.nota2 + n.nota3 + n.nota4) / 4) AS media " +
                                  "FROM aluno a " +
                                  "JOIN notas n ON a.id = n.aluno_id " +
                                  "ORDER BY a.nome, n.disciplina";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlRelatorio)) {

                String alunoAtual = "";

                while (rs.next()) {
                    String nomeAluno = rs.getString("nome");
                    String serie = rs.getString("serie");
                    String turma = rs.getString("turma");
                    String disciplina = rs.getString("disciplina");
                    double nota1 = rs.getDouble("nota1");
                    double nota2 = rs.getDouble("nota2");
                    double nota3 = rs.getDouble("nota3");
                    double nota4 = rs.getDouble("nota4");
                    double media = rs.getDouble("media");

                    if (!nomeAluno.equals(alunoAtual)) {
                        alunoAtual = nomeAluno;
                        
                        System.out.println("------------------------");
                        
                        System.out.printf("\nAluno: %s \n"
                                + "Série: %s "
                                + "\nTurma: %s\n", nomeAluno, serie, turma);
                    }
                    
                    System.out.println("------------------------");
                    System.out.printf("Disciplina: %s \n"
                            + "Notas: %.1f, %.1f, %.1f, %.1f "
                            + "\nMédia: %.2f\n",
                            disciplina, nota1, nota2, nota3, nota4, media);
                   
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite números corretamente.");
        }
    }
}