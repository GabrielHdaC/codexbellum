# CLAUDE.md — Codex Bellum

Instruções de como me comportar e trabalhar neste workspace. Leia antes de mexer no código.

## O que é o projeto

Codex Bellum é um projeto acadêmico de POO em **Java puro**. Lê uma base de fichas de
personagens de RPG (heróis e monstros) de um arquivo texto, converte cada linha em
objetos, simula combate por turnos e persiste a base em binário. O foco é demonstrar
os conceitos de POO exigidos pela disciplina — veja [requirements.md](requirements.md)
para o checklist e onde cada requisito está implementado.

## Idioma

- **Código, comentários, nomes e Javadoc: português** (sem acentos em strings/identificadores
  quando puder quebrar encoding; os comentários usam acento normalmente).
- **Converse comigo em português.** O usuário é estudante; explique o *porquê* das decisões,
  não só o *o quê*. Comentários no estilo "memória futura" (explicam a intenção) são bem-vindos
  e fazem parte do estilo do projeto — mantenha esse tom ao editar comentários.

## Tech stack

- **Linguagem:** Java (sintaxe moderna: `instanceof` com pattern matching, `Set.of`, enums com corpo).
- **Build:** `javac` puro. **NÃO há Maven nem Gradle.** Não crie `pom.xml`/`build.gradle`.
- **Testes:** JUnit 5 (Jupiter) via JUnit Console Standalone em `lib/`.
- **Persistência:** `ObjectOutputStream`/`ObjectInputStream` (binário `.dat`) e leitura de texto (`fichas.txt`).
- **Sem dependências externas** além do jar do JUnit em `lib/`.

## Estrutura

```
src/
  app/            Principal (menu) e camadas de UI (GerenciadorMenu, GerenciadorBatalha)
  combate/        Combatente (interface), Batalha (coordena o turno)
  modelo/         Personagem (abstract) -> Heroi, Monstro
                  Equipamento (abstract) -> Arma, Magia
                  Enums: Classe, Sexo, TipoMagia
  repositorio/    FichaRepositorio (lê txt + serialização), Exportavel,
                  FichaInvalidaException, PersistenciaException
  test/           BatalhaTest, FichaRepositorioTest
  fichas.txt      base de dados texto (cabeçalho + 520 registros)
```

## Como compilar e rodar os testes

Não use Maven/Gradle. A partir da raiz:

```bash
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d out_test -cp "lib/junit-platform-console-standalone-1.10.2.jar" @sources.txt
java -jar "lib/junit-platform-console-standalone-1.10.2.jar" execute --class-path out_test --scan-class-path --details=tree
```

`out_test/` e `sources.txt` são temporários — apague depois.

## Convenções de design (respeite ao editar)

- **Encapsulamento:** campos `private`, acesso por getter/setter. `setVida` nunca deixa vida negativa (`Math.max(0, ...)`).
- **`Classe` (enum) usa Strategy:** cada constante implementa `curar`, `somaMagiaNoAtaque`, `reduzirDano`.
  Para adicionar uma classe de herói, **acrescente uma constante** — não espalhe `if`/`switch` no `Heroi`.
- **Combate é polimórfico:** `Batalha` e `Combatente` não conhecem `Heroi`/`Monstro` concretamente.
  O atacante calcula o dano; **o defensor aplica a própria defesa**. Não inverta isso.
- **Persistência não imprime no console:** sinaliza erro com `PersistenciaException`/`FichaInvalidaException`
  (ambas `RuntimeException`); quem mostra mensagem ao usuário é a camada de menu.
- **`getFichas()` devolve cópia defensiva** (raso). Não exponha a `List` interna.

## Guidelines de memória

- Memória persistente fica em `~/.claude/projects/.../memory/` com índice em `MEMORY.md`.
- Já existe a nota **build-e-testes** (como compilar/testar sem Maven). Consulte antes de inventar comandos de build.
- Não salve em memória o que já está documentado aqui ou derivável do git/código. Salve só o não-óbvio.

## Ao trabalhar aqui

- Verifique fatos contra o código atual antes de afirmar (memórias podem estar desatualizadas).
- Rode os testes depois de mexer em `modelo/`, `combate/` ou `repositorio/`.
- Mantenha o estilo de comentário existente: explique a intenção, não narre o óbvio.
- Não commite nem dê push sem o usuário pedir.

## Comandos exatos de teste

```bash
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d out_test -cp "lib/junit-platform-console-standalone-1.10.2.jar" @sources.txt
java -jar "lib/junit-platform-console-standalone-1.10.2.jar" execute --class-path out_test --scan-class-path --details=tree
rm -rf out_test sources.txt
```

São 2 classes de teste (`BatalhaTest`, `FichaRepositorioTest`). `out_test/` e `sources.txt` são temporários.

## Padrão de commit

- Só commite/push quando o usuário pedir. Rode os testes antes.
- Mensagem curta e descritiva em português, no imperativo (ex.: "Adiciona CLAUDE.md e requirements.md").
- Termine a mensagem de commit com:
  `Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>`

## Fluxo automatizado de PR (padrão)

`gh` (GitHub CLI) já está instalado e autenticado (conta GabrielHdaC, HTTPS).
Quando o usuário pedir "abre o PR" / "commita", executo o ciclo completo de uma vez —
sem deixar URL para o usuário clicar:

```bash
git checkout -b <tipo>/<descricao-curta>      # nunca commitar direto na main
# rodar a suite de testes (secao acima) ANTES de commitar
git add <arquivos>
git commit -m "..."                            # com Co-Authored-By
git push -u origin <branch>
gh pr create --base main --head <branch> --title "..." --body "..."
```

- Prefixos de branch: `docs/`, `feature/`, `refactor/`, `fix/`.
- Corpo do PR termina com:
  `🤖 Generated with [Claude Code](https://claude.com/claude-code)`
- **Não mergear pela URL manualmente** enquanto o ciclo é automatizado: deixar o
  merge para `gh pr merge` (ou pedir explicitamente). Mergear pela web faz o `main`
  local divergir — foi o que motivou esta automação.
- Depois do merge: `git checkout main && git pull --ff-only && git branch -d <branch>`.

## Estado / decisões consolidadas (memória central)

- **Build:** Java puro, sem Maven/Gradle; JUnit 5 standalone em `lib/`.
- **Comentário do `atacar` default** reescrito em [Combatente.java:11](src/combate/Combatente.java) para descrever o `instanceof` pattern matching + regra "defensor aplica a defesa".
- **Docs de apoio:** [requirements.md](requirements.md) mapeia os 16 requisitos acadêmicos para `arquivo:linha` (todos atendidos: 520 registros no txt, 4 equals/hashCode, 7 try-blocks, 3 enums, 2 classes abstratas, custom exceptions RuntimeException).
- **Regras de escopo descobertas:** persistência sinaliza erro via exceção (não imprime); `getFichas()` é cópia defensiva; enum `Classe` = Strategy (nova classe = nova constante); combate é polimórfico (atacante calcula dano, defensor reduz); `MONSTRO` no txt vira `Monstro`, não passa por `Classe.valueOf`.
- **Pendências:** nenhuma aberta no momento.
