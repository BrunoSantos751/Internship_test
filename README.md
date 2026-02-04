# HtmlAnalyzer

Solução para o desafio técnico "Internship Test".

## Requisitos

- JDK 17.2 ou superior (Código compatível com Java 17)

## Como Compilar

Navegue até o diretório contendo o arquivo `HtmlAnalyzer.java` e execute:

```bash
javac HtmlAnalyzer.java
```

*Nota: Se você estiver usando uma versão do Java muito mais recente (como Java 21) e quiser garantir compatibilidade estrita com o bytecode do Java 17, pode usar:*
```bash
javac --release 17 HtmlAnalyzer.java
```

## Como Executar


Para analisar uma URL específica, execute:

```bash
java HtmlAnalyzer <URL>
```

Exemplo:
```bash
java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
```

Caso nenhum argumento seja fornecido, o programa executará com uma URL de exemplo padrão:

```bash
java HtmlAnalyzer
```

## Notas da Implementação

- A solução segue as premissas de formatação HTML simplificada descritas no PDF.
- Detecta HTML mal formatado (tags não balanceadas ou pilha não vazia ao final).
- Em caso de falha na conexão ou URL inválida, retorna `URL connection error`.
