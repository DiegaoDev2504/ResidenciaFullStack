public class Json {

    public static String extrairTexto(String jsonResposta) {
        if (jsonResposta == null) return null;

        // indexOf devolve a posição, ou -1 se não achar.
        // O 2º argumento é "comece a procurar a partir daqui".
        int posContent = jsonResposta.indexOf("\"content\"");
        if (posContent < 0) return null;

        int posParts = jsonResposta.indexOf("\"parts\"", posContent);
        if (posParts < 0) return null;

        int posText = jsonResposta.indexOf("\"text\"", posParts);
        if (posText < 0) return null;

        int posDoisPontos = jsonResposta.indexOf(':', posText);
        int posAspaAbre = jsonResposta.indexOf('"', posDoisPontos);
        if (posAspaAbre < 0) return null;

        // Percorre até a aspa que FECHA, desfazendo escapes pelo caminho.
        StringBuilder texto = new StringBuilder();
        int i = posAspaAbre + 1;

        while (i < jsonResposta.length()) {
            char atual = jsonResposta.charAt(i);

            if (atual == '\\' && i + 1 < jsonResposta.length()) {
                char proximo = jsonResposta.charAt(i + 1);
                switch (proximo) {
                    case 'n':
                        texto.append('\n');
                        i += 2;
                        break;
                    case 'r':
                        texto.append('\r');
                        i += 2;
                        break;
                    case 't':
                        texto.append('\t');
                        i += 2;
                        break;
                    case '"':
                        texto.append('"');
                        i += 2;
                        break;
                    case '\\':
                        texto.append('\\');
                        i += 2;
                        break;
                    case '/':
                        texto.append('/');
                        i += 2;
                        break;
                    case 'u':  // escape unicode: 4 dígitos hexadecimais viram 1 char
                        texto.append((char) Integer.parseInt(jsonResposta.substring(i + 2, i + 6), 16));
                        i += 6;
                        break;
                    default:
                        texto.append(proximo);
                        i += 2;
                }
            } else if (atual == '"') {
                break;              // aspa não escapada = fim da string
            } else {
                texto.append(atual);
                i++;
            }
        }
        return texto.toString();
    }

}

