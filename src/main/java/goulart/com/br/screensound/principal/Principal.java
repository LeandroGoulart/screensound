package goulart.com.br.screensound.principal;

import goulart.com.br.screensound.model.Artista;
import goulart.com.br.screensound.model.Musica;
import goulart.com.br.screensound.model.TipoArtista;
import goulart.com.br.screensound.repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository repositorio;

    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrando músicas");
        System.out.println("Deseja cadastrar músicas de que artista?: ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()) {
            System.out.println("Digite o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);

            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);

            repositorio.save(artista.get());
            System.out.println("Música cadastrada com sucesso!");
        } else {
            System.out.println("Artista não encontrado!");
        }

    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(System.out::println);
    }

    private void buscarMusicasPorArtista() {
    }

    private void pesquisarDadosDoArtista() {
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "s";

        while (cadastrarNovo.equalsIgnoreCase("s")) {

            System.out.println("Cadastrando artistas");
            System.out.println("Digite o nome do artista: ");
            var nome = leitura.nextLine();
            System.out.println("Digite o tipo do artista: ");
            var tipo = leitura.nextLine();
            System.out.println("Digite a nacionalidade do artista: ");
            var nacionalidade = leitura.nextLine();
            System.out.println("Digite o gênero do artista: ");
            var genero = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista, nacionalidade, genero);

            repositorio.save(artista);
            System.out.println("Artista cadastrado com sucesso!");
            System.out.println("Deseja cadastrar um novo artista? (s/n)");
            cadastrarNovo = leitura.nextLine();

        }
    }


}
