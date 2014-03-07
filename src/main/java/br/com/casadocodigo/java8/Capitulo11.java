package br.com.casadocodigo.java8;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.function.*;

class Type {
	MUSIC, VIDEO, IMAGE;
}

class Product {
	BigDecimal price;
	String name;
	String file;
}

class Payment {
	List<Product> products;
	DateTime

}

class Subscription {
	BigDecimal monthlyFee;
	DateTime begin;
	Optional<DateTime> end;

}


class Capitulo11 {

	private static long total = 0;

	public static void main (String... args) throws Exception 	{


		LongStream lines = 
			Files.list(Paths.get("./br/com/casadocodigo/java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.mapToLong(p -> lines(p).count());

		List<Long> lines2 = 
			Files.list(Paths.get("./br/com/casadocodigo/java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.map(p -> lines(p).count())
				.collect(Collectors.toList());	

		{
			Map<Path, Long> linesPerFile =  new HashMap<>();
			Files.list(Paths.get("./br/com/casadocodigo/java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.forEach(p -> 
					linesPerFile.put(p, lines(p).count()));
			System.out.println(linesPerFile);
				
		}
		Map<Path, Long> linesPerFile = 
			Files.list(Paths.get("./br/com/casadocodigo/java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(
						Function.identity(), 
						p -> lines(p).count()));

		System.out.println(linesPerFile);



		Map<Path, List<String>> content = 
			Files.list(Paths.get("./br/com/casadocodigo/java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(
						p -> p, 
						p -> lines(p).collect(Collectors.toList())));




		Usuario user1 = new Usuario("Paulo Silveira", 150, true);
		Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
		Usuario user3 = new Usuario("Guilherme Silveira", 90);
		Usuario user4 = new Usuario("Sergio Lopes", 120);
		Usuario user5 = new Usuario("Adriano Almeida", 100);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);

		Map<String, Usuario> nameToUser = usuarios
			.stream()
			.collect(Collectors.toMap(
						Usuario::getNome, 
						Function.identity()));
		System.out.println(nameToUser);


		Map<Integer, List<Usuario>> pontuacaoVelha = new HashMap<>();
		
		for(Usuario u: usuarios) {
			if(!pontuacaoVelha.containsKey(u.getPontos())) {
				pontuacaoVelha.put(u.getPontos(), new ArrayList<>());
			}
			pontuacaoVelha.get(u.getPontos()).add(u);
		}

		System.out.println(pontuacaoVelha);		

		Map<Integer, List<Usuario>> pontuacaoJ8 = new HashMap<>();
		
		for(Usuario u: usuarios) {
			pontuacaoJ8
				.computeIfAbsent(u.getPontos(), user -> new ArrayList<>())
				.add(u);
		}

		System.out.println(pontuacaoJ8);		


		Map<Integer, List<Usuario>> pontuacao = usuarios
			.stream()
			.collect(Collectors.groupingBy(Usuario::getPontos));

		System.out.println(pontuacao);

		Map<Boolean, List<Usuario>> moderadores = usuarios
		 	.stream()
		 	.collect(Collectors.partitioningBy(Usuario::isModerador));

		System.out.println(moderadores);

		Map<Boolean, Integer> pontuacaoPorTipo = usuarios
		 	.stream()
            .collect(Collectors.partitioningBy(u -> u.isModerador(),
            	Collectors.summingInt(Usuario::getPontos)));

		System.out.println(pontuacaoPorTipo);

		Map<Boolean, List<String>> nomesPorTipo = usuarios
		 	.stream()
            .collect(Collectors.partitioningBy(u -> u.isModerador(),
            	Collectors.mapping(Usuario::getNome, Collectors.toList())));

		System.out.println(nomesPorTipo);

		long sum = 
			LongStream.range(0, 1_000_000_000)
			.parallel()
			.sum();
		System.out.println(sum);

		// nao faca:
		LongStream.range(0, 1_000_000_000)
			.parallel()
			.forEach(n -> total += n);
		System.out.println(total);

	}

	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
