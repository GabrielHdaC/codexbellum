# Divisão do Trabalho Final — CodexBellum

Trabalho em dupla, dividido por **pacotes**:

| Quem | Pacotes | Classe de teste (gabarito) |
|---|---|---|
| Gabriel | `app`, `combate`, `repositorio` | `FichaRepositorioTest` + teste do turno no `BatalhaTest` |
| Parceiro | `modelo` | `BatalhaTest` (ataque, dano, equals) |

As classes de teste em `src/test` **já estão prontas** — ninguém precisa mexer
nelas. Elas são o gabarito: sua parte acabou quando os testes dela passarem.

**Rodar os testes:** botão verde na classe de teste no IntelliJ, ou:

```
javac -cp lib\junit-platform-console-standalone-1.10.2.jar -d out (gci -r src -filter *.java).FullName
java -jar lib\junit-platform-console-standalone-1.10.2.jar execute --class-path out --scan-class-path
```

---

## Parte 1 — Gabriel (`app`, `combate`, `repositorio`)

- [x] `lerTxt` — lê o .txt, converte pra objetos, lança `FichaInvalidaException`
- [x] `ordenarPorVida` — ordena a lista por vida (menor → maior)
- [x] `implements Serializable` em Personagem, Arma e Magia
- [ ] `exportarDat` — quase pronto (ver aviso do flush abaixo)
- [x] `importarDat` — `ObjectInputStream` + `FileInputStream`, `readObject` com cast
- [x] `Batalha.executarTurno(Combatente, Combatente)` — atacante ataca o defensor
- [x] case 6 do menu — escolhe dois lutadores pelo número e executa o turno
- [x] `Principal` — menu no console com `Scanner`: carregar txt, listar fichas,
      ordenar por vida, exportar .dat, importar .dat, batalhar, sair.
      Try/catch pra opção inválida

**Aviso do flush:** o `ObjectOutputStream` precisa estar DENTRO dos parênteses
do try-with-resources (embrulhando o `FileOutputStream`), senão o final do
arquivo pode não ser gravado (buffer não descarregado).

## Parte 2 — Parceiro (`modelo`)

**Meta: deixar o `BatalhaTest` 100% verde.**

- [x] `Personagem.receberDano(int dano)` — `vida -= dano` (sem ficar negativo)
- [x] `Heroi.atacar(Personagem)` — causa o dano da arma equipada
- [x] `Heroi.atacar(Personagem, Arma)` — sobrecarga: dano da arma passada
- [x] `Heroi.defender(int dano)` — reduz o dano conforme a classe
- [x] `Heroi.usarHabilidade()` — cura conforme a `Classe`
- [x] `Monstro` — mesmos métodos, lógica de monstro
- [x] `equals` em Arma, Magia, Heroi, Monstro e Personagem

Obs. da integração (12/06/2026): o modelo do parceiro foi integrado a partir de
um zip — ele tinha recriado o projeto do zero, então foi preciso readicionar o
`implements Serializable` (Personagem, Arma, Magia) e o `implements Combatente`
(Heroi, Monstro). Da próxima vez: clonar o repositório e trabalhar numa branch.

---

## Combinados

- Cada um trabalha numa branch (`parte-1` / `parte-2`) e faz merge na `main`
  quando os testes da sua parte passarem.
- Integração final: a opção "batalhar" do menu chama o `executarTurno`, que usa
  os métodos do modelo — testar junto no final.
- Apresentação (máx. 10 min): cada um explica a própria parte.
