/*Author: Rui Pedro Paiva
Teoria da Informação, LEI, 2007/2008
*/

#include <cstdlib>
#include <iostream>
#include <fstream>
#include "gzip.h"
#include "huffman.h"
#define DEBUG 4
#define TRUE 1
using namespace std;



int HCLEN_Order[] = {16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};

int BitsCMP[] = {
	0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3,
	4, 4, 4, 4, 5, 5, 5, 5, 0
};

int MinimalCMP[] = {
	3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35,
	43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258
};

int DistanceBits[] = {
	0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8,
	9, 9, 10, 10, 11, 11, 12, 12, 13, 13
};

int MinimalDistanceBits[] = {
	1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193,
	257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145,
	8193, 12289, 16385, 24577
};


class BitStream //classe responsavel pela leitura da informaçao contida
{	
	private:	
	FILE * fp;
	unsigned int byteBuffer; //buffer com byte do ficheiro		
	int bitsInBuffer; //numero de bits disponiveis no buffer

	inline int makeMask(int m) //cria uma mascara com os ultimos m-1 bits a 1
	{
		return ((1<<m)-1);
	}
	
	public:
	BitStream(FILE *fp,int bitsInBuffer = 0,unsigned int previousByte = 0) //construtor da classe BitStream. Inicializaçao dos atributos
	{	
		this->byteBuffer=previousByte; 
		this->bitsInBuffer = bitsInBuffer;
		this->fp = fp;	
	}
	
	unsigned int getKBits(int n) //permite obter nbits
	{
		unsigned int temp=0;	
		unsigned char aux;
		
		while(n>0)
			if(bitsInBuffer<n) //caso o numero de bits no buffer seja menor do que o numero de bits pedidos
			{	
				fread(&aux, 1, 1, fp);//vai buscar um byte ao ficheiro	
				byteBuffer = (aux<<bitsInBuffer) | byteBuffer;//adiciona o byte ao buffer de bits
				bitsInBuffer += 8;//incrementa o numero de bits no buffer
			}
			else
			{	
				temp = byteBuffer & makeMask(n); //coloca a a informacao pedida no temp	
				bitsInBuffer-=n;//decrementa o numero de bits no buffer
				byteBuffer=byteBuffer>>n;//remove used bits
				n=0;
			}
		return temp;	
	}
};

char * bitToString_int(int value, int len) //cria uma string com os codigos
{
	char* out = new char [len+1];
	int mask = 1;
	int i;
	
	for (i = 0; i < len; i++)
	{
		out[len-i-1] = ((char)((value >> i) & mask)) + '0';
	}
	out[len] = 0;
	
	return out; 
}

char ** convertCMPToHuffmanCodes(char * vector_cmp, int sizeHCLEN) // array de comprimentos para codigos de huffman
{
	int i = 0;
	int j;
	char** vectorHuffcmp;
	int maxlen, MinimalCMP;
	maxlen = vector_cmp[0]; //calcula o comprimento maximo
	
	while(vector_cmp[i] == 0)
	{
		i++;
	}
	
	MinimalCMP = vector_cmp[i]; //calcula o comprimento minimo
	
	for (j = 0; j < sizeHCLEN; j++)
	{
		if (vector_cmp[j] > maxlen) //calcula o valor maximo do vector de ccc e coloca na variavel maxlen
		{
			maxlen = vector_cmp[j];
		}

		if (vector_cmp[j] < MinimalCMP && vector_cmp[j] != 0) //calcula o valor minimo do vector de ccc e coloca na variavel MinimalCMP
		{
			MinimalCMP = vector_cmp[j];
		}
	}

	if (DEBUG == 4)
	{
		for (int t=0; t< sizeHCLEN; t++)
		{
			printf("valorf = %d\n", vector_cmp[t]);
		}
		printf("minimo = %d\n", MinimalCMP);
		printf("maximo = %d\n", maxlen);
	}
	
	int l, car = 0;
	
	vectorHuffcmp = new char* [sizeHCLEN];
	
	for (j = 0; j < sizeHCLEN; j++)
	{
		if (vector_cmp[j] == 0)
		{
			vectorHuffcmp[j] = NULL;
		}
	}
			
	for (l = MinimalCMP; l <= maxlen; l++) //percorre os valores entre o minimo e o maximo
	{
		for (j = 0; j < sizeHCLEN; j++) //percorre o vector ccc
		{
			if (l == vector_cmp[j])
			{
				vectorHuffcmp[j] =  bitToString_int(car, l);
				printf("Simbolo = %d   Bit = %s\n",car,vectorHuffcmp[j]);
				car++;
			}
		} 
		car = car << 1;
	}
	
	return vectorHuffcmp;
}

char * BuildTree(HuffmanTree* tree, int num, BitStream * File) //constroi arvore de huffman
{	
	char * treeRepr = new char[num+1]; //cria array
	int previousPosition = -1, position, n;
	char aux;

	for (int i=0; i<num; i++)
	{
		resetCurNode(tree); //coloca o ponteiro no inicio da arvore
		do 
		{	
			aux = File->getKBits(1) + '0';
			position = nextNode(tree, aux);
		} while (position == -2); 
		
		if (position == -2) 
		{
			break;
		}
		
		if (position == 16)
		{
			n = File->getKBits(2) + 3;
			
			for (int j = 0; j < n; j++, i++)
			{
				treeRepr[i] = previousPosition;
			}
			i--;
		}
		
		else if (position == 17)
		{
			n = File->getKBits(3) + 3;

			for (int j = 0; j < n; j++, i++)
			{
				treeRepr[i] = 0;
			}
			i--;
		} 
		
		else if (position == 18)
		{
			n = File->getKBits(7) + 11;

			for (int j = 0; j < n; j++, i++)
			{
				treeRepr[i] = 0;
			}
			i--;
		} 
		
		else
		{
			treeRepr[i] = position;
		}

		previousPosition = position;
	}
	return treeRepr;
}

int getLength(int index, int * Len, int * MinimalCMP, BitStream &buffer) //calcula o tamanho final
{	
	int extra, minLength, finalLength;
	short buffer_extra = 0;

	extra = Len[index];
	minLength = MinimalCMP[index];
		
	if (extra > 0)
	{
		buffer_extra = buffer.getKBits(extra);
	}
	
	else
	{
		buffer_extra = 0;
	}		
	finalLength = minLength + buffer_extra;	

	return finalLength;
}

class OutputFile
{
	private:
	int writtingPosition;
	char * File;
	FILE * fp ;
	
	public: 
	OutputFile(int File_size,char * name) //cria o ficheiro de saida
	{
		writtingPosition = 0;
		File = new char[File_size]; 
		fp = fopen(name, "w+");
	}
	
	void operator << (const int x)
	{	
		File[writtingPosition]=x;
		writtingPosition++;	
	}
	
	void offset(int comprimento,int distancia) //LZ77
	{	
		for(int i = 0 ; i<comprimento ; i++)
		{		
			File[writtingPosition] = File[writtingPosition-distancia];
			writtingPosition++;
		}
	} 
	
	void criaFicheiro() //escreve no ficheiro
	{
		fwrite(File, writtingPosition, 1, fp);
		fclose(fp);	
	}
};


BitStream * bitStream;

huffStruct huffman;



//função principal, a qual gere todo o processo de descompactaçã0
int main(int argc, char** argv)
{
	//--- Gzip file managment variables
	FILE *gzFile;  //ponteiro para o ficheiro a abrir
	long fileSize;
	long origFileSize;
	int numBlocks = 0;	
	gzipHeader gzh;
	unsigned char readedByte;  //variável temporária para armazenar um byte lido directamente do ficheiro
	unsigned int previousByte = 0;  //último byte lido (poderá ter mais que 8 bits, se tiverem sobrado alguns de leituras anteriores)
	char bitsNec = 0, bitsDisp = 0;		
	
	//--- obter ficheiro a descompactar
	char fileName[] = "FAQ.txt.gz";
	/*
	if (argc != 2)
	{
		printf("Linha de comando inválida!!!");
		return -1;
	}
	char * fileName = argv[1];
	*/
	//--- processar ficheiro
	gzFile = fopen(fileName, "r");
	fseek(gzFile, 0L, SEEK_END);
	fileSize = ftell(gzFile); //tamanho do file
	fseek(gzFile, 0L, SEEK_SET);

	//ler tamanho do ficheiro original
	origFileSize = getOrigFileSize(gzFile);


	//--- ler cabeçalho
	int error = getHeader(gzFile, &gzh);
	if (error != 0)
	{
		printf ("Formato inválido!!!");
		return -1;
	}


	OutputFile outputFile(origFileSize,gzh.fName);
	
	//--- Para todos os blocos encontrados
	char BFINAL;	
	
	do
	{				
		//--- ler o block header: primeiro readedByte depois do cabeçalho do ficheiro
		bitsNec = 3;
		if (bitsDisp < bitsNec)
		{
			fread(&readedByte, 1, 1, gzFile);
			previousByte = (readedByte << bitsDisp) | previousByte;
			bitsDisp += 8;
		}
		
		//obter BFINAL
		//ver se é o último bloco
		BFINAL = previousByte & 0x01; //primeiro bit é o menos significativo
		printf("BFINAL = %d\n", BFINAL);
		previousByte = previousByte >> 1; //descartar o bit correspondente ao BFINAL
		bitsDisp -=1;
						
		//analisar block header e ver se é huffman dinâmico					
		if (!isDynamicHuffman(previousByte))  //ignorar bloco se não for Huffman dinâmico
			continue;				
		
		previousByte = previousByte >> 2; //descartar os 2 bits correspondentes ao BTYPE
		bitsDisp -= 2;
		
		//---------------------//

		bitStream = new BitStream(gzFile,bitsDisp,previousByte); //cria objecto principal para descompactaçao
		
		huffman.HLIT = bitStream->getKBits(5); //obtem o valor de HLIT
		huffman.HDIST = bitStream->getKBits(5); //obtem o valor de HDIST
		huffman.HCLEN = bitStream->getKBits(4); //obtem o valor de HCLEN
		
		cout<< "HLIT -----> " <<(int)huffman.HLIT<<"\n"; //imprime
		cout<< "HDIST ------>" <<(int)huffman.HDIST<<"\n"; //imprime
		cout<< "HCLEN ------>" <<(int)huffman.HCLEN<<"\n"; //imprime
		
		//--------------------//

		char* vectorCMP; 
		vectorCMP = new char[19]; //inicializa vector de comprimentos com o numero de posiçoes de HCLEN_order
		
		printf("Array de comprimentos de codigo\n");
		
		printf("++++ %d\n", huffman.HCLEN);
		
		for (int i = 0; i < huffman.HCLEN + 4; i++) //percorre as posicoes de HCLEN + 4
		{
			vectorCMP[HCLEN_Order[i]] = bitStream->getKBits(3); //obtem 3 bits e coloca o valor na posicao segundo a ordem de HCLEN + 4
			printf("Localizacao ---> %d, i ---> %d ,valor ---> %d\n",HCLEN_Order[i], i, vectorCMP[HCLEN_Order[i]]); 
		}
		
		//--------------------//
		
		char ** huffmanCMP; 
		
		huffmanCMP = convertCMPToHuffmanCodes(vectorCMP,19); //converte os comprimentos de codigo em “alfabeto de comprimentos de codigos”
		
		for (int i = 0; i < 19; i++)
		{
			printf("posicao = %d | valor = %d | codigo = %s \n",i,vectorCMP[i],huffmanCMP[i] == NULL ? "":huffmanCMP[i]); 
		}
		
		//-------------------//

		HuffmanTree *huffmanTreeCMP = createHFTree(); //cria arvore vazia
		
		for (int i = 0; i < 19; i++)
		{
			if (huffmanCMP[i])
			{
				addNode(huffmanTreeCMP, huffmanCMP[i], i, 0); //adiciona o codigo à arvore
			}
		}
		
		//------------------//

		cout<<"Literais\n";

		char* LITCC = BuildTree(huffmanTreeCMP, huffman.HLIT+257, bitStream); //armazena no array LITCC os HLIT + 257 comprimentos de codigo

		char** LIT = convertCMPToHuffmanCodes(LITCC, huffman.HLIT + 257); //converte para alfabeto de comprimentos de codigo de huffman
		
		for (int i = 0; i < huffman.HLIT+257; i++)
		{
			printf("%d --> %d --> %s\n", i, LITCC[i], LIT[i] == NULL ? "":LIT[i] ); //imprime
		}
		
		//-------------------//

		cout<<"Comprimento\n";

		char* DISTCC = BuildTree(huffmanTreeCMP, huffman.HDIST+1, bitStream); //armazena no array LITCC os HDIST + 1 comprimentos de codigo
		
		char **DIST = convertCMPToHuffmanCodes(DISTCC, huffman.HDIST+1); //converte para alfabeto de comprimentos de codigo de huffman
		
		for (int i = 0; i < huffman.HDIST+1; i++)
		{
			printf("%d --> %d --> %s\n", i, DISTCC[i], DIST[i] == NULL ? "":DIST[i] );
		}
		
		delete DISTCC; //deallocate
		delete LITCC; //deallocate
		delete vectorCMP; //deallocate
		
		HuffmanTree *hfLIT = createHFTree(); //inicializa arvore de Literais
		HuffmanTree *hfDIST = createHFTree(); //inicializa arvore de distancias
			
		for (int i = 0; i < huffman.HLIT+257; i++) //constroi arvore de literais
		{
			if (LIT[i] != NULL) //NULL ---> 0
			{
				addNode(hfLIT, LIT[i], i, 0); //adiciona o valor ao no
			}
		}

		for (int i = 0; i < huffman.HDIST+1; i++) //constroi arvore de Distancias
		{
			if (DIST[i] != NULL) //NULL ---> 0
			{
				addNode(hfDIST, DIST[i], i, 0); //adiciona valor ao no
			}
		}

		delete LIT; //deallocate
		delete DIST; //deallocate
		delete huffmanCMP; //deallocate
		
		int literal,dist;
		char bit;
		
		int length = 0,distance = 0;
		
		while(TRUE)
		{	
			resetCurNode(hfLIT); //volta ao no inicial da arvore de literais
			do 
			{
				bit = bitStream->getKBits(1)+'0';
				literal = nextNode(hfLIT, bit);
			} while (literal == -2); //avancao pelos varios ramos ate chegar a folha
			
			if (literal < 0) //invalido
			{
				break; 
			}
			
			if(literal <= 256)
			{
				if(256 == literal) //fim do bloco
				{
					break;
				}
				else
				{
					outputFile << literal; //coloca directamente
				}	
			}
			else
			{	
				length = getLength(literal-257,BitsCMP,MinimalCMP,*bitStream); //calcula length
				resetCurNode(hfDIST); //volta ao inicio da arvore
				do 
				{
					bit = bitStream->getKBits(1) + '0';
					dist = nextNode(hfDIST, bit);
				} while (dist == -2); //percorre ramos ate as folhas

				if (dist < 0)
				{
					break; //não encontrou o nó
				}

				distance = getLength(dist,DistanceBits,MinimalDistanceBits,*bitStream); //calcula distance a recuar
				
				outputFile.offset(length,distance); //vai escrever
			}
		}		
		//actualizar número de blocos analisados
		numBlocks++;
	}while(BFINAL == 0);

	outputFile.criaFicheiro();
	
	//terminações			
	fclose(gzFile);
	printf("End: %d bloco(s) analisado(s).\n", numBlocks);	

    return EXIT_SUCCESS;
}


//---------------------------------------------------------------
//Lê o cabeçalho do ficheiro gzip: devolve erro (-1) se o formato for inválido devolve, ou 0 se ok
int getHeader(FILE *gzFile, gzipHeader *gzh) //obtém cabeçalho
{
	unsigned char byteLido;

	//Identicação 1 e 2: valores fixos
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).ID1 = byteLido;
	if ((*gzh).ID1 != 0x1f) return -1; //erro no cabeçalho
	
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).ID2 = byteLido;
	if ((*gzh).ID2 != 0x8b) return -1; //erro no cabeçalho
	
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).CM = byteLido;
	if ((*gzh).CM != 0x08) return -1; //erro no cabeçalho
				
	//Flags
	fread(&byteLido, 1, 1, gzFile);
	unsigned char FLG = byteLido;
	
	//MTIME
	char lenMTIME = 4;	
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).MTIME = byteLido;
	for (int i = 1; i <= lenMTIME - 1; i++)
	{
		fread(&byteLido, 1, 1, gzFile);
		(*gzh).MTIME = (byteLido << 8) + (*gzh).MTIME;				
	}
					
	//XFL (not processed...)
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).XFL = byteLido;
	
	//OS (not processed...)
	fread(&byteLido, 1, 1, gzFile);
	(*gzh).OS = byteLido;
	
	//termina header
	
	
	//--- Check Flags
	(*gzh).FLG_FTEXT = (char)(FLG & 0x01);
	(*gzh).FLG_FHCRC = (char)((FLG & 0x02) >> 1);
	(*gzh).FLG_FEXTRA = (char)((FLG & 0x04) >> 2);
	(*gzh).FLG_FNAME = (char)((FLG & 0x08) >> 3);
	(*gzh).FLG_FCOMMENT = (char)((FLG & 0x10) >> 4);
				
	//FLG_EXTRA
	if ((*gzh).FLG_FEXTRA == 1)
	{
		//1º byteLido: LSB, 2º: MSB
		//char lenXLEN = 2;

		fread(&byteLido, 1, 1, gzFile);
		(*gzh).xlen = byteLido;
		fread(&byteLido, 1, 1, gzFile);
		(*gzh).xlen = (byteLido << 8) + (*gzh).xlen;
		
		(*gzh).extraField = new unsigned char[(*gzh).xlen];
		
		//ler extra field (deixado como está, i.e., não processado...)
		for (int i = 0; i <= (*gzh).xlen - 1; i++)
		{
			fread(&byteLido, 1, 1, gzFile);
			(*gzh).extraField[i] = byteLido;
		}
	}
	else
	{
		(*gzh).xlen = 0;
		(*gzh).extraField = 0;
	}
	
	//FLG_FNAME: ler nome original	
	if ((*gzh).FLG_FNAME == 1)
	{			
		(*gzh).fName = new char[1024];
		unsigned int i = 0;
		do
		{
			fread(&byteLido, 1, 1, gzFile);
			if (i <= 1023)  //guarda no máximo 1024 caracteres no array
				(*gzh).fName[i] = byteLido;
			i++;
		}while(byteLido != 0);
		if (i > 1023)
			(*gzh).fName[1023] = 0;  //apesar de nome invectorCMPleto, garantir que o array termina em 0
	}
	else
		(*gzh).fName = 0;
	
	//FLG_FCOMMENT: ler comentário
	if ((*gzh).FLG_FCOMMENT == 1)
	{			
		(*gzh).fComment = new char[1024];
		unsigned int i = 0;
		do
		{
			fread(&byteLido, 1, 1, gzFile);
			if (i <= 1023)  //guarda no máximo 1024 caracteres no array
				(*gzh).fComment[i] = byteLido;
			i++;
		}while(byteLido != 0);
		if (i > 1023)
			(*gzh).fComment[1023] = 0;  //garantir que o array termina em 0
	}
	else
		(*gzh).fComment = 0;

	
	//FLG_FHCRC (not processed...)
	if ((*gzh).FLG_FHCRC == 1)
	{			
		(*gzh).HCRC = new unsigned char[2];
		fread(&byteLido, 1, 1, gzFile);
		(*gzh).HCRC[0] = byteLido;
		fread(&byteLido, 1, 1, gzFile);
		(*gzh).HCRC[1] = byteLido;		
	}
	else
		(*gzh).HCRC = 0;
	
	return 0;
}


//---------------------------------------------------------------
//Analisa block header e vê se é huffman dinâmico
int isDynamicHuffman(unsigned char byteAnterior)
{				
	unsigned char BTYPE = byteAnterior & 0x03;
					
	if (BTYPE == 0) //--> sem compressao
	{
		printf("Ignorando bloco: sem compactacao!!!\n");
		return 0;
	}
	else if (BTYPE == 1)
	{
		printf("Ignorando bloco: compactado com Huffman fixo!!!\n");
		return 0;
	}
	else if (BTYPE == 3)
	{
		printf("Ignorando bloco: BTYPE = reservado!!!\n");
		return 0;
	}
	else
		return 1;		
}


//---------------------------------------------------------------
//Obtém tamanho do ficheiro original
long getOrigFileSize(FILE * gzFile)
{
	//salvaguarda posição actual do ficheiro
	long fp = ftell(gzFile);
	
	//últimos 4 byteLidos = ISIZE;
	fseek(gzFile, -4, SEEK_END);
	
	//determina ISIZE (só correcto se cabe em 32 bits)
	unsigned long sz = 0;
	unsigned char byteLido;
	fread(&byteLido, 1, 1, gzFile);
	sz = byteLido;
	for (int i = 0; i <= 2; i++)
	{
		fread(&byteLido, 1, 1, gzFile);
		sz = (byteLido << 8*(i+1)) + sz;
	}

	
	//restaura file pointer
	fseek(gzFile, fp, SEEK_SET);
	
	return sz;		
}


//---------------------------------------------------------------
void bits2String(char *stbyteAnteriorits, unsigned char byteLido)
{
	char mask = 0x01;  //get LSbit
	
	stbyteAnteriorits[8] = 0;
	for (char bit, i = 7; i >= 0; i--)
	{		
		bit = byteLido & mask;
		stbyteAnteriorits[i] = bit +48; //converter valor numérico para o caracter alfanumérico correspondente
		byteLido = byteLido >> 1;
	}	
}



