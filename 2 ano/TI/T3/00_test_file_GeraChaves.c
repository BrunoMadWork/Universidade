#include <stdio.h>
#include <stdlib.h>


#define MASCARA_28 268435455 // Máscara com os os primeiros 38 bits menos significativos a 1

/*Este código está em Ansi, especificação C99*/
/************************************************************************************************/
/****************************************WARNING*************************************************/
/*********************ESTE ARQUIVO CONTÉM MAIS COMENTÁRIOS DO QUE CÓDIGOD************************/
/************************************************************************************************/

unsigned int PC1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
unsigned int PC2[] = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
char DEBUG = (char) 1;

void DESKeySchedule(unsigned long long key, unsigned long long* subKeys);
unsigned long long exchange(unsigned long long key, unsigned int table[], int tam);
//_Bool* bitVector(unsigned long long value, int len); //método não usado
//unsigned long long * convert_to_binary(unsigned long long numero, int size); //método para debug


int main() 
{
	unsigned long long chave = 0x0123456789ABCDEF;
	unsigned long long *subkeys = (unsigned long long*) malloc(sizeof(unsigned long long)*16);
	DESKeySchedule(chave, subkeys);


	free(subkeys);
	return 0;
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
		printf("subkey -> %llx\n", aux);
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


unsigned long long * convert_to_binary(unsigned long long numero, int size){

 	int i;
 	unsigned long long temp=numero;
 	unsigned long long *array;
 	array=malloc(size* sizeof(unsigned long long));
 	for(i=0;i<size;i++){

 		array[size-i-1]=temp%2;
 		temp=temp>>1; // optimização 

 	}
 	for(i = 0; i < size; i++) {
 		printf("%llu", array[i]);
 	}
 	printf("\n");



 	return array;

 }

 /*Função que lê um número e passa os bits 
  desse número para um array de chars*/
/*_Bool* bitVector(unsigned long long value, int len)
{
	_Bool* out;
	int mask = 1;
	int i;

	out = (_Bool*) malloc(sizeof(_Bool)*(len+1)*8);
	
	for (i = 0; i < len; i++)
	{
		out[len-i-1] = ((_Bool)((value >> i) & mask)) + '0';
	}
	out[len] = 0;
	
	return out; 
}*/