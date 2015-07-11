/*Define a estrutura de uma lista ligada de jogos e declara as fun��es que permitem a sua utiliza��o*/

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

/*Destroi a lista ligada de jogos recebida como parametro libertando a mem�ria que estava alocada para cada um dos seus n�s*/
GameList destroyGameList (GameList list);

/*Verifica se a lista ligada de jogos est� vazia*/
int gameListIsEmpty (GameList list);

/*Verifica se a lista ligada de jogos est� cheia (devolve sempre falso)*/
int gameListIsFull (GameList list);

/*Insere um jogo na lista ligada de jogos recebida como parametro*/
int  insertGame (GameList list, Game item);

/*Pesquisa na lista ligada de jogos o jogo "key" e devolve o "n�" onde o jogo est� guardado e o "n�" imediatamente anterior*/
void searchGame (GameList list, Game key, GameList *previous, GameList *node);

/*Devolve o tamanho da lista ligada de jogos recebida como parametro*/
int gameListSize (GameList list);

/*Apaga o jogo recebido como parametro da lista ligada de jogos libertando o espa�o alocado para o respectivo n�*/
void deleteGame (GameList list, Game item);

/*Imprime a lista ligada de jogos recebida como parametro*/
void printGameList (GameList list);

#endif
