# Divisão do Trabalho Final — CodexBellum

Trabalho em dupla. As assinaturas de todos os métodos já estão prontas e o projeto
compila — falta implementar a lógica. Cada parte mexe em arquivos diferentes,
então dá pra trabalhar em paralelo sem conflito.

**Como saber se terminou:** rodar os testes. Cada parte tem uma classe de teste
que precisa ficar toda verde.

```
javac -cp lib\junit-platform-console-standalone-1.10.2.jar -d out (gci -r src -filter *.java).FullName
java -jar lib\junit-platform-console-standalone-1.10.2.jar execute --class-path out --scan-class-path
```

---

## Parte 1 — Dados & Menu (pacotes `repositorio` e `app`)

**Meta: deixar o `FichaRepositorioTest` 100% verde.**

Arquivos: `FichaRepositorio.java`, `Principal.java` (+ uma linha em
`Personagem.java`, `Arma.java` e `Magia.java`, ver item 3)

1. **`lerTxt(String caminho)`** — ler o arquivo com `BufferedReader`, pular o
   cabeçalho, fazer `split(";")` de cada linha e converter:
   - classe `MONSTRO` → cria `Monstro`; senão → cria `Heroi` com o enum `Classe`
   - arma/magia vêm no formato `Nome:dano` (fazer `split(":")`)
   - linha com campo faltando/errado → lançar `FichaInvalidaException`
   - envolver o IO em **try/catch**
2. **`ordenarPorVida()`** — ordenar a lista da menor pra maior vida
   (`fichas.sort(Comparator.comparingInt(Personagem::getVida))`)
3. **`exportarDat` / `importarDat`** — `ObjectOutputStream` pra exportar e
   `ObjectInputStream` pra importar (o quadro inverteu os nomes!). Pra funcionar,
   adicionar `implements Serializable` em `Personagem`, `Arma` e `Magia`.
   Try/catch nos dois.
4. **`Principal`** — menu no console com `Scanner`: carregar txt, listar fichas,
   ordenar por vida, exportar .dat, importar .dat, batalhar (usa a Parte 2!) e
   sair. Try/catch pra opção inválida (`InputMismatchException`).

Requisitos do quadro cobertos: leitura do .txt, conversão pras classes, exceção
customizada em uso, ordenação, collection, export/import, maioria dos 5 try/catch.

---

## Parte 2 — Combate & Modelo (pacotes `modelo` e `combate`)

**Meta: deixar o `BatalhaTest` 100% verde.**

Arquivos: `Personagem.java`, `Heroi.java`, `Monstro.java`, `Batalha.java`

1. **`Personagem.receberDano(int dano)`** — `vida -= dano` (sem deixar negativo)
2. **`Heroi`**:
   - `atacar(Personagem)` — causa o dano da arma equipada
   - `atacar(Personagem, Arma)` — sobrecarga: causa o dano da arma passada
   - `defender(int dano)` — repassa pro `receberDano`
   - `usarHabilidade()` — efeito conforme a `Classe` (ex.: MAGO usa a magia,
     CLERIGO cura, GUERREIRO dano extra, LADINO esquiva)
3. **`Monstro`** — mesmos métodos, com lógica de monstro (ex.: habilidade usa a
   magia se tiver)
4. **`Batalha.executarTurno(Combatente, Combatente)`** — o atacante ataca e o
   defensor defende; pode validar e lançar exceção se alguém já estiver morto
   (vale como try/catch extra)

Requisitos do quadro cobertos: sobrecarga + sobrescrita, método abstrato
implementado, as 2 interfaces em uso, herança da classe abstrata.

---

## Combinados

- Cada um trabalha numa branch (`parte-1-dados` / `parte-2-combate`) e faz merge
  na `main` quando os testes da sua parte passarem.
- Único ponto de contato: a opção "batalhar" do menu (Parte 1) chama o
  `executarTurno` (Parte 2) — integrar no final, quando as duas partes estiverem
  prontas.
- `Personagem.java` é da Parte 2; a Parte 1 só adiciona o
  `implements Serializable` nele — avisar antes pra não dar conflito.
- Apresentação (máx. 10 min): cada um explica a própria parte.
