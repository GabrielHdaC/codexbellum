package modelo;

/**
 * Classes jogáveis dos heróis. Cada constante carrega o próprio comportamento
 * (Strategy via enum): quanto cura, se a magia entra no golpe e quanto reduz de
 * dano na defesa. Assim, adicionar uma classe nova é só acrescentar uma
 * constante aqui — o Heroi não precisa ser tocado (princípio Aberto/Fechado).
 *
 * Monstros não têm classe — por isso o valor MONSTRO do arquivo não está aqui:
 * na leitura ele vira um objeto Monstro.
 */
public enum Classe {
    GUERREIRO {
        @Override
        public int curar(Magia magia) {
            return 10;
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return false;
        }

        @Override
        public int reduzirDano(int dano) {
            return dano / 2; // absorve metade
        }
    },
    MAGO {
        @Override
        public int curar(Magia magia) {
            return 5;
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return magia != null; // canaliza qualquer magia no golpe
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.9);
        }
    },
    LADINO {
        @Override
        public int curar(Magia magia) {
            return 3;
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            return false;
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.7); // esquiva 30%
        }
    },
    CLERIGO {
        @Override
        public int curar(Magia magia) {
            // cura divina base; uma magia de cura reforça a recuperação
            int cura = 15;
            if (magia != null && magia.getTipo() == TipoMagia.CURA) {
                cura += magia.getDano();
            }
            return cura;
        }

        @Override
        public boolean somaMagiaNoAtaque(Magia magia) {
            // só magia ofensiva (ex.: Punição Divina) entra no golpe;
            // a de cura fica reservada para a habilidade
            return magia != null && magia.getTipo() == TipoMagia.DANO;
        }

        @Override
        public int reduzirDano(int dano) {
            return (int) Math.ceil(dano * 0.8);
        }
    };

    /**
     * Quanto de vida a habilidade recupera, podendo depender da magia equipada.
     *
     * @param magia magia equipada do herói (pode ser null)
     * @return pontos de vida a recuperar
     */
    public abstract int curar(Magia magia);

    /**
     * Diz se a magia equipada soma seu dano ao golpe desta classe.
     *
     * @param magia magia equipada do herói (pode ser null)
     * @return true se a magia entra no cálculo do dano
     */
    public abstract boolean somaMagiaNoAtaque(Magia magia);

    /**
     * Aplica a redução de defesa desta classe sobre um dano recebido.
     *
     * @param dano dano bruto do golpe
     * @return dano já reduzido pela defesa da classe
     */
    public abstract int reduzirDano(int dano);
}
