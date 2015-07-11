
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
int IP[64] = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};
int E[48] = {32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
int P[32] = {16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};
int S[8][4][16] = {{{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}},
                   {{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}},
                   {{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}},
                   {{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}},
                   {{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}},
                   {{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}},
                   {{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}},
                   {{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}}};
int IPreverse[64] = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
char DEBUG = (char) 1;

void DESKeySchedule(unsigned long long key, unsigned long long* subKeys);
unsigned long long exchange(unsigned long long key, unsigned int table[], int tam);
unsigned long long encryptDESplain(unsigned long long plain, unsigned long long* subKeys);
unsigned long long f(unsigned long long R, unsigned long long K);
//_Bool* bitVector(unsigned long long value, int len); //método não usado
//unsigned long long * convert_to_binary(unsigned long long numero, int size); //método para debug

int main() {

    unsigned long long plain = 0x1f8b0808b8f7a435;
    unsigned long long subkeys[16];
    DESKeySchedule(0x0123456789ABCDEF, subkeys);
    encryptDESplain(plain, subkeys);

    return 0;
}

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
    printf("L0->%llx\nR0->%llx\n", li, ri);


    for (i=0; i<16; i++) {
        temp = ri;
        ri = li ^ f(ri, subKeys[i]); //leva com a funcao 16 vezes com chaves diferentes
        li = temp;
        printf("L%d->%llx\nR%d->%llx\n", i, li, i, ri);
    }
    printf("L%d->%llx\nR%d->%llx\n", i, li, i, ri);

    plainConcat = (ri << 32) | li;


    for (i=0; i<64; i++) {
        bit_actual = (plainConcat >> (64-IPreverse[i])) & 1;
        plainF |= bit_actual << (64-i-1);
    }

    printf("Cifra->%llx\n", plainF);

    return plainF;

}

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
        mask = mask << shift;   //cria uma máscara para ler o bit na posição table[i]
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