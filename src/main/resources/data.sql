SET FOREIGN_KEY_CHECKS=0;

REPLACE INTO USUARIO(nome, email, senha) VALUES('Loja', 'loja@gft.com', '$2a$10$wsDzcrw8lQdXPh81lnZ8p.XC0PtUAbQ2Sru9OFmxQrHnn12prbjkO');
REPLACE INTO USUARIO(nome, email, senha) VALUES('Cliente', 'cliente@gft.com', '$2a$10$wsDzcrw8lQdXPh81lnZ8p.XC0PtUAbQ2Sru9OFmxQrHnn12prbjkO');

--Popular Produto
REPLACE INTO produto (codigo, nome, descricao, unidade) values (1,"Oxford Vegano Unissex", "Oxford Vegano Unissex Heavy Preto - Tamanhos 40 a 44", "Unitário");
REPLACE INTO produto (codigo, nome, descricao, unidade) values (2,"Sandália Vegana Birken", "Sandália Vegana Birken Morcego Spikes", "Unitário");
REPLACE INTO produto (codigo, nome, descricao, unidade) values (3,"Bota Vegana Biker", "Bota Vegana Biker Spikes Banshee", "Unitário");

--Popular Estoque
REPLACE INTO estoque (codigo, produto_codigo, quantidade, valor_venda) values (1,1, 0, 0);
REPLACE INTO estoque (codigo, produto_codigo, quantidade, valor_venda) values (2,2, 0, 0);
REPLACE INTO estoque (codigo, produto_codigo, quantidade, valor_venda) values (3,3,0, 0);

--Popular Fonecedor
REPLACE INTO fornecedor (codigo, cnpj, nome, telefone, email, logradouro, numero, complemento, cep, bairro, cidade, estado) values (1,"222.222.222.222.22", "diego" , "(15) 9 2929-0292", "lelo@gft.com","rua 1", "244", "casa", "18077-333", "bairro 1", "Sorocaba", "São Paulo");

--Popular Cliente
REPLACE INTO cliente (codigo, cpf, nome, telefone, email, logradouro, numero, complemento, cep, bairro, cidade, estado) values (1,"111.111.111.22", "diego" , "(15) 9 2222-2222", "test@gft.com","rua 1", "333", "apto", "18077-222", "bairro 2", "Sorocaba", "São Paulo");

--Popular Itens Compra
REPLACE INTO itens_compra (codigo, quantidade, valor, produto_codigo) values (1,2, 100 ,1);

--Popular Itens Compra
REPLACE INTO compra (codigo, codigo_fornecedor) values (1,1);

--Perfil
REPLACE INTO perfil(codigo, nome) values (1,'Loja');
REPLACE INTO perfil(codigo,nome) values (2,'Cliente');

--Perfis
REPLACE INTO usuario_perfis(codigo_usuario, codigo_perfis) values (1,1);
REPLACE INTO usuario_perfis(codigo_usuario, codigo_perfis) values (2,2);


SET FOREIGN_KEY_CHECKS=1;