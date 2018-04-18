CREATE TABLE categorias(
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  NOME VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX categorias_nome ON categorias(NOME);

INSERT INTO categorias(NOME) VALUES ('Categoria 1');
INSERT INTO categorias(NOME) VALUES ('Categoria 2');
INSERT INTO categorias(NOME) VALUES ('Categoria 3');