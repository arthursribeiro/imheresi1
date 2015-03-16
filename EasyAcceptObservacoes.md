**Obs geral:** Quando ele coloca só um nome é porque ta chamando o método ^^

## 1. Retorno ##
linha 21 do us1.txt:
```
id1=getUsuarioPorNome nome=Fulano indice=1
```
no caso id1 é o id (int) do usuário Fulano (mesmo não estando com as aspas, é uma String) no sistema.
Não sei o que é esse indice ainda.

**Obs:** ele passa Fulano, mas define o usuario com o nome Fulano de Tal.. isso acontece com outros casos.. creio que tem que usar String.contains pra pegar o nome e nao a String que foi passada por completo.

## 2. expect ##

linha 21 do us1.txt:
```
expect "Fulano de Tal" getAtributoUsuario id=${id1} atributo=nome
```
o metodo getAtributoUsuario recebe o id1 (variavel definida la em cima) e uma String...

## 3. expectError ##

linhas 48-49 do us1.txt
```
expectError "Username eh um dado obrigatorio." criarUsuario userName= \
nome="Jurema Santos" email="jurema@email.com" senha="8797n75" telefone="558399999999"
```
nesse caso ele ta chamando o criarUsuario sem o campo userName (userName == null) e tem que ser lançada uma exceção com a mensagem do jeito que está escrito (se esquecer o "." no fim ele não passa)

**Obs:** como a gente vai implementar isso? um monte de if é a unica coisa que me vem a cabeça =/

## 4. equalFiles ##

linha 32 do us4.txt
```
equalFiles arq1=convites.log arq2=testesAceitacao/saidas/us4_1.txt
```

compara os dois arquivos =P
**Obs:** Ele compara parecido com o diff do linux. tem uma linha que é um monte de "**" se o numero de "**" for diferente, não passa no teste.