/*Define a estrutura de uma lista ligada de jogos e declara as funções que permitem a sua utilização*/

#ifndef LINKEDGAME_H
#define LINKEDGAME_H

#include "Game.h"

/*ITEM_TYPE = Game*/

typedef struct game_lnode *GameList;

typedef struct game_lnode {
    Game info;
    GameList next;
} ListGame_node;

/*Cria uma lista ligada de jogos vazia*/
GameList createGameList ();

/*Destroi a lista ligada de jogos recebida como parametro libertando a memória que estava alocada para cada um dos seus nós*/
GameList destroyGameList (GameList list);

/*Verifica se a lista ligada de jogos está vazia*/
int gameListIsEmpty (GameList list);

/*Verifica se a lista ligada de jogos está cheia (devolve sempre falso)*/
int gameListIsFull (GameList list);

/*Insere um jogo na lista ligada de jogos recebida como parametro*/
int  insertGame (GameList list, Game item);

/*Pesquisa na lista ligada de jogos o jogo "key" e devolve o "nó" onde o jogo está guardado e o "nó" imediatamente anterior*/
void searchGame (GameList list, Game key, GameList *previous, GameList *node);

/*Devolve o tamanho da lista ligada de jogos recebida como parametro*/
int gameListSize (GameList list);

/*Apaga o jogo recebido como parametro da lista ligada de jogos libertando o espaço alocado para o respectivo nó*/
void deleteGame (GameList list, Game item);

/*Imprime a lista ligada de jogos recebida como parametro*/
void printGameList (GameList list);

#endif
