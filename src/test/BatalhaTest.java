package test;

import combate.Batalha;
import modelo.Arma;
import modelo.Classe;
import modelo.Heroi;
import modelo.Magia;
import modelo.Monstro;
import modelo.Sexo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de unidade do combate: turno da batalha, dano recebido,
 * sobrecarga do atacar e igualdade de heróis.
 */
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

    private Heroi criarClerigo(String nomeArma, int danoArma, String nomeMagia, int danoMagia) {
        Arma arma = new Arma();
        arma.setNome(nomeArma);
        arma.setDano(danoArma);

        Magia magia = new Magia();
        magia.setNome(nomeMagia);
        magia.setDano(danoMagia);

        Heroi clerigo = new Heroi();
        clerigo.setNome("Anya Pelegris");
        clerigo.setSexo(Sexo.F);
        clerigo.setClasse(Classe.CLERIGO);
        clerigo.setArma(arma);
        clerigo.setMagia(magia);
        clerigo.setVida(52);
        return clerigo;
    }

    @Test
    public void clerigoComMagiaDeCuraDeveCurarBaseMaisODanoDaMagia() {
        Heroi clerigo = criarClerigo("Maca Sagrada", 6, "Luz Curativa", 9);
        clerigo.setVida(40);

        clerigo.usarHabilidade();

        assertEquals(40 + 15 + 9, clerigo.getVida(),
                "clerigo cura 15 de base + 9 da Luz Curativa");
    }

    @Test
    public void clerigoComMagiaOfensivaDeveSomarODanoDaMagiaAoGolpe() {
        Heroi clerigo = criarClerigo("Martelo da Fe", 7, "Punicao Divina", 11);
        Monstro alvo = criarMonstro();
        alvo.setVida(51);

        clerigo.atacar(alvo);

        assertEquals(51 - (7 + 11), alvo.getVida(),
                "Martelo da Fe (7) + Punicao Divina (11) somam no golpe");
    }

    @Test
    public void clerigoComMagiaDeCuraNaoDeveSomarMagiaAoGolpe() {
        Heroi clerigo = criarClerigo("Maca Sagrada", 6, "Luz Curativa", 9);
        Monstro alvo = criarMonstro();
        alvo.setVida(51);

        clerigo.atacar(alvo);

        assertEquals(51 - 6, alvo.getVida(),
                "magia de cura nao entra no ataque: so o dano da arma (6)");
    }

    @Test
    public void executarTurnoDeveAplicarADefesaDoDefensor() {
        Heroi atacante = criarHeroi();   // guerreiro, arma com dano 7
        Heroi defensor = criarHeroi();   // guerreiro: defende reduzindo pela metade
        defensor.setVida(58);

        new Batalha().executarTurno(atacante, defensor);

        // guerreiro corta o golpe pela metade: 7 / 2 = 3 (divisão inteira)
        assertEquals(58 - 3, defensor.getVida(),
                "no turno o defensor aplica a propria defesa, nao leva o dano cheio");
    }

    @Test
    public void monstroDeveDecidirCurarQuandoAVidaEstaBaixa() {
        Monstro monstro = criarMonstro();

        monstro.setVida(15);
        assertTrue(monstro.deveUsarHabilidade(), "com vida baixa o monstro deve se curar");

        monstro.setVida(50);
        assertFalse(monstro.deveUsarHabilidade(), "com vida alta o monstro deve atacar");
    }

    @Test
    public void estaVivoDeveAcompanharAVida() {
        Heroi heroi = criarHeroi();

        heroi.setVida(1);
        assertTrue(heroi.estaVivo());

        heroi.setVida(0);
        assertFalse(heroi.estaVivo());
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
