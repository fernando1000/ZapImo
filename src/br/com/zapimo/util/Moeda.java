package br.com.zapimo.util;

import java.math.BigDecimal;

public class Moeda {

	public String desenhaReaisComPontoEvirgula(String value, int qtdCasasDecimais, int divididoPor100) {

		BigDecimal parsed = null;

		StringBuilder stringBuilder = new StringBuilder();

		parsed = new BigDecimal(value).setScale(qtdCasasDecimais, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(divididoPor100), BigDecimal.ROUND_FLOOR);

		String parsedd = parsed.toString();

		String cleanStrin = parsedd.replace(".", ",");

		int tamanhoTotal = cleanStrin.length();

		int tamanhoAtehAvirgula = cleanStrin.substring(0, cleanStrin.indexOf(",")).length();
		
		stringBuilder.append(cleanStrin);

		if(tamanhoAtehAvirgula >= 4){

			stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 3).length() ), ".");
		}

		if(tamanhoAtehAvirgula >= 7){

			stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 5).length() ), ".");
		}
		return "R$ "+stringBuilder;
	}
	

}
