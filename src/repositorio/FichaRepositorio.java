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
                String[] campos = linha.split(";");

                if (campos.length != 6) {
                    throw new FichaInvalidaException("Ficha invalida: " + linha);
                }

                // A decisão Heroi/Monstro vem ANTES do valueOf: MONSTRO não
                // existe no enum Classe, então não pode passar pela conversão
                Personagem personagem;
                if (campos[2].equals("MONSTRO")) {
                    personagem = new Monstro();
                } else {
                    Heroi heroi = new Heroi();
                    heroi.setClasse(Classe.valueOf(campos[2]));
                    personagem = heroi;
                }

                personagem.setNome(campos[0]);
                personagem.setSexo(Sexo.valueOf(campos[1]));

                // A arma vem como "Nome:dano" — segundo split separa as partes
                String[] dadosArma = campos[3].split(":");
                Arma arma = new Arma();
                arma.setNome(dadosArma[0]);
                arma.setDano(Integer.parseInt(dadosArma[1]));
                personagem.setArma(arma);

                // Guerreiros e ladinos têm o campo de magia vazio (;;) —
                // sem este if, o split/parseInt explodiria na string vazia
                if (!campos[4].isEmpty()) {
                    String[] dadosMagia = campos[4].split(":");
                    Magia magia = new Magia();
                    magia.setNome(dadosMagia[0]);
                    magia.setDano(Integer.parseInt(dadosMagia[1]));
                    personagem.setMagia(magia);
                }

                personagem.setVida(Integer.parseInt(campos[5]));

                fichas.add(personagem);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Traduz o erro técnico (parseInt/valueOf) para a exceção do domínio
            throw new FichaInvalidaException("Ficha com campo invalido: " + e.getMessage());
        }
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
            System.out.println("Base exportada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
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
            System.out.println("Base importada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao importar o arquivo: " + e.getMessage());
        }
    }
}
