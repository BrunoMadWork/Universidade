/*Author: Rui Pedro Paiva
Teoria da Informação, LEI, 2008/2009*/

#include "DES.h"

//função para encriptação
int DES (char* inFileName, unsigned long long key)
{
	return DESgeneral(inFileName, key, 0);
}


//função para decriptação
int unDES (char* inFileName, unsigned long long key)
{
	return DESgeneral(inFileName, key, 1);
}


int debug = 1;

//função geral para encriptação (type = 0) e decriptação (type = 1) de um ficheiro 
int DESgeneral (char* inFileName, unsigned long long key, int type)
{
	FILE* DESInFile;
	unsigned char* inByteArray;
	long inFileSize;
	unsigned char* crpByteArray;
	char* outFileName;
	int write;
	char response;
	struct stat stFileInfo;
	FILE* DESOutFile;
	char suf[5];


	//abrir ficheiro e ler tamanho
	DESInFile = fopen(inFileName, "rb");
	if (DESInFile == NULL)
	{
		printf("Error opening file for reading. Exiting...\n");
		return 1;
	}
	fseek(DESInFile, 0L, SEEK_END);
	inFileSize = ftell(DESInFile);  //ignore EOF
	fseek(DESInFile, 0L, SEEK_SET);


	//ler ficheiro inteiro para array inByteArray	
	inByteArray = (unsigned char*) calloc(inFileSize, sizeof(unsigned char)); 
	fread(inByteArray, 1, inFileSize, DESInFile);


	//criar assinatura
	if (type == 0)  //encripta‹o
	{
		/******* ADICIONAR CîDIGO: 
		 implementar ˆ fun‹o:
		 unsigned char* signature(unsigned char* inByteArray, long dim, unsigned long long key)  // ver abaixo
		 e adicionar hash aos dados
		 ***********************/
	}
	
	
	//encriptar dados e assinatura no array
	crpByteArray = encryptDES(inByteArray, inFileSize, key, type);
		
	//flush do crpByteArray para ficheiro
	//nome do ficheiro de saída
	if (type == 0)  //encriptação
	{
		outFileName = (char*) calloc(strlen(inFileName) + 5, sizeof(char)); 
		strcpy(outFileName, inFileName);
		strcat(outFileName, ".DES");
	}
	else  //decriptação
	{
		strcpy(suf, &inFileName[strlen(inFileName) - 4]);
		if (strcmp(suf, ".DES") == 0)
		{		
			outFileName = (char*) calloc(strlen(inFileName) + 5, sizeof(char)); 
			strcpy(outFileName, "DES_");
			strcat(outFileName, inFileName);
			outFileName[strlen(outFileName) - 4] = 0;
		}
		else
		{
			outFileName = (char*) calloc(14, sizeof(char));
			strcpy(outFileName, "DES_decrypted");
		}

	}
	
	
	//verificar assinatura
	if (type == 1)
	{
		/******* ADICIONAR CîDIGO: 
		 implementar ˆ fun‹o:
		 int checkSignature(unsigned char* inByteArray, unsigned char* hash)  // ver abaixo
		 e retirar hash aos dados
		 abortar desencripta‹o caso a verifica‹o da assinatura n‹o passe no teste
		 ***********************/		
	}
	
	//criar ficheiro
	write = 1;
	if(stat(outFileName, &stFileInfo) == 0) //see if file already exists
	{
		printf ("File already exists. Overwrite (y/n)?: ");
		response = getchar();
		if (response == 'n')
			write = 0;
		printf("\n");
		fflush(stdin);
	}

	if (write)
	{
		DESOutFile = fopen(outFileName, "wb");
		if (DESOutFile == NULL)
		{
			printf("Error opening file for writing!!! Exiting...\n");
			return -1;
		}
		fwrite(crpByteArray, 1, inFileSize, DESOutFile);
		fclose(DESOutFile);
	}
	
	//finalizações
	free(inByteArray);
	free(outFileName);
	free(crpByteArray);
	fclose(DESInFile);

	return 0;	
}


// função para encriptação/decriptação de dados no array inByteArray, de dimensão dim
unsigned char* encryptDES(unsigned char* inByteArray, long dim, unsigned long long key, int type)
{
	unsigned long long subKeys[16];
	unsigned long long temp;
	unsigned char* outByteArray;
	unsigned long long plain, cipher, aux1, aux2;
	int i, j;

	//obtém sub-keys (16 de comprimento 48)
	/**** ADICIONAR CÓDIGO PARA A FUNÇÃO DESKEYSCHEDULE (ABAIXO) ********/
	DESKeySchedule(key, subKeys);


	if (type == 1) //decrypt --> inverter subKeys
	{
 		for( i = 0; i < 16/2 ; i++){
 			temp=subKeys[15-i];
 			subKeys[15-i] = subKeys[i];
 			subKeys[i] = temp;
 		};
	}
	outByteArray = (unsigned char*) calloc(dim, sizeof(unsigned char)); 
	i = 0;
	plain = 0;
	while (i < dim)
	{
		plain = 0;
		j = i;
		while (j < i + 8 && j < dim)
		{
 			plain = plain | ((unsigned long long)inByteArray[j] << (64 - 8*(j-i+1)));
			j++;
		}

		//determina cifra
		if (j - i == 8)  //ficheiro é múltiplo de 8 bytes
		{
			/**** ADICIONAR CÓDIGO PARA A FUNÇÃO ENCRYPTDESPLAIN (ABAIXO) ********/
			cipher = encryptDESplain(plain, subKeys);
		} else
		{
			cipher = plain;
		}


		//guarda cifra no array de saída
		j = i;
		while (j < i + 8 && j < dim)
		{
			outByteArray[j] = (unsigned char) (cipher >> (56 - 8*(j-i)) & (0xFF));
			j++;
		}

		i = j;		
	}

	return outByteArray;
}


/************************************************************************************/
/***************************** ADICIONAR CóDIGO *************************************/
/************************************************************************************/


// função para encriptação de uma mensagem de 64 bits (plain), com base nas subKeys
//devolve a mensagem cifrada
unsigned long long encryptDESplain(unsigned long long plain, unsigned long long* subKeys)
{
    unsigned long long bit_actual, li, ri, temp, plainTrocada = 0, plainConcat, plainF = 0;
    int i;

     
    for (i=0; i<64; i++) {
        bit_actual = (plain >> (64-IP[i])) & 1; //efectua a permutação segundo array PC1
        plainTrocada |= bit_actual << (64-i-1); 
    }

    //plainTrocada = exchange(plain, IP, 64);


    li = (plainTrocada >> 32);  //divide na barte bireita e bisquerda
    ri = plainTrocada & 0xFFFFFFFF;
    //printf("L0->%llx\nR0->%llx\n", li, ri);


    for (i=0; i<16; i++) {
        temp = ri;
        ri = li ^ f(ri, subKeys[i]); //leva com a funcao 16 vezes com chaves diferentes
        li = temp;
        //printf("L%d->%llx\nR%d->%llx\n", i, li, i, ri);
    }
    //printf("L%d->%llx\nR%d->%llx\n", i, li, i, ri);

    plainConcat = (ri << 32) | li;


    for (i=0; i<64; i++) {
        bit_actual = (plainConcat >> (64-IPreverse[i])) & 1;
        plainF |= bit_actual << (64-i-1);
    }

    //printf("Cifra%d->%llx\n", debug++, plainF);

    return plainF;

}


// função para gerar sub-keys (uma chave para cada uma das 16 iterações)
void DESKeySchedule(unsigned long long key, unsigned long long* subKeys)
{
	unsigned long long aux;
	unsigned long Ci, Di;
	int i;
	short shift;

//printf("key -> %llx\n", key);
	//convert_to_binary(key, 64);
	/*Efectua operações de permutação*/
	aux = exchange(key, PC1, 56);
	/*********************************/
	//printf("aux -> %llx\n", aux);
	//convert_to_binary(aux, 64);

	Di =  aux & MASCARA_28; // Este número é uma máscara com os primeiros 36 bits a 0
	//printf("D0: 0x%lx\n", D0);
	aux = aux >> 28;
	Ci = aux & MASCARA_28;
	//printf("C0: 0x%lx\n", C0);

	for(i = 1;i <= 16; i++) {

		/*Define o shift circular*/
		if(i == 1 || i == 2 || i == 9 || i == 16)
			shift = 1;
		else
			shift = 2;

		/**********Faz o shift circular*************/
		Di = ((Di << shift) | (Di >> (28 - shift)));
		Ci = ((Ci << shift) | (Ci >> (28 - shift)));
		/*******************************************/
		/*Apaga os bits que não importam usando a máscara*/
		Ci = Ci & MASCARA_28;
		Di = Di & MASCARA_28;
		/*************************************************/

		//printf("C1: 0x%lx\n", Ci);
		//convert_to_binary(Ci, 32);
		//printf("D1: 0x%lx\n", Di);
		//convert_to_binary(Di, 32);

		aux = Ci;
		//printf("%llx\n", subKey);
		//convert_to_binary(subKey, 64);
		aux = aux << 28;
		aux = aux | Di;
		//printf("%llx\n", subKey);
		aux = aux << 8; //faz com que ci e di estejam concatenadas no inicio da palavra
		//convert_to_binary(subKey, 64);

		//printf("*********************************\n");
		aux = exchange(aux, PC2, 48);
		//convert_to_binary(subKey, 64);
		//convert_to_binary((unsigned long long) 0x0b02679b49a5, 64);
		//printf("subkey -> %llx\n", aux);
		//printf("subkeyOrig -> %llx\n", 0x0b02679b49a5);
		*(subKeys + (i-1)) = aux;
	
		/*if(subKey == 0xb02679b49a5)
			printf("okay\n");*/




	}

}
//Permuta uma chave de acordo com a tabela table nos últimos 'tam' bits menos significativos
unsigned long long exchange(unsigned long long key, unsigned int table[], int tam) {

	unsigned long long result;
	unsigned long long mask;
	unsigned long long readedBit;
	int i, shift/*, debug*/;

	result = 0;
	/*debug = 1;*/

	for(i = 0; i < tam; i++) {
		//printf("%d\n", i);
		mask = 1;
		shift = ((64 - table[i])); //calcula o shift necessário na máscara para ler o bit na posicao table[i]
		mask = mask << shift;	//cria uma máscara para ler o bit na posição table[i]
		readedBit = key & mask; //lê o bit da chave key na posição table[i] 
		//printf("%d - ", 64 - shift);
		//convert_to_binary(mask, 64);

		if(!readedBit)
			continue;

		//printf("%d - ", debug++);
		shift = tam - (i + 1); 
		mask = ((unsigned long long)  1 ) << shift;
		//printf("%d - ", shift);
		//convert_to_binary(mask, 64);
		result |= mask;
		
	}

	return result;
}


// fun‹o para cria‹o de de uma hash a partir dos dados do ficheiro, usando MDC-4
unsigned char* signature(unsigned char* inByteArray, long dim, unsigned long long key)
{
	
}

//fun‹o para verifica‹o da assinatura: verificar se a hash criada a partir dos dados Ž igual ˆ hash recebida
int checkSignature(unsigned char* inByteArray, unsigned char* hash){


}
 /*int * convert_to_binary(int numero, int size){

 	int i;
 	int temp=numero;
 	int *array;
 	array=malloc(size* sizeof(int));
 	for(i=0;i<size;i++){

 		array[size-i-1]=temp%2;
 		temp=temp>>1; // optimização 

 	}



 	return array;

 }
*/
 /*void invert_keys(unsigned long long* chaves){
 	printf("inicio\n");
 	unsigned long long *temp = (unsigned long long*) malloc(sizeof(unsigned long long)*16);
 	printf("te peguei->0x%llx\n", *temp);
 	int i;
 	printf("aqui?\n");
 	for( i = 0; i < 16 ; i++){
 		//printf("estrume\n");
 		//printf("%d", i + 1);
 		temp[i]=chaves[15-i];
 	};
 	//printf("bludrut\n");
 	free(chaves);
 	chaves=temp;



 }*/

//recebe o Ri e uma subchave de 48 bits, devolve uma palavra de 48 bits
unsigned long long f(unsigned long long R, unsigned long long K)
{
    unsigned long long bit_actual, si, siSH, temp1 = 0, temp2 = 0, temp3 = 0; //incializar os temps a 00000000
    int i, c, l;
    //E(Ri-1)
    for (i=0; i<48; i++) {
        bit_actual = (R >> (32-E[i])) & 1; // poe um bit a 1
        temp1 |= bit_actual << (47-i); //or
    }
    //
    //E(ri-1)(+)ki   //basicamente passa se de 32 para 48 para se puder fazer o xor

    temp1 ^= K; //xor

    //S(E(Ri−1)(+) Ki )
    for (i=0; i<8; i++) { //percorrer de 6 em 6 bits
        si = (temp1 >> (48-6*(i+1))) & 0x3F; //valor de b6 (indice 5)
    
        c = (si >> 1) & 0xF; //bits de com os indices de 1 a 4
        l = 2 * (si >> 5) + (si & 1); //determinação de 2bi 

        siSH = S[i][l][c];

        temp2 |= siSH << 4*(7-i); //por na posição e em 32bits  
    }

    for (i=0; i<32; i++) {
        bit_actual = (temp2 >> (32-P[i])) & 1;
        temp3 |= bit_actual << (32-i-1); //P(S(E(Ri−1)(+) Ki ))
    }
    //printf("%llx\n", temp3);
    return temp3;
}

