# Cola da apresentação — CodexBellum

Respostas curtas para as perguntas que o professor provavelmente vai fazer.
Cada uma aponta onde está no código.

## "O que é esse cast aqui?"

`(Personagem) defensor` na Batalha e `(Combatente) atacante` no Principal.

> O cast é uma conversão de tipo: eu garanto pro Java que aquele objeto é de
> um tipo mais específico do que a variável declara. Na Batalha, o defensor
> chega como `Combatente`, mas o `atacar` pede `Personagem` — como todo
> Heroi/Monstro é os dois ao mesmo tempo, a conversão é segura.
> Também tem cast no `importarDat`: o `readObject` devolve `Object` genérico
> e eu aviso que ali dentro tem uma `List<Personagem>`.

## "Onde tem polimorfismo?"

> Na linha `atacante.atacar(...)` da Batalha: ela não sabe se quem ataca é
> Heroi ou Monstro — chama o método da interface e cada classe executa a
> própria versão. Também na lista: `List<Personagem>` guarda heróis e
> monstros juntos.

## "Qual a diferença entre sobrecarga e sobrescrita?"

> Sobrecarga: mesmo nome, parâmetros diferentes, na MESMA classe —
> `atacar(Personagem)` e `atacar(Personagem, Arma)` no Heroi.
> Sobrescrita: a filha redefine o método do pai/interface — os `@Override`
> de `usarHabilidade`, `equals` etc.

## "Por que Personagem é abstrata? Por que interface?"

> Abstrata porque não faz sentido existir um "personagem genérico" — só
> heróis e monstros. Ela guarda o que é comum e obriga os filhos a
> implementar o `usarHabilidade` (método abstrato).
> Interface (`Combatente`, `Exportavel`) é um contrato puro: diz O QUE fazer,
> não COMO. Uma classe só herda de um pai, mas pode implementar várias
> interfaces.

## "Para que serve o Serializable?"

> É uma interface vazia (de marcação): autoriza o Java a converter o objeto
> em bytes. Sem ela, o `writeObject` do export lança
> `NotSerializableException`. Está em Personagem, Arma e Magia — Heroi e
> Monstro herdam.

## "Por que o getFichas devolve new ArrayList<>(fichas)?"

> Cópia defensiva: quem chama recebe uma cópia da lista, então não consegue
> alterar a estrutura interna do repositório (encapsulamento). A cópia é
> rasa: os personagens dentro são os mesmos objetos, de propósito — a
> batalha precisa alterar a vida deles.

## "Por que a exceção estende RuntimeException?"

> `FichaInvalidaException` é não-verificada: não obriga try/catch em todo
> lugar. Ela sobe até quem souber tratar — e indica erro de DADO (linha
> quebrada no arquivo), traduzindo erros técnicos (parseInt/valueOf) para a
> linguagem do domínio.

## "O que faz o sc.nextLine() dentro do catch?"

> Quando alguém digita texto em vez de número, o `nextInt` falha MAS o texto
> fica preso no Scanner. O `nextLine()` descarta esse lixo — sem ele, o
> próximo `nextInt` falharia de novo, em loop infinito.

## "O que é Personagem::getVida?"

> Method reference, no `ordenarPorVida`: diz ao Comparator "compare os
> personagens pelo valor que o getVida de cada um devolver". Equivale a
> `p -> p.getVida()`.

## "Por que new Principal() dentro do próprio main?"

> O main é static: roda sem nenhum objeto existir. O exibirMenu é método de
> instância — para chamá-lo, preciso de um objeto. Static pertence à
> "planta"; método de instância pertence à "casa construída".

## "Por que equals e hashCode andam juntos?"

> Contrato do Java: objetos iguais pelo equals DEVEM ter o mesmo hashCode,
> senão coleções como HashMap/HashSet se perdem ao procurar o objeto.

## "O que é try-with-resources?"

> O `try (BufferedReader br = ...)`: o recurso aberto nos parênteses é
> fechado automaticamente no fim, mesmo se der erro. Usado no lerTxt,
> exportarDat e importarDat.

## O fluxo dos dados (saber desenhar!)

```
fichas.txt --lerTxt--> List<Personagem> --exportarDat--> fichas.dat
   (texto)              (objetos na RAM)  <--importarDat--  (binário)
```

## Quem fez o quê

- Gabriel: pacotes `app` (menu), `combate` (Batalha) e `repositorio`
  (leitura do txt, ordenação, export/import .dat).
- Parceiro: pacote `modelo` (Personagem, Heroi, Monstro, Arma, Magia,
  enums, regras de combate por classe).
