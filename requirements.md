# Requisitos do Projeto — Codex Bellum

Checklist dos requisitos acadêmicos com a localização de cada um no código, para
rastrear, encontrar e conferir rapidamente. Todos os links apontam para `arquivo:linha`.

> Status legenda: ✅ atendido • ⚠️ atende com observação

| # | Requisito | Status | Onde está |
|---|-----------|--------|-----------|
| 1 | Pelo menos 1 cabeçalho principal | ✅ | Cabeçalho do `.txt`: [src/fichas.txt:1](src/fichas.txt) (`nome;sexo;classe;arma;magia;vida`) |
| 2 | Pelo menos 500 registros no `.TXT` | ✅ | [src/fichas.txt](src/fichas.txt) — 520 registros (521 linhas com o cabeçalho) |
| 3 | Pelo menos 8 classes convertidas do `.TXT` em classes Java | ✅ | Ver [tabela de classes](#classes-do-dominio) abaixo (10 classes/enums) |
| 4 | Pelo menos 1 classe abstrata | ✅ | [Personagem.java:11](src/modelo/Personagem.java) e [Equipamento.java:11](src/modelo/Equipamento.java) |
| 5 | Encapsulamento (getters/setters, private/protected) | ✅ | Campos `private` + acessores em [Personagem.java:12](src/modelo/Personagem.java), [Equipamento.java:12](src/modelo/Equipamento.java); `setVida` protege invariante [Personagem.java:67](src/modelo/Personagem.java) |
| 6 | Exportar com `ObjectOutputStream` (serialização) | ✅ | `exportarDat` [FichaRepositorio.java:109](src/repositorio/FichaRepositorio.java) |
| 7 | Importar a base com `ObjectInputStream` (desserialização) | ✅ | `importarDat` [FichaRepositorio.java:122](src/repositorio/FichaRepositorio.java) |
| 8 | Pelo menos 2 classes de teste unitário | ✅ | [BatalhaTest.java](src/test/BatalhaTest.java) e [FichaRepositorioTest.java](src/test/FichaRepositorioTest.java) |
| 9 | Pelo menos 2 Enums | ✅ | [Classe.java:14](src/modelo/Classe.java), [Sexo.java:6](src/modelo/Sexo.java), [TipoMagia.java:9](src/modelo/TipoMagia.java) (3 enums) |
| 10 | Pelo menos 1 sobrecarga **ou** sobrescrita de método | ✅ | Sobrecarga: `atacar(Personagem, Arma)` [Heroi.java:59](src/modelo/Heroi.java) vs `atacar(Personagem)` [Combatente.java:17](src/combate/Combatente.java). Sobrescritas: `usarHabilidade`/`calcularDano`/`defender` em Heroi/Monstro |
| 11 | Pelo menos 1 método abstrato | ✅ | `usarHabilidade()` [Personagem.java:73](src/modelo/Personagem.java); `curar`/`somaMagiaNoAtaque`/`reduzirDano` [Classe.java:109](src/modelo/Classe.java) |
| 12 | Pelo menos 1 ordenação de registros (Comparable/Comparator) | ✅ | `ordenarPorVida` com `Comparator.comparingInt` [FichaRepositorio.java:101](src/repositorio/FichaRepositorio.java) |
| 13 | Pelo menos 4 implementações de igualdade (equals/hashCode) | ✅ | [Personagem.java:102](src/modelo/Personagem.java), [Heroi.java:77](src/modelo/Heroi.java), [Monstro.java:60](src/modelo/Monstro.java), [Equipamento.java:46](src/modelo/Equipamento.java) (4) |
| 14 | Pelo menos 5 blocos try/catch | ✅ | 7 blocos `try`: menu [GerenciadorMenu.java:45,55,65](src/app/menu/GerenciadorMenu.java); loop [Principal.java:30](src/app/Principal.java); repositório [FichaRepositorio.java:37,110,123](src/repositorio/FichaRepositorio.java) |
| 15 | Pelo menos 1 exceção custom (extends RuntimeException) usada na aplicação | ✅ | [FichaInvalidaException.java:8](src/repositorio/FichaInvalidaException.java) e [PersistenciaException.java:9](src/repositorio/PersistenciaException.java); lançadas em [FichaRepositorio.java:47,50,58,92,115,128](src/repositorio/FichaRepositorio.java) |
| 16 | Pelo menos 1 tipo de Collection (List, Set ou HashMap) | ✅ | `List<Personagem>` [FichaRepositorio.java:16](src/repositorio/FichaRepositorio.java); `Set<String>` [Magia.java:17](src/modelo/Magia.java) |

## Classes do domínio

As 10 classes/enums que modelam os dados lidos do `fichas.txt` (requisito #3):

| Classe | Tipo | Arquivo |
|--------|------|---------|
| `Personagem` | classe abstrata | [Personagem.java](src/modelo/Personagem.java) |
| `Heroi` | classe (extends Personagem, implements Combatente) | [Heroi.java](src/modelo/Heroi.java) |
| `Monstro` | classe (extends Personagem, implements Combatente) | [Monstro.java](src/modelo/Monstro.java) |
| `Equipamento` | classe abstrata | [Equipamento.java](src/modelo/Equipamento.java) |
| `Arma` | classe (extends Equipamento) | [Arma.java](src/modelo/Arma.java) |
| `Magia` | classe (extends Equipamento) | [Magia.java](src/modelo/Magia.java) |
| `Classe` | enum (Strategy) | [Classe.java](src/modelo/Classe.java) |
| `Sexo` | enum | [Sexo.java](src/modelo/Sexo.java) |
| `TipoMagia` | enum | [TipoMagia.java](src/modelo/TipoMagia.java) |
| `Combatente` | interface (regra de ataque default) | [Combatente.java](src/combate/Combatente.java) |

## Como verificar

Compilar e rodar os testes (sem Maven/Gradle — ver [CLAUDE.md](CLAUDE.md)):

```bash
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d out_test -cp "lib/junit-platform-console-standalone-1.10.2.jar" @sources.txt
java -jar "lib/junit-platform-console-standalone-1.10.2.jar" execute --class-path out_test --scan-class-path --details=tree
```

Conferências rápidas:
- **Registros do txt:** `wc -l src/fichas.txt` → 521 (520 + cabeçalho).
- **Requisito #2** é coberto pelo teste `lerTxtDeveCarregarNoMinimo500Fichas` [FichaRepositorioTest.java:27](src/test/FichaRepositorioTest.java).
- **Requisitos #6/#7** pelo teste `exportarEImportarDatDevePreservarABase` [FichaRepositorioTest.java:134](src/test/FichaRepositorioTest.java).
- **Requisito #12** pelo teste `ordenarPorVidaDeveOrdenarDaMenorParaAMaior` [FichaRepositorioTest.java:120](src/test/FichaRepositorioTest.java).
