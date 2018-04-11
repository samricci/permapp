CREATE TABLE plantas(
	ID BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	CATEGORIA_ID BIGINT(20) NOT NULL,
  NOME VARCHAR(50) NOT NULL,
  NOME2 VARCHAR(50),
  NOME3 VARCHAR(50),
  NOME4 VARCHAR(50),
  NOME5 VARCHAR(50),
  ESTRATO VARCHAR(15),
  ESPACOL VARCHAR(15),
  ESPACOH VARCHAR(15),
  TEMPO_COLHEITA VARCHAR(20),
  TEMPO_REBROTA VARCHAR(20),
  TEMPO_VIDA VARCHAR(20),
  VALOR_MUDA DECIMAL(10,2),
  VALOR_SEMENTE DECIMAL(10,2),
  VALOR_VENDA DECIMAL(10,2),
  INFORMACOES VARCHAR(500),
  IMAGEM LONGBLOB,
  IMAGEM2 LONGBLOB,
  SOMBREAMENTO SMALLINT(3),
  FOREIGN KEY (CATEGORIA_ID) REFERENCES categorias(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;