CREATE TABLE usuario_role (
  USUARIO_ID BIGINT(20) NOT NULL,
  ROLE_ID SMALLINT NOT NULL,
  PRIMARY KEY(USUARIO_ID, ROLE_ID),
  FOREIGN KEY(USUARIO_ID) REFERENCES usuarios(ID),
  FOREIGN KEY(ROLE_ID) REFERENCES roles(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permissao (
  ROLE_ID SMALLINT NOT NULL,
  PERMISSAO_ID SMALLINT NOT NULL,
  PRIMARY KEY(ROLE_ID, PERMISSAO_ID),
  FOREIGN KEY(ROLE_ID) REFERENCES roles(ID),
  FOREIGN KEY(PERMISSAO_ID) REFERENCES permissoes(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- INSERT ALL PERMISSIONS INTO ADMIN ROLE
INSERT INTO ROLE_PERMISSAO VALUES(1, 1);
INSERT INTO ROLE_PERMISSAO VALUES(1, 2);
INSERT INTO ROLE_PERMISSAO VALUES(1, 3);
INSERT INTO ROLE_PERMISSAO VALUES(1, 4);
INSERT INTO ROLE_PERMISSAO VALUES(1, 5);
INSERT INTO ROLE_PERMISSAO VALUES(1, 6);
INSERT INTO ROLE_PERMISSAO VALUES(1, 7);
INSERT INTO ROLE_PERMISSAO VALUES(1, 8);
INSERT INTO ROLE_PERMISSAO VALUES(1, 9);
INSERT INTO ROLE_PERMISSAO VALUES(1, 10);
INSERT INTO ROLE_PERMISSAO VALUES(1, 11);
INSERT INTO ROLE_PERMISSAO VALUES(1, 12);


