INSERT INTO funcoes (nome) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_CLIENT'),
    ('ROLE_PROPRIETOR');

INSERT INTO usuarios (nome, sobrenome, email, senha,  ativado, id_funcao, usuario_tipo) VALUES
    ('Thays', 'Gama', 'thays@gama.com', '$2a$10$P/tNdWZgyOMThfSx.YCxke7K7FG3cAfKQD4QVmJb.wXWASgja2bSe', 1, 1, 'C'),
    ('Esther', 'Maria', 'esther@maria.com', '$2a$10$I/e4FGU5pV1gbkbA8WPUFeGnnLYJJuX9kfb/ildzTqutPrZZ6NYOq',1, 2, 'A'),
    ('Leonardo', 'Martins', 'leo@martins.com', '$2a$10$4q439DiaETwZsjW.UsPRbu5kJtejJYF1hPMu8do1wvleqnM4/9AlK',1, 3, 'P');
