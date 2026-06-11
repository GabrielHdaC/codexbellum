package repositorio;

import modelo.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FichaRepositorio implements Exportavel {

    private List<Personagem> fichas = new ArrayList<>();

    public List<Personagem> getFichas() {
        return new ArrayList<>(fichas);
    }

    public void lerTxt(String caminho) {
        fichas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                if (campos.length != 6) {
                    throw new FichaInvalidaException("Ficha invalida: " + linha);
                }

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

                String[] dadosArma = campos[3].split(":");
                Arma arma = new Arma();
                arma.setNome(dadosArma[0]);
                arma.setDano(Integer.parseInt(dadosArma[1]));
                personagem.setArma(arma);

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
            throw new FichaInvalidaException("Ficha com campo invalido: " + e.getMessage());
        }
    }

    public void ordenarPorVida() {
        fichas.sort(Comparator.comparingInt(Personagem::getVida));
    }

    @Override
    public void exportarDat(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(fichas);
            System.out.println("Base exportada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void importarDat(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            fichas = (List<Personagem>) ois.readObject();
            System.out.println("Base importada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao importar o arquivo: " + e.getMessage());
        }
    }
}
