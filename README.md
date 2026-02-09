# HtmlAnalyzer

Solução para o desafio técnico "Internship Test".

O programa analisa o código HTML de uma URL fornecida e retorna o primeiro trecho de texto contido no nível mais profundo da estrutura.

## Requisitos

- JDK 17 ou superior (compatível com Java 17)

## Como Compilar

Navegue até o diretório contendo o arquivo `HtmlAnalyzer.java` e execute:

```bash
javac HtmlAnalyzer.java
```

## Como Executar

Para analisar uma URL específica, execute o seguinte comando:

```bash
java HtmlAnalyzer <URL>
```

**Exemplo:**
```bash
java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
```

## Saída do Programa

O programa gera apenas os seguintes tipos de output no console padrão:

1.  **Trecho de texto identificado:** O texto no nível mais profundo da estrutura HTML.
2.  **malformed HTML:** Caso a estrutura HTML esteja malformada (tags desbalanceadas, tag de fechamento sem abertura, estrutura incompleta).
3.  **URL connection error:** Caso não seja possível obter o conteúdo HTML por falha de conexão ou caso a URL não seja fornecida corretamente.

## Premissas e Limitações

A solução segue as premissas simplificadas descritas no desafio:

-   O HTML é analisado linha a linha.
-   Cada linha contém apenas um tipo de conteúdo (tag de abertura, tag de fechamento ou texto).
-   Ignora espaços em branco e linhas vazias.
-   Tags de abertura não possuem atributos.
-   Não utiliza bibliotecas externas ou pacotes de manipulação de XML/DOM do Java.
