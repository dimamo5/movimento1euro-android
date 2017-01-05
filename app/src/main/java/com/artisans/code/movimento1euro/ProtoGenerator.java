package com.artisans.code.movimento1euro;

import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.Election;
import com.artisans.code.movimento1euro.models.PastCause;
import com.artisans.code.movimento1euro.models.VotingCause;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Duart on 05/01/2017.
 */

public class ProtoGenerator {

    public static List<Cause> generateVotingCauses(){
        JSONObject obj = null;
        List<Cause> causesList= new ArrayList<>();

        try {
            obj = new JSONObject("{\"votacao\":[\n" +
                    "  {\n" +
                    "   \"id\": 125,\n" +
                    "   \"titulo\": \"Outubro 2016\",\n" +
                    "   \"data_de_inicio\": \"20-09-2016 00:00:00\",\n" +
                    "   \"data_de_fim\": \"21-11-2016 23:59:59\",\n" +
                    "   \"montante_disponivel\": \"797\",\n" +
                    "   \"total_votos\": \"1\",\n" +
                    "   \"utilizador_ja_votou\": false,\n" +
                    "   \"causas\": [\n" +
                    "     {\n" +
                    "       \"id\": 130,\n" +
                    "       \"titulo\": \"Higiene para todos\",\n" +
                    "       \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                    "       \"verba\": \"750\",\n" +
                    "       \"votos\": \"0\",\n" +
                    "       \"voto_utilizador\": false,\n" +
                    "       \"associacao\": {\n" +
                    "         \"nome\": \"Comunidade Vida e Paz\",\n" +
                    "         \"apresentacao\": null,\n" +
                    "         \"morada\": \"\n" +
                    "Rua Domingos Bomtempo, nº 7\n" +
                    "\\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                    "         \"telefone\": \"218460165\",\n" +
                    "         \"telemovel\": \"\",\n" +
                    "         \"website\": \"movimento1euro.com\",\n" +
                    "         \"email\": \"testes@movimento1euro.com\",\n" +
                    "         \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                    "         \"youtube\": \"\"\n" +
                    "       },\n" +
                    "       \"documentos\": [\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Paper\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Revista\"\n" +
                    "        }\n" +
                    "       ],\n" +
                    "       \"videos\": [\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                    "            \"descricao\": \"video\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                    "            \"descricao\": \"video2\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                    "            \"descricao\": \"video3\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "     },\n" +
                    "     {\n" +
                    "       \"id\": 132,\n" +
                    "       \"titulo\": \"EM’LAÇANDO ALEGRIAS!\",\n" +
                    "       \"descricao\": \"Estávamos sózinhos em casa!\\r\\n\\r\\nAliás, não estávamos sózinhos. A solidão e a EM mais conhecida por Esclerose Múltipla estavam connosco...........\\r\\n\\r\\nSomos milhares, mas delegámos na Helena, na Ana, no Rui e na Luisa  a apresentação  de um anjo da guarda que a SPEM “arranjou”  e que  começou a visitar-nos e a modificar os nossos dias.\\r\\n\\r\\nAgora esperamos pela sua visita e semana a semana a expectativa renasce.\\r\\n\\r\\nPassámos a ter um objetivo! A ter companhia.\\r\\n\\r\\nJuntos pintamos, trabalhamos no computador, fazemos cem número de trabalhos no tablet que desenvolvem as nossas criatividades, a imaginação e que avivam na nossa memória. Rimos e conversamos. Estamos a adorar!\\r\\n\\r\\nO anjo da guarda é o projeto EM’Laço, totalmente inovador, concebido pela  SPEM - Sociedade Portuguesa de Esclerose Múltipla IPSS  para prestar apoio aos utentes, que na sua grande maioria se encontram numa situação de extremo isolamento nos seus domicilios, sem condições fisicas ou económicas para sair de casa ou contratar ajudas exteriores.\\r\\n\\r\\nO EM’Laço, com deslocações semanais que incluem uma educadora social e voluntários formados, proporciona a estes utentes gratuitamente  o acompanhamento que necessitam para desenvolver as suas capacidades cognitivas, de concentração de coordenação motora e para melhorar a comunicação (verbal e não verbal) e a expressão corporal.\\r\\n\\r\\nO EM’Laço  combate assim a solidão, levando de forma informal mas profissional, afeto, conversa e ocupações dinâmicas que por sua vez agilizam a inserção na sociedade e na própria familia, aumentando a auto estima e a indepêndencia de cada utente.\\r\\n\\r\\nA outra componente  do projecto EM’Laço, e por isso a SPEM considera que é um projeto inovador,  é a formação paralela de voluntários  por forma a que os serviços sejam prestados de forma coesa, uniforme, cheia de afetos, carinho e profissional.\\r\\n\\r\\nO objetivo é que possa expandir não só em número de pessoas servidas mas a diferentes faixas etárias, uma vez que cada vez mais aparecem jovens com EM.\\r\\n\\r\\nO EM’Laço é um bom exemplo de um 3 em 1: para os utentes combate a solidão, o abandono a que muitas vezes são votados e devolve-lhes expectativas e renova objetivos; para os familiares garante-lhes o bem estar dos seus doentes e permite-lhes os tempos livres que também necessitam; e para todos, dá formação a voluntários para poder chegar a mais doentes.\\r\\n\\r\\nContribua para a nossa causa e ajude o  EM’Laço a levar mais longe os afetos e a servir cada vez  mais e melhor quem precisa de si.\",\n" +
                    "       \"verba\": \"768.61\",\n" +
                    "       \"votos\": \"0\",\n" +
                    "       \"voto_utilizador\": false,\n" +
                    "       \"associacao\": {\n" +
                    "         \"nome\": \"SPEM\",\n" +
                    "         \"apresentacao\": null,\n" +
                    "         \"morada\": \"\n" +
                    "Rua Zófimo Pedroso 66\n" +
                    "\\n1950-291 Lisboa\\n\",\n" +
                    "         \"telefone\": \"218650480\",\n" +
                    "         \"telemovel\": \"\",\n" +
                    "         \"website\": \"movimento1euro.com\",\n" +
                    "         \"email\": \"testes@movimento1euro.com\",\n" +
                    "         \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                    "         \"youtube\": \"\"\n" +
                    "       },\n" +
                    "       \"documentos\": [\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Paper\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Revista\"\n" +
                    "        }\n" +
                    "       ],\n" +
                    "       \"videos\": [\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                    "            \"descricao\": \"video2\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                    "            \"descricao\": \"video3\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                    "            \"descricao\": \"video\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "     },\n" +
                    "     {\n" +
                    "       \"id\": 133,\n" +
                    "       \"titulo\": \"O BEM EM MOVIMENTO\",\n" +
                    "       \"descricao\": \"Num vai e vem circula a nossa carrinha para o Bem transportar.\\r\\n\\r\\nEm movimento estamos e precisamos continuar.\\r\\n\\r\\nAo movimento 1 euro pedimos ajuda para a carrinha abastecer\\r\\n\\r\\ne os laços de solidariedade fortalecer.\",\n" +
                    "       \"verba\": \"760\",\n" +
                    "       \"votos\": \"1\",\n" +
                    "       \"voto_utilizador\": false,\n" +
                    "       \"associacao\": {\n" +
                    "         \"nome\": \"Casa do Gaiato de Lisboa\",\n" +
                    "         \"apresentacao\": null,\n" +
                    "         \"morada\": \"\n" +
                    "Rua Padre Adriano nº 40\n" +
                    "\\n2660-119 Santo Antão do Tojal\\n\",\n" +
                    "         \"telefone\": \"219749974\",\n" +
                    "         \"telemovel\": \"\",\n" +
                    "         \"website\": \"\",\n" +
                    "         \"email\": \"testes@movimento1euro.com\",\n" +
                    "         \"facebook\": \"\",\n" +
                    "         \"youtube\": \"\"\n" +
                    "       },\n" +
                    "       \"documentos\": [\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Paper\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                    "          \"descricao\": \"Revista\"\n" +
                    "        }\n" +
                    "       ],\n" +
                    "       \"videos\": [\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                    "            \"descricao\": \"video3\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                    "            \"descricao\": \"video\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                    "            \"descricao\": \"video2\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "     }\n" +
                    "   ]\n" +
                    " }\n" +
                    "  ]\n" +
                    " }");






            JSONArray votingCauses = obj.getJSONArray("votacao");
            Election election = new Election(obj);
            VotingCause cause;
            causesList.clear();
            for (int j = 0; j < votingCauses.length(); j++) {
                //TODO discarded useful information?
                JSONArray arr = votingCauses.getJSONObject(j).getJSONArray("causas");
                for (int i = 0; i < arr.length(); i++) {
                    cause = new VotingCause(arr.getJSONObject(i));
                    cause.setElection(election);
                    causesList.add(cause);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return causesList;
    }

    public static void generatePastCauses(String year,Map<String,List<Cause>> allCausesByYear){
        JSONObject obj = null;
        List<Cause> causesList= new ArrayList<>();

        try {

            if(year.equals("2017")){
                obj = new JSONObject("{\n" +
                        "  \"result\": \"success\",\n" +
                        "  \"causes\": [\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Janeiro 2017\",\n" +
                        "      \"data_de_inicio\": \"01-01-2017 00:00:00\",\n" +
                        "      \"data_de_fim\": \"02-01-2017 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"titulo\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "  ]\n" +
                        "}");
            }else {
                obj = new JSONObject("{\n" +
                        "  \"result\": \"success\",\n" +
                        "  \"causes\": [\n" +
                        "    {\n" +
                        "      \"id\": 123465,\n" +
                        "      \"titulo\": \"Dezembro 2016\",\n" +
                        "      \"data_de_inicio\": \"21-08-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-12-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Novembro 2016\",\n" +
                        "      \"data_de_inicio\": \"21-08-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-10-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Outubro 2016\",\n" +
                        "      \"data_de_inicio\": \"21-08-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-10-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Setembro 2016\",\n" +
                        "      \"data_de_inicio\": \"21-08-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-09-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Agosto 2016\",\n" +
                        "      \"data_de_inicio\": \"21-08-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"27-08-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Julho 2016\",\n" +
                        "      \"data_de_inicio\": \"21-06-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-07-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": 129,\n" +
                        "      \"titulo\": \"Junho 2016\",\n" +
                        "      \"data_de_inicio\": \"21-05-2016 00:00:00\",\n" +
                        "      \"data_de_fim\": \"20-06-2016 23:59:59\",\n" +
                        "      \"montante_disponivel\": \"700\",\n" +
                        "      \"total_votos\": null,\n" +
                        "      \"causas\": [\n" +
                        "        {\n" +
                        "         \"id\": 130,\n" +
                        "         \"nome\": \"Higiene para todos\",\n" +
                        "         \"descricao\": \"A Comunidade Vida e Paz tem como missão ir ao encontro e acolher pessoas sem-abrigo, ou em situação de vulnerabilidade social, ajudando-as a recuperar a sua dignidade e a (re)construir o seu projeto de vida, através de uma ação integrada de prevenção, reabilitação e reinserção.\\r\\n\\r\\nPara podermos oferecer os cuidados básicos de higiene às pessoas sem-abrigo e aos utentes em programa de reabilitação e reinserção necessitamos de produtos básicos de higiene: gel de banho, champô, e pasta de dentes.\\r\\n\\r\\nEm 2015, apoiámos diariamente cerca de 500 pessoas através das Equipas de Rua e acolhemos aproximadamente 300  situações nas respostas residenciais da Comunidade: Comunidades Terapêuticas, Comunidades de Inserção, Apartamentos de Reinserção e partilhados e Unidade de Vida Autónoma.\\r\\n\\r\\nCom esta simbólica ajuda é abrigo para quem o perdeu!\",\n" +
                        "         \"verba\": \"750\",\n" +
                        "         \"votos\": \"0\",\n" +
                        "         \"voto_utilizador\": false,\n" +
                        "         \"associacao\": {\n" +
                        "           \"nome\": \"Comunidade Vida e Paz\",\n" +
                        "           \"apresentacao\": null,\n" +
                        "           \"morada\": \"\n" +
                        "  Rua Domingos Bomtempo, nº 7\n" +
                        "  \\n1700-142 Lisboa\\n\",    //CUIDADO QUE ISTO RETORNA HTML\n" +
                        "           \"telefone\": \"218460165\",\n" +
                        "           \"telemovel\": \"\",\n" +
                        "           \"website\": \"movimento1euro.com\",\n" +
                        "           \"email\": \"testes@movimento1euro.com\",\n" +
                        "           \"facebook\": \"https://www.facebook.com/movimento1euro\",\n" +
                        "           \"youtube\": \"\"\n" +
                        "         },\n" +
                        "         \"documentos\": [\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Paper\"\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"url\": \"http://www.illumina.com/content/dam/illumina-marketing/documents/products/research_reviews/cancer_research_review.pdf\",\n" +
                        "            \"descricao\": \"Revista\"\n" +
                        "          }\n" +
                        "         ],\n" +
                        "         \"videos\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=oAVtOmhQ5D4\",\n" +
                        "              \"descricao\": \"video\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=baVQCdgS0KI\",\n" +
                        "              \"descricao\": \"video2\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://www.youtube.com/watch?v=sLz6B-gkm_4\",\n" +
                        "              \"descricao\": \"video3\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
            }



            // Get Yearly elections array from response
            JSONArray yearlyElections = obj.getJSONArray("causes");
            int totalElectionNr = yearlyElections.length();


            List<Cause> requestedCauses = new ArrayList<Cause>();

            for (int electionNr = 0; electionNr < totalElectionNr; electionNr++) {

                // Get each election's winning causes and add them
                JSONObject electionObject = yearlyElections.getJSONObject(electionNr);
                JSONArray winningCauses = electionObject.getJSONArray("causas");
                int totalCausesNr = winningCauses.length();

                Election election = new Election(electionObject);

                for (int causeNr = 0; causeNr < totalCausesNr; causeNr++) {

                    JSONObject cause = winningCauses.getJSONObject(causeNr);

                    //HashMap<String, String> tempCause = new HashMap<String, String>();
                    //add titulo and montante to the cause object
                    cause.put("titulo", electionObject.getString("titulo"));
                    cause.put("montante_disponivel", electionObject.getString("montante_disponivel"));
                    PastCause tempCause = new PastCause(cause);
                    tempCause.setElection(election);


                    requestedCauses.add(tempCause);
                }
            }

            allCausesByYear.put(year, requestedCauses);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
