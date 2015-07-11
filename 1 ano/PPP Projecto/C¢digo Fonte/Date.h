/*Define a estrutura da data e declara as fun��es que permitem a sua utiliza��o*/

#ifndef DATE_H
#define DATE_H

/*Estrutura Date:
  year - Ano
  month - M�s
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
  month - M�s
  year - Ano

  Devolve: Uma nova data.
*/
Date newDate(int day, int month, int year);

/*Imprime a data recebida como parametro na consola*/
void printDate(Date* date);

/*Indica se o ano que foi recebido como parametro � bissexto ou n�o
    Devolve:
        1 se for bissexto
        0 se n�o for bissexto
*/
int leapYear (int year);

/*Indica se a data recebida como parametro � uma data valida ou n�o.
    Devolve:
        1 se a data for valida
        0 se a data n�o for valida
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
