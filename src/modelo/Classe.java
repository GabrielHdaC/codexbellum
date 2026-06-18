package modelo;

/**
 * O que é: as classes jogáveis dos heróis (guerreiro, mago, ladino, clérigo).
 * O que faz: cada constante carrega o próprio comportamento — quanto cura na
 * habilidade, se a magia entra no golpe e quanto reduz de dano ao defender.
 * Por que assim: é o padrão Strategy feito com enum. Cada classe "sabe se virar"
 * sozinha, então criar uma classe nova é só acrescentar uma constante aqui, sem
 * tocar no Heroi (princípio Aberto/Fechado: aberto a extensão, fechado a mudança).
 *
 * Obs.: monstro não tem classe — por isso não existe constante MONSTRO aqui; na
 * leitura do txt o valor "MONSTRO" vira um objeto Monstro, não um Heroi.
 */
public enum Classe {

    // GUERREIRO — o tanque: cura razoável, ignora magia no golpe e é quem mais
    // aguenta pancada (corta o dano recebido pela metade).
    GUERREIRO {
        @Override
        public int curar(Magia magia) {
            return 10; // cura fixa, não depende de magia
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return false; // guerreiro não conjura: só o dano da arma conta
        }

        @Override
        public int reduzirDano(int dano) {
            return dano / 2; // defesa forte: absorve metade do golpe
        }
    },

    // MAGO — o canalizador: cura fraca, mas soma QUALQUER magia ao golpe; em
    // troca, quase não tem defesa.
    MAGO {
        @Override
        public int curar(Magia magia) {
            return 5; // cura baixa
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return magia != null; // canaliza qualquer magia equipada no golpe
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.9); // defesa fraca: evita só 10%
        }
    },

    // LADINO — o esquivo: a menor cura, não usa magia, mas desvia boa parte do
    // golpe.
    LADINO {
        @Override
        public int curar(Magia magia) {
            return 3; // menor cura entre as classes
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return false; // ladino não conjura: só a arma
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.7); // esquiva: evita 30% do dano
        }
    },

    // CLERIGO — o suporte: melhor cura do jogo (a magia de cura ainda reforça) e
    // só leva magia OFENSIVA para o ataque; defesa mediana.
    CLERIGO {
        @Override
        public int curar(Magia magia) {
            // cura divina base; se a magia equipada for de cura, soma o dano dela
            int cura = 15;
            if (magia != null && magia.getTipo() == TipoMagia.CURA) {
                cura += magia.getDano();
            }
            return cura;
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            // só magia ofensiva (ex.: Punição Divina) entra no golpe;
            // a magia de cura fica reservada para a habilidade
            return magia != null && magia.getTipo() == TipoMagia.DANO;
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.8); // defesa média: evita 20%
        }
    };

    /**
     * O que é: a cura da habilidade desta classe.
     * O que faz: devolve quantos pontos de vida o herói recupera ao usar a
     * habilidade — o valor pode depender da magia equipada (caso do clérigo).
     * Por que assim: é um método abstrato; cada constante acima dá a sua resposta,
     * então a regra de cura de cada classe fica num lugar só.
     *
     * @param magia magia equipada do herói (pode ser null)
     * @return pontos de vida a recuperar
     */
    public abstract int curar(Magia magia);

    /**
     * O que é: a regra de "essa magia entra no golpe?" desta classe.
     * O que faz: responde se o dano da magia equipada deve ser somado ao ataque.
     * Por que assim: separa quem conjura (mago/clérigo) de quem não conjura
     * (guerreiro/ladino) sem espalhar ifs pelo Heroi.
     *
     * @param magia magia equipada do herói (pode ser null)
     * @return true se a magia entra no cálculo do dano
     */
    public abstract boolean somaMagiaNoAtaque(Magia magia);

    /**
     * O que é: a defesa desta classe.
     * O que faz: recebe o dano bruto de um golpe e devolve quanto realmente passa,
     * já descontada a redução da classe.
     * Por que assim: cada classe defende de um jeito (guerreiro corta metade,
     * ladino esquiva 30%...); manter aqui evita um switch espalhado no Heroi.
     *
     * @param dano dano bruto do golpe
     * @return dano já reduzido pela defesa da classe
     */
    public abstract int reduzirDano(int dano);
}
