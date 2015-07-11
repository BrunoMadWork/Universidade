/*Ficheiro que define as funções que manipulam uma lista ligada de jogos (GameList)*/

#include <stdio.h>
#include <stdlib.h>
#include "LinkedGame.h"

/*ITEM_TYPE = Game*/

/*Cria uma lista ligada de jogos vazia alocando espaço para um nó especial que não guarda informação nenhuma (header)*/
GameList createGameList (void) {

    GameList aux;
    aux = (GameList) malloc (sizeof (ListGame_node));

    if (aux != NULL) {
        aux->info = 0;
        aux->next = NULL;
    }

    return aux;
}

/*Destroi a lista ligada de jogos recebida como parametro libertando a memória que estava alocada para cada um dos seus nós*/
GameList destroyGameList (GameList list) {

    if (list->next == NULL) {
        free(list);
        return NULL;
    } else {
        destroyGameList(list->next);
        free(list);
    }
    return NULL;
}

/*Verifica se a lista ligada de jogos está vazia*/
int gameListIsEmpty (GameList list) {
    return (list->next == NULL ? 1 : 0);
}

/*Verifica se a lista ligada de jogos está cheia (devolve sempre falso pois uma lista ligada nunca fica cheia enquanto houver memória no computador)*/
int gameListIsFull (GameList list) {
    return 0;
}

/*Pesquisa na lista ligada de jogos a posição em que se pode inserir o jogo "key".
  Devolve o nó que contém o jogo com a data mais próxima da data de “key” mas contudo inferior a esta*/
GameList searchPositionToInsert (GameList list, Game key) {

    GameList aux = list;
    while ((aux->next) != NULL && (compareGameByDate((aux->next->info), key) == -1)) {
        aux = aux->next;
    }
    return aux;
}

/*Pesquisa na lista ligada de jogos o jogo "key" e devolve o "nó" onde o jogo está guardado e o "nó" imediatamente anterior*/
void searchGame (GameList list, Game key, GameList *previous, GameList *node) {
    *previous = list;
    *node = list->next;
    while ((*node) != NULL && ((*node)->info != key)) {
        *previous = *node;
        *node = (*node)->next;
    }
}

/*Insere um jogo na lista ligada de jogos recebida como parametro.
    Devolve:
        0 se ocorreu um erro
        1 se a função executou normalmente
*/
int insertGame (GameList list, Game item) {
    GameList node;
    GameList previous;
    node = (GameList) malloc (sizeof (ListGame_node));
    if (node != NULL) {
        node->info = item;

        previous = searchPositionToInsert(list, item);

        node->next = previous->next;
        previous->next = node;
        return 1;
    }
    return 0;
}

/*Devolve o tamanho da lista ligada de jogos recebida como parametro*/
int gameListSize(GameList list) {

    int count = 0;
    GameList aux = list;
    while(aux->next != NULL) {
        aux = aux->next;
        count++;
    }
    return count;
}

/*Apaga o jogo recebido como parametro da lista ligada de jogos libertando o espaço alocado para o respectivo nó*/
void deleteGame (GameList list, Game item) {
    GameList previous;
    GameList node;
    searchGame (list, item, &previous, &node);
    if (node != NULL) {
        previous->next = node->next;
        free (node);
    }
}

/*Imprime a lista ligada de jogos recebida como parametro*/
void printGameList(GameList list) {

    GameList aux = list->next; /* Salta o header */

    if(gameListIsEmpty(list)) {
        printf("Nao existem jogos na base de dados\n");
    } else {
        while (aux) {
            printGame(aux->info);
            printf("\n");
            aux=aux->next;
        }
    }
}
