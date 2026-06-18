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
    public void atacarDeveAplicarADefesaDoAlvo() {
        Heroi heroi = criarHeroi();      // guerreiro, arma com dano 7
        Monstro monstro = criarMonstro();
        monstro.setVida(51);

        heroi.atacar(monstro);           // monstro defende 80%: ceil(7 * 0.8) = 6

        assertEquals(51 - 6, monstro.getVida(),
                "atacar passa pela defesa do alvo, igual ao turno da Batalha");
    }

    @Test
    public void atacarComArmaDeveUsarAArmaRecebidaEAplicarADefesa() {
        Heroi heroi = criarHeroi();
        Monstro monstro = criarMonstro();
        monstro.setVida(51);

        heroi.atacar(monstro, heroi.getArma());  // Espada Longa (7); monstro defende 80%

        // a sobrecarga usa a arma recebida e o alvo ainda aplica a própria defesa:
        // ceil(7 * 0.8) = 6
        assertEquals(51 - 6, monstro.getVida(),
                "a sobrecarga usa a arma recebida e o monstro ainda aplica sua defesa");
    }

    @Test
    public void receberDanoNaoDeveDeixarVidaNegativa() {
        Monstro monstro = criarMonstro();
        monstro.setVida(10);

        monstro.receberDano(999);

        assertEquals(0, monstro.getVida(), "a vida nunca fica negativa");
        assertFalse(monstro.estaVivo());
    }

    @Test
    public void defesaDeCadaClasseDeveReduzirODanoConformeAClasse() {
        assertEquals(100 - 5, defenderComVida100(Classe.GUERREIRO, 10),
                "guerreiro corta pela metade: 10 / 2 = 5");
        assertEquals(100 - 9, defenderComVida100(Classe.MAGO, 10),
                "mago absorve pouco: ceil(10 * 0.9) = 9");
        assertEquals(100 - 7, defenderComVida100(Classe.LADINO, 10),
                "ladino esquiva: ceil(10 * 0.7) = 7");
        assertEquals(100 - 8, defenderComVida100(Classe.CLERIGO, 10),
                "clerigo: ceil(10 * 0.8) = 8");
    }

    private int defenderComVida100(Classe classe, int dano) {
        Heroi heroi = criarHeroi();
        heroi.setClasse(classe);
        heroi.setVida(100);
        heroi.defender(dano);
        return heroi.getVida();
    }

    @Test
    public void monstroDeveAbsorver20PorCentoDoDanoAoDefender() {
        Monstro monstro = criarMonstro();
        monstro.setVida(100);

        monstro.defender(10);   // ceil(10 * 0.8) = 8

        assertEquals(100 - 8, monstro.getVida());
    }

    @Test
    public void magoDeveSomarAMagiaNoCalculoDeDano() {
        Heroi mago = new Heroi();
        mago.setClasse(Classe.MAGO);
        mago.setArma(new Arma("Cajado", 4));
        mago.setMagia(new Magia("Bola de Fogo", 8));

        assertEquals(4 + 8, mago.calcularDano(),
                "mago canaliza a magia: arma (4) + magia (8)");
    }

    @Test
    public void monstroComMagiaDeveRegenerarMetadeDoDanoDaMagia() {
        Monstro monstro = criarMonstro();
        monstro.setMagia(new Magia("Sopro Sombrio", 9));
        monstro.setVida(30);

        monstro.usarHabilidade();   // +max(1, 9 / 2) = 4

        assertEquals(34, monstro.getVida());
    }

    @Test
    public void monstroSemMagiaDeveRegenerarDoisDeVida() {
        Monstro monstro = criarMonstro();   // criado sem magia
        monstro.setVida(30);

        monstro.usarHabilidade();

        assertEquals(32, monstro.getVida());
    }

    @Test
    public void armasComMesmoNomeEDanoDevemSerIguais() {
        Arma a = new Arma("Espada", 7);
        Arma b = new Arma("Espada", 7);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Arma("Espada", 8));
    }

    @Test
    public void magiasComMesmoNomeEDanoDevemSerIguais() {
        Magia a = new Magia("Cura", 5);
        Magia b = new Magia("Cura", 5);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Magia("Cura", 9));
    }

    @Test
    public void heroiEMonstroComMesmosCamposNaoDevemSerIguais() {
        Arma arma = new Arma("Lamina", 5);
        Heroi heroi = new Heroi("Eco", Sexo.M, arma, null, 30, Classe.LADINO);
        Monstro monstro = new Monstro("Eco", Sexo.M, arma, null, 30);

        assertNotEquals(heroi, monstro, "tipos diferentes nunca são iguais (getClass)");
        assertNotEquals(monstro, heroi, "a igualdade tem de ser simétrica");
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

        assertEquals(7 + 11, clerigo.calcularDano(),
                "Martelo da Fe (7) + Punicao Divina (11) somam no golpe");
    }

    @Test
    public void clerigoComMagiaDeCuraNaoDeveSomarMagiaAoGolpe() {
        Heroi clerigo = criarClerigo("Maca Sagrada", 6, "Luz Curativa", 9);

        assertEquals(6, clerigo.calcularDano(),
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
