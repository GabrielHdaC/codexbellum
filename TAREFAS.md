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
- [ ] `importarDat` — `ObjectInputStream` + `FileInputStream`, `readObject` com cast
- [ ] `Batalha.executarTurno(Combatente, Combatente)` — atacante ataca, defensor
      defende. **Depende dos métodos do modelo (Parte 2)** — integrar quando o
      parceiro terminar
- [ ] `Principal` — menu no console com `Scanner`: carregar txt, listar fichas,
      ordenar por vida, exportar .dat, importar .dat, batalhar, sair.
      Try/catch pra opção inválida

**Aviso do flush:** o `ObjectOutputStream` precisa estar DENTRO dos parênteses
do try-with-resources (embrulhando o `FileOutputStream`), senão o final do
arquivo pode não ser gravado (buffer não descarregado).

## Parte 2 — Parceiro (`modelo`)

**Meta: deixar o `BatalhaTest` 100% verde.**

- [ ] `Personagem.receberDano(int dano)` — `vida -= dano` (sem ficar negativo)
- [ ] `Heroi.atacar(Personagem)` — causa o dano da arma equipada
- [ ] `Heroi.atacar(Personagem, Arma)` — sobrecarga: dano da arma passada
- [ ] `Heroi.defender(int dano)` — repassa pro `receberDano`
- [ ] `Heroi.usarHabilidade()` — efeito conforme a `Classe` (MAGO usa magia,
      CLERIGO cura, GUERREIRO dano extra, LADINO esquiva)
- [ ] `Monstro` — mesmos métodos, lógica de monstro
- [ ] Conferir os `equals` (Arma, Magia, Heroi, Monstro já têm — entender pra
      apresentação)

Obs.: o `implements Serializable` no `Personagem` já foi adicionado pelo
Gabriel (era necessário pro export da Parte 1) — não remover.

---

## Combinados

- Cada um trabalha numa branch (`parte-1` / `parte-2`) e faz merge na `main`
  quando os testes da sua parte passarem.
- Integração final: a opção "batalhar" do menu chama o `executarTurno`, que usa
  os métodos do modelo — testar junto no final.
- Apresentação (máx. 10 min): cada um explica a própria parte.
