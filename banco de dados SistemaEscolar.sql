CREATE DATABASE escola;

USE escola;

CREATE TABLE aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    serie VARCHAR(20),
    turma VARCHAR(10)
);

CREATE TABLE notas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id INT,
    disciplina VARCHAR(50),
    nota1 DOUBLE,
    nota2 DOUBLE,
    nota3 DOUBLE,
    nota4 DOUBLE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);
    


