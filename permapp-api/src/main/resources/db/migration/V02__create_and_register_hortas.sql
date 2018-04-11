CREATE TABLE hortas(
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(20) NOT NULL,
  LOGRADOURO VARCHAR(50),
  NUMERO VARCHAR(10),
  COMPLEMENTO VARCHAR(20),
  CEP VARCHAR(9),
  CIDADE VARCHAR(20),
  ESTADO VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO hortas(NOME, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO) VALUES ('Horta 1', 'Rua José Ferreiro', '123', 'NA', '01233-020', 'São Paulo', 'SP');
INSERT INTO hortas(NOME) VALUES ('Horta 2');
INSERT INTO hortas(NOME) VALUES ('Horta 3');