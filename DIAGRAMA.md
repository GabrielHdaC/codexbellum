# Diagrama UML — CodexBellum

Diagrama de classes completo do projeto, refletindo o código atual.
O GitHub renderiza este diagrama automaticamente nesta página.

```mermaid
classDiagram
    direction TB

    class Principal {
        +main(String[] args)$ void
        +exibirMenu() void
    }

    class FichaRepositorio {
        -List~Personagem~ fichas
        +getFichas() List~Personagem~
        +lerTxt(String caminho) void
        +ordenarPorVida() void
        +exportarDat(String caminho) void
        +importarDat(String caminho) void
    }

    class Exportavel {
        <<interface>>
        +exportarDat(String caminho) void
        +importarDat(String caminho) void
    }

    class FichaInvalidaException {
        +FichaInvalidaException(String mensagem)
    }

    class RuntimeException

    class Batalha {
        +executarTurno(Combatente atacante, Combatente defensor) void
    }

    class Combatente {
        <<interface>>
        +atacar(Personagem alvo) void
        +defender(int dano) void
    }

    class Serializable {
        <<interface>>
    }

    class Personagem {
        <<abstract>>
        -String nome
        -Sexo sexo
        -Arma arma
        -Magia magia
        -int vida
        +usarHabilidade()* void
        +receberDano(int dano) void
        +getters()
        +setters()
    }

    class Heroi {
        -Classe classe
        +usarHabilidade() void
        +atacar(Personagem alvo) void
        +atacar(Personagem alvo, Arma arma) void
        +defender(int dano) void
        +equals(Object o) boolean
        +hashCode() int
        +getters()
        +setters()
    }

    class Monstro {
        +usarHabilidade() void
        +atacar(Personagem alvo) void
        +defender(int dano) void
        +equals(Object o) boolean
    }

    class Arma {
        -String nome
        -int dano
        +equals(Object o) boolean
        +hashCode() int
        +getters()
        +setters()
    }

    class Magia {
        -String nome
        -int dano
        +equals(Object o) boolean
        +hashCode() int
        +getters()
        +setters()
    }

    class Sexo {
        <<enumeration>>
        M
        F
    }

    class Classe {
        <<enumeration>>
        GUERREIRO
        MAGO
        LADINO
        CLERIGO
    }

    Principal ..> FichaRepositorio : usa
    Principal ..> Batalha : usa
    FichaRepositorio ..|> Exportavel
    FichaRepositorio o-- Personagem : fichas
    FichaRepositorio ..> FichaInvalidaException : lança
    FichaInvalidaException --|> RuntimeException
    Batalha ..> Combatente : usa
    Personagem ..|> Serializable
    Heroi --|> Personagem
    Monstro --|> Personagem
    Heroi ..|> Combatente
    Monstro ..|> Combatente
    Personagem --> Sexo : usa
    Personagem o-- Arma : possui
    Personagem o-- Magia : pode ter
    Heroi --> Classe : usa
```

## Legenda das setas

| Seta | Significado |
|---|---|
| `--|>` (linha cheia, triângulo) | herança (`extends`) |
| `..|>` (linha tracejada, triângulo) | implementação de interface (`implements`) |
| `..>` (linha tracejada, aberta) | dependência (usa) |
| `o--` (losango) | agregação (tem um / tem vários) |
| `*` no método | método abstrato |
| `$` no método | método estático |
