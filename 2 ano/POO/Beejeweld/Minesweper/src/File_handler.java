import java.io.*;
//IMPORTANTE!!
//NOME DO FICHEIRO: Scores.txt

public class File_handler {

	
	private BufferedReader fR;
	private BufferedWriter fW;

	
	
	public void abre_leitura(String nome) throws IOException{ //abre o ficheiro para leitura
		
		fR=new BufferedReader(new FileReader(nome));
		
	}
	public void abre_escrita(String nome) throws IOException{ //abre o ficheiro para escrita
		
		fW=new BufferedWriter(new FileWriter(nome));

		
		
	}
	public String le_linha() throws IOException{ //le uma linha de cada vez
		String linha;
                linha=fR.readLine();
                System.out.println(linha);
		return linha;
	}
	
	public void escreve_linha(String linha) throws IOException{
		
		fW.write(linha,0,linha.length());
	}
	
	public Dados_ficheiro get_data() throws IOException{ //le nome numa linha e na a seguir a pontuacao
		Dados_ficheiro temp=new Dados_ficheiro();
		temp.new_nome(le_linha());
		temp.new_score(Integer.parseInt(le_linha()));
		return temp;
		
	}
	public void write_data(Dados_ficheiro dados) throws IOException{//ecreve nome numa linha e na a seguir a pontuacao
		escreve_linha(dados.get_nome());
		escreve_linha(String.valueOf(dados.get_score()));//rever
	}
	
	public void fecha_leitura() throws IOException{
		fR.close();
	}
	public void fecha_escrita() throws IOException{
		fW.close();
	}
	//criar metodo que conte o tamanho e leia todos os dados
	
	public Dados_ficheiro[] get_all_data() throws IOException{
		int i;
		//abrir para leitura
		abre_leitura("Scores.txt");
		//saber o tamanho do ficheiro
		int n=0;
		while(fR.readLine()!=null){ //conta o tamanho do array
			n++;
		}
		fecha_leitura();
		Dados_ficheiro[] temp=new Dados_ficheiro[n];
		abre_leitura("Scores.txt");
		for(i=0;i<n/2;i++){
			temp[i]=get_data();
		}
		return temp;
	}
        public String get_all_data_string() throws IOException{
          FileReader reader = new FileReader("Scores.txt");  
          BufferedReader buffReader = new BufferedReader(reader);  
          String aux="";
          String linha;  
          while ((linha = buffReader.readLine()) != null) {  
              System.out.println(linha);
             aux.concat(linha);
             aux.concat("\n");
         }  
             return aux;


	}
}
