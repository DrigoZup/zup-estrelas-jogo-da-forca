package br.com.zup.jogo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {

	
		public static int contaLinhas(String documento) throws IOException {
			LineNumberReader leitorDeLinhas = new LineNumberReader(new FileReader(documento));
			leitorDeLinhas.skip(Long.MAX_VALUE);
			int numeroDePalavras = leitorDeLinhas.getLineNumber() + 1;
			leitorDeLinhas.close();
			return numeroDePalavras;
		}

		public static void leituraDoArquivo(String documento, String[] palavras) throws IOException {
			BufferedReader lendoArquivo = new BufferedReader(new FileReader(documento));
			String linhaLida;
			int numeroDaLinha = 0;

			while ((linhaLida = lendoArquivo.readLine()) != null) {
				palavras[numeroDaLinha] = linhaLida;
				numeroDaLinha++;
			}
			lendoArquivo.close();
		}

		public static String sorteiaPalavra(int numeroDePalavrasParaSorteio, String[] palavras) {
			Random aleatorio = new Random();
			int sorteio = aleatorio.nextInt(numeroDePalavrasParaSorteio);
			String palavraSorteada = (palavras[sorteio]);
			return palavraSorteada;
		}

		public static final String INICIODOGAME=(

			"     ------- Bemm Vindo ao Jogo da Forca!!! ---------\n"
					+ "Pense bem antes de falar cada letra, seu pescoço está em jogo aqui\n\n"
					+ "Eis os espaços a serem preenchidos logo abaixo.\n");

		public static void resultadoDoGame(int chances, String palavraUsada) {
			if (chances > 0) {
				System.out.println("\n\nExatamente, " + palavraUsada + " era a palavra...\n"
						+ "Que droga, dessa vez você conseguiu salvar seu pescoço!\n"
						+ "Mas não fique muito animado, eu te pego na próxima!");
			} else {
				System.out.println("\n\nA palavra era " + palavraUsada + "! Você falhou miseravelmente!!!\n"
						+ "Soltem as cordas e degolem esse analfabeto!!!\n" + "HUAHAHAHAUAHAHAHAHAUUUUAHAHAHAHAHA...");
			}
		}

		public static void escondeLetras(int tamanhoArray, char[] arrayDaPalavra) {
			
			for (int i = 0; i < tamanhoArray; i++) {

				if (arrayDaPalavra[i] == 0) {
					System.out.print(" _ ");
				} else {
					System.out.print(" ");
				}
			}
		}
		
		public static void tentaAdivinharLetra(int tamanhoArray, char letra, String palavra, boolean chance, char [] confirmaLetra) {
			
			for (int i = 0; i < tamanhoArray; i++) {
				if (letra == palavra.charAt(i)) {
					confirmaLetra[i] = 1;
					chance = false;
				}
			}

		}
		
		public static void main(String[] args) throws IOException {

			Scanner teclado = new Scanner(System.in);
			String nomeDocumento = "palavras.txt";
			int numeroDePalavras = contaLinhas(nomeDocumento);
			String[] possiveisPalavrasUsadasInGame = new String[numeroDePalavras]; 
			leituraDoArquivo(nomeDocumento, possiveisPalavrasUsadasInGame);
			String palavraSorteada = sorteiaPalavra(numeroDePalavras, possiveisPalavrasUsadasInGame);
			int tamanhoPalavraSorteada = palavraSorteada.length();
			char[] acertouALetra = new char[tamanhoPalavraSorteada];
			
			
			for (int i = 0; i < acertouALetra.length; i++) {
				acertouALetra[i] = 0;
				if (palavraSorteada.charAt(i) == ' ') {
					acertouALetra[i] = 1;// mostra os espaços em palavras compostas;
				}
			} // Um array no mesmo tamanho da palavra sorteada q confere a letra digitada em
			 // todas as posições da palavra;

			escondeLetras(tamanhoPalavraSorteada, acertouALetra);

			char letra;
			boolean vitoria = false;
			int tentativas = 6;
			String letrasEscolhidas = "";

			System.out.println(INICIODOGAME); 
			do {

				System.out.println("\n\n" + "Você tem " + tentativas + " tentativas\n" + "Letras escolhidas: "
						+ letrasEscolhidas + "\n" + "Escolha uma letra!");

				letra = teclado.next().charAt(0);
				System.out.println("");

				letrasEscolhidas += " " + letra;

				boolean perdeChance = true;
				
				tentaAdivinharLetra(tamanhoPalavraSorteada, letra, palavraSorteada, perdeChance, acertouALetra);
				
				if (perdeChance) {
					tentativas--;
				}
				vitoria = true;
				
				for (int i = 0; i < tamanhoPalavraSorteada; i++) {

					if (acertouALetra[i] == 0) {
						System.out.print(" _ ");
						vitoria = false;
					} else {
						System.out.print(palavraSorteada.charAt(i));
					}
				}

			} while (!vitoria && tentativas > 0);

			resultadoDoGame(tentativas, palavraSorteada);

			teclado.close();
		}
}
