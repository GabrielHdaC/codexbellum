package test;

import combate.Batalha;
import modelo.Arma;
import modelo.Classe;
import modelo.Heroi;
import modelo.Monstro;
import modelo.Sexo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BatalhaTest {

    private Heroi criarHeroi() {
        Arma espada = new Arma();
        espada.setNome("Espada Longa");
        espada.setDano(7);

        Heroi heroi = new Heroi();
        heroi.setNome("Cedric Laminar");
        heroi.setSexo(Sexo.M);
        heroi.setClasse(Classe.GUERREIRO);
        heroi.setArma(espada);
        heroi.setVida(58);
        return heroi;
    }

    private Monstro criarMonstro() {
        Arma garra = new Arma();
        garra.setNome("Garra Afiada");
        garra.setDano(7);

        Monstro monstro = new Monstro();
        monstro.setNome("Goblin das Ruinas");
        monstro.setSexo(Sexo.M);
        monstro.setArma(garra);
        monstro.setVida(51);
        return monstro;
    }

    @Test
    public void executarTurnoDeveCausarDanoNoDefensor() {
        Heroi heroi = criarHeroi();
        Monstro monstro = criarMonstro();
        int vidaInicial = monstro.getVida();

        new Batalha().executarTurno(heroi, monstro);

        assertTrue(monstro.getVida() < vidaInicial,
                "depois do turno o defensor deve ter menos vida que antes");
    }

    @Test
    public void receberDanoDeveDiminuirAVida() {
        Monstro monstro = criarMonstro();
        monstro.setVida(51);

        monstro.receberDano(10);

        assertEquals(41, monstro.getVida());
    }

    @Test
    public void atacarComArmaDeveCausarODanoDaArma() {
        Heroi heroi = criarHeroi();
        Monstro monstro = criarMonstro();
        monstro.setVida(51);

        heroi.atacar(monstro, heroi.getArma());

        assertEquals(51 - 7, monstro.getVida(),
                "o ataque com Espada Longa (dano 7) deve tirar 7 de vida");
    }

    @Test
    public void heroisComAMesmaClasseDevemSerIguais() {
        Heroi a = criarHeroi();
        Heroi b = criarHeroi();
        assertEquals(a, b);

        b.setClasse(Classe.MAGO);
        assertNotEquals(a, b);
    }
}
