package com.example.screenmatch;

import com.example.screenmatch.model.DadosEpisodio;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.services.ConverteDados;
import com.example.screenmatch.model.DadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.screenmatch.services.ConsumoAPI;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+flash&apikey=7d610144");

		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

		System.out.println(dadosSerie);

		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+flash&season=1&episode=1&apikey=7d610144");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporada = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+flash&season=" + i + "&apikey=7d610144");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporada.add(dadosTemporada);
		}
		temporada.forEach(System.out::println);
	}
}
