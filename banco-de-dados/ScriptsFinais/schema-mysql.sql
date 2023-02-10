DROP TABLE IF EXISTS confirmacao_token;
DROP TABLE IF EXISTS produtos_favoritos;
DROP TABLE IF EXISTS avaliacoes;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS produtos_caracteristicas;
DROP TABLE IF EXISTS imagens;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS funcoes;
DROP TABLE IF EXISTS cidades;
DROP TABLE IF EXISTS caracteristicas;
DROP TABLE IF EXISTS categorias;

CREATE TABLE IF NOT EXISTS categorias (
  id INT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(50) NOT NULL,
  descricao VARCHAR(200) NOT NULL,
  url_imagem VARCHAR(250) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS caracteristicas (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  icone VARCHAR(250) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cidades (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  uf VARCHAR(50) NOT NULL,
  pais VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS funcoes (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usuarios (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  sobrenome VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,
  ativado TINYINT(1) NOT NULL DEFAULT 0,
  id_funcao INT NOT NULL,
  usuario_tipo VARCHAR(50),
  PRIMARY KEY (id),
  CONSTRAINT fk_usuarios_funcoes
      FOREIGN KEY (id_funcao) REFERENCES funcoes (id)
);

CREATE TABLE IF NOT EXISTS produtos (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  descricao TEXT NOT NULL,
  score DOUBLE,
  endereco VARCHAR(200) NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  regras_gerais TEXT NOT NULL,
  regras_seguranca TEXT NOT NULL,
  regras_cancelamento TEXT NOT NULL,
  id_cidade INT NOT NULL,
  id_categoria INT NOT NULL,
  id_proprietario INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_cidade
            FOREIGN KEY (id_cidade) REFERENCES cidades (id),
  CONSTRAINT fk_categoria
              FOREIGN KEY (id_categoria) REFERENCES categorias (id),
  CONSTRAINT fk_proprietario
                FOREIGN KEY (id_proprietario) REFERENCES usuarios (id)
);

CREATE TABLE IF NOT EXISTS imagens (
  id INT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(50) NOT NULL,
  url VARCHAR(250) NOT NULL,
  id_produto INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_produto_img
          FOREIGN KEY (id_produto) REFERENCES produtos (id)
);

CREATE TABLE IF NOT EXISTS produtos_caracteristicas (
  id_produto INT NOT NULL,
  id_caracteristica INT NOT NULL,
  PRIMARY KEY (id_produto,id_caracteristica),
  CONSTRAINT fk_produto
      FOREIGN KEY (id_produto) REFERENCES produtos (id),
  CONSTRAINT fk_caracteristica
      FOREIGN KEY (id_caracteristica) REFERENCES caracteristicas (id)
);


CREATE TABLE IF NOT EXISTS reservas (
  id INT NOT NULL AUTO_INCREMENT,
  hora_reserva TIME NOT NULL,
  data_entrada DATE NOT NULL,
  data_saida DATE NOT NULL,
  id_cliente INT NOT NULL,
  id_produto INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_reservas_usuarios
    FOREIGN KEY (id_cliente) REFERENCES usuarios (id),
  CONSTRAINT fk_reservas_produtos
      FOREIGN KEY (id_produto) REFERENCES produtos (id)
);

CREATE TABLE IF NOT EXISTS avaliacoes (
  id_produto INT NOT NULL,
  id_usuario INT NOT NULL,
  score DOUBLE NOT NULL,
  PRIMARY KEY (id_produto,id_usuario),
  CONSTRAINT fk_avaliacao_produto
      FOREIGN KEY (id_produto) REFERENCES produtos (id),
  CONSTRAINT fk_avaliacao_usuario
      FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
);

CREATE TABLE IF NOT EXISTS produtos_favoritos (
  id_produto INT NOT NULL,
  id_usuario INT NOT NULL,
  PRIMARY KEY (id_produto,id_usuario),
  CONSTRAINT fk_favorito_produto
      FOREIGN KEY (id_produto) REFERENCES produtos (id),
  CONSTRAINT fk_favorito_usuario
      FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
);

CREATE TABLE IF NOT EXISTS confirmacao_token (
  id INT NOT NULL AUTO_INCREMENT,
  token VARCHAR(100) NOT NULL,
  criado_em TIMESTAMP NOT NULL,
  expira_em TIMESTAMP NOT NULL,
  confirmado_em TIMESTAMP,
  id_usuario INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_confirmacao_usuario
      FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
);