|Tópicos|
|:-------|
|[Métodos Gerais](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#Métodos_Gerais)|
|[User Story 1](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_1)|
|[User Story 2](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_2)|
|[User Story 3](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_3)|
|[User Story 4](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_4)|
|[User Story 5](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_5)|
|[User Story 6](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_6)|
|[User Story 7](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#User_Story_7)|
|[Erros](http://code.google.com/p/imheresi1/wiki/ImplementarMetodos#Erros)|

<br>
<h1>Métodos Gerais</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> zerarSistema </td><td> zera o sistema e todos os cadastrados </td></tr>
<tr><td> encerrarSistema </td><td> grava o cadastro em arquivo e encerra o programa </td></tr></tbody></table>

<br>
<h1>User Story 1</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> criarUsuario </td><td> 5 atributos, (1) </td></tr>
<tr><td> getUsuarioPorNome </td><td> 2 atributos, retorna o id do usuario, (2) </td></tr>
<tr><td> atualizarUsuario </td><td> 3 atributos, (3) </td></tr>
<tr><td> removerUsuario </td><td> 1 atributo, (4) </td></tr>
<tr><td> getAtributoUsuario </td><td> 2 atributos </td></tr></tbody></table>

<br>
<h1>User Story 2</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> login </td><td> 3 atributos, (5) </td></tr>
<tr><td> logout </td><td> 1 atributo, (6) </td></tr></tbody></table>

<br>
<h1>User Story 3</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> cadastrarUsuario </td><td> 6 atributos, retorna o id de autenticacao do usuario </td></tr>
<tr><td> getLocalizadores </td><td> retorna uma string com os possiveis localizadores </td></tr>
<tr><td> obterLocalizacao </td><td> 1 parametro, (7) </td></tr>
<tr><td> setLocalizacao </td><td> 3 atributos, (8) </td></tr>
<tr><td> getLocalizacao </td><td> 1 atributo, retorna String com a posição </td></tr></tbody></table>

<br>
<h1>User Story 4</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> initMensageiro </td><td> 1 atributo </td></tr>
<tr><td> setDiretorioGabaritos </td><td> 1 atributo </td></tr>
<tr><td> enviarConvite </td><td> 2 atributos </td></tr>
<tr><td> confirmarCompartilhamento </td><td> 3 atributos </td></tr>
<tr><td> recusarCompartilhamento </td><td> 2 atributos </td></tr>
<tr><td> getAmigos </td><td> 1 atributo, retorna String com usernames de amigos, (9) </td></tr></tbody></table>

<br>
<h1>User Story 5</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> possoVerLocalizacao </td><td> 2 atributos, (10) </td></tr>
<tr><td> setCompartilhamento </td><td> 3 atributos </td></tr>
<tr><td> removerAmigo </td><td> 2 atributos,(11) </td></tr></tbody></table>

<br>
<h1>User Story 6</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> getLocalizacaoAmigo </td><td> 2 atributos, (12) </td></tr></tbody></table>

<br>
<h1>User Story 7</h1>

<table><thead><th> <b>Método</b> </th><th> <b>Obs</b> </th></thead><tbody>
<tr><td> enviarEmail </td><td> 4 atributos </td></tr>
<tr><td> enviarSMS </td><td> 3 atributos </td></tr>
<tr><td> setPortChat </td><td> 1 atributo </td></tr>
<tr><td> initChat </td><td> 2 atributos </td></tr>
<tr><td> enviarMensagem </td><td> 2 atributos </td></tr>
<tr><td> receberMensagem </td><td> 1 atributo </td></tr></tbody></table>

<br>
<h1>Erros</h1>

(1) falta de campos obrigatorios, userName existente, senha com menos de 6 caracteres.<br>
<br>
(2) usuario não existe<br>
<br>
(3) alterar username nao pode, usuario nao existe, falta de campos obrigatorios, senha com menos de 6 caracteres.<br>
<br>
(4) usuario não existe<br>
<br>
(5) login/senha/ip invalidos<br>
<br>
(6) sessão inexistente<br>
<br>
(7) GeoIp não conseguir achar a localização.<br>
<br>
(8) Lat/Long invalidos, usuario nao existe<br>
<br>
(9) apenas usuarios autenticados podem ver seus amigos<br>
<br>
(10) usuario desconhecido<br>
<br>
(11) usuario desconhecido, permissão negada(?)<br>
<br>
(12) usuario desconhecido, permissão negada(?)