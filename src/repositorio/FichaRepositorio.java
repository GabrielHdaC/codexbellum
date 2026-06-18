package repositorio;

import modelo.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Guarda a base de fichas (heróis e monstros) e cuida da persistência:
 * leitura do arquivo texto, ordenação e exportação/importação binária (.dat).
 */
public class FichaRepositorio implements Exportavel {

    private List<Personagem> fichas = new ArrayList<>();

    /**
     * Devolve as fichas em uma cópia defensiva: quem chama pode ler e iterar,
     * mas não consegue alterar a lista interna do repositório (encapsulamento).
     * A cópia é rasa de propósito: os personagens dentro são os mesmos objetos,
     * pois a batalha precisa alterar a vida deles.
     *
     * @return cópia da lista de fichas
     */
    public List<Personagem> getFichas() {
        return new ArrayList<>(fichas);
    }

    /**
     * Lê o arquivo texto (formato nome;sexo;classe;arma;magia;vida) e converte
     * cada linha em um Heroi ou Monstro dentro da lista.
     *
     * @param caminho caminho do arquivo .txt
     * @throws FichaInvalidaException se alguma linha estiver fora do formato
     */
    public void lerTxt(String caminho) {
        // Esvazia antes de carregar: chamar duas vezes não duplica a base
        fichas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine(); // descarta o cabeçalho (nome;sexo;classe;...)

            String linha;
            while ((linha = br.readLine()) != null) {
                fichas.add(lerFicha(linha));
            }
        } catch (IOException e) {
            // Não imprime aqui: a persistência só sinaliza o erro; quem avisa o
            // usuário é a camada de menu
            throw new PersistenciaException("Erro ao ler o arquivo: " + caminho, e);
        } catch (IllegalArgumentException e) {
            // Traduz o erro técnico (parseInt/valueOf) para a exceção do domínio
            throw new FichaInvalidaException("Ficha com campo invalido: " + e.getMessage());
        }
    }

    /**
     * Converte uma linha do arquivo em um Heroi ou Monstro já completo.
     *
     * @param linha linha no formato nome;sexo;classe;arma;magia;vida
     * @return personagem montado a partir da linha
     * @throws FichaInvalidaException se a linha estiver fora do formato esperado
     */
    private Personagem lerFicha(String linha) {
        String[] campos = linha.split(";");
        if (campos.length != 6) {
            throw new FichaInvalidaException("Ficha invalida: " + linha);
        }

        Sexo sexo = Sexo.valueOf(campos[1]);

        String[] dadosArma = separarNomeEDano(campos[3], linha);
        Arma arma = new Arma(dadosArma[0], Integer.parseInt(dadosArma[1]));

        // Guerreiros e ladinos têm o campo de magia vazio (;;) — sem magia equipada
        Magia magia = null;
        if (!campos[4].isEmpty()) {
            String[] dadosMagia = separarNomeEDano(campos[4], linha);
            magia = new Magia(dadosMagia[0], Integer.parseInt(dadosMagia[1]));
        }

        int vida = Integer.parseInt(campos[5]);

        // A decisão Heroi/Monstro vem ANTES do valueOf: MONSTRO não existe no
        // enum Classe, então não pode passar pela conversão
        if (campos[2].equals("MONSTRO")) {
            return new Monstro(campos[0], sexo, arma, magia, vida);
        }
        return new Heroi(campos[0], sexo, arma, magia, vida, Classe.valueOf(campos[2]));
    }

    /**
     * Quebra um campo "Nome:dano" (arma ou magia) em [nome, dano], validando o
     * formato — sem este split protegido, uma arma escrita só como "Espada"
     * estouraria um ArrayIndexOutOfBoundsException não tratado.
     *
     * @param texto campo bruto (ex.: "Espada Longa:7")
     * @param linha linha inteira, só para compor a mensagem de erro
     * @return vetor de duas posições: nome e dano (ainda como texto)
     * @throws FichaInvalidaException se faltar o ":" ou o valor do dano
     */
    private String[] separarNomeEDano(String texto, String linha) {
        String[] dados = texto.split(":");
        if (dados.length != 2) {
            throw new FichaInvalidaException("Equipamento invalido na ficha: " + linha);
        }
        return dados;
    }

    /**
     * Ordena a base da menor para a maior vida.
     */
    public void ordenarPorVida() {
        // Personagem::getVida é um method reference: "compare pelo valor
        // que o getVida de cada personagem devolver"
        fichas.sort(Comparator.comparingInt(Personagem::getVida));
    }

    /**
     * Grava a base inteira em arquivo binário.
     * Um único writeObject basta: o ArrayList serializa junto todos os
     * personagens (e as armas e magias penduradas em cada um).
     *
     * @param caminho caminho do arquivo .dat a criar
     */
    @Override
    public void exportarDat(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(fichas);
        } catch (IOException e) {
            // Antes este erro era engolido (só imprimia): agora a falha sobe e o
            // menu sabe que a exportação não deu certo
            throw new PersistenciaException("Erro ao exportar para " + caminho, e);
        }
    }

    /**
     * Restaura a base a partir de um arquivo binário gravado pelo exportarDat.
     *
     * @param caminho caminho do arquivo .dat a ler
     */
    @Override
    @SuppressWarnings("unchecked")
    public void importarDat(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            // O readObject devolve Object genérico — o cast avisa o compilador
            // que ali dentro há uma List de Personagem (fomos nós que gravamos)
            fichas = (List<Personagem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Erro ao importar de " + caminho, e);
        }
    }
}
