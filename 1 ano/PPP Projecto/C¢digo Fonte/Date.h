/*Define a estrutura da data e declara as funções que permitem a sua utilização*/

#ifndef DATE_H
#define DATE_H

/*Estrutura Date:
  year - Ano
  month - Mês
  day - Dia
*/
typedef struct dt{
    int year;
    int month;
    int day;
}Date;

/*Cria uma nova data

  Parametros:
  day - Dia
  month - Mês
  year - Ano

  Devolve: Uma nova data.
*/
Date newDate(int day, int month, int year);

/*Imprime a data recebida como parametro na consola*/
void printDate(Date* date);

/*Indica se o ano que foi recebido como parametro é bissexto ou não
    Devolve:
        1 se for bissexto
        0 se não for bissexto
*/
int leapYear (int year);

/*Indica se a data recebida como parametro é uma data valida ou não.
    Devolve:
        1 se a data for valida
        0 se a data não for valida
*/
int isValid (Date* date);

/*  Compara 2 datas.
    Devolve:
    -1 se date1 < date2
    0  se date1 = date2
    1  se date1 > date2
*/
int compareDate(Date* date1, Date* date2);

#endif
