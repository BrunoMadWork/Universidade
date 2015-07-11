/*Ficheiro que define as funções que manipulam datas (Date) */

#include <stdio.h>
#include <stdlib.h>
#include "Date.h"

/*Cria uma nova data*/
Date newDate(int day, int month, int year) {

    Date date;
    date.day = day;
    date.month = month;
    date.year = year;
    return date;
}

/*Imprime a data recebida como parametro na consola.*/
void printDate(Date* date) {

    printf("%d-%d-%d", date->day,date->month,date->year);
}

/*Indica se o ano que foi recebido como parametro é bissexto ou não*/
int leapYear (int year) {

    int aux = year;
    if (aux%100 == 0) {
        aux = aux/100;
    }
    if (aux%4 == 0) return 1;

    return 0;
}

/*Indica se a data recebida como parametro é uma data valida ou não.
    Uma data é considerada invalida se:
        -O dia for maior do que 31 ou menor do que zero
        -O mês for maior do que 12 ou menor do que zero
        -O ano for menor do que 1
        -O ano não for bissexto, o mês for fevereiro, e o dia for 29
        -O dia for 31 e o mês for fevereiro, abril, junho, setembro ou novembro*/
int isValid (Date* date) {

    int boolean = 1;
    if((date->day > 31) || (date->day <= 0)) boolean = 0;
    if((date->month > 12) || (date->month <= 0)) boolean = 0;
    if(date->year <= 0) boolean = 0;
    if((date->month == 2) && (date->day > 28)) {
        if(date->day > 29) boolean = 0;
        if ((date->day == 29) && (!(leapYear(date->year)))) boolean = 0;
    }
    if (date->day > 30) {
        if((date->month == 2) || (date->month == 4) || (date->month == 6) || (date->month == 9) || (date->month == 11)) {
            boolean = 0;
        }
    }
    return boolean;
}

/*  Compara 2 datas. Compara o ano, se os anos forem iguais compara o mês, se os meses forem
    iguais compara o dia, se os dias forem iguais significa que as datas são iguais e devolve 0

    Devolve:
    -1 se date1 < date2
    0  se date1 = date2
    1  se date1 > date2
*/
int compareDate(Date* date1, Date* date2) {

    if (date1->year < date2->year) return -1;
    else if (date1->year > date2->year) return 1;
    else {
        if(date1->month < date2->month) return -1;
        else if (date1->month > date2->month) return 1;
        else {
            if(date1->day < date2->day) return -1;
            else if(date1->day > date2->day) return 1;
        }
    }
    return 0;
}
