
FLAGS  = -Wall -g -lcrypt
CC     = gcc
PROG   = projso
OBJS   = main.o desencriptar.o

all:	${PROG}

clean:
	rm ${OBJS} ${PROG} *~
  
${PROG}:	${OBJS}
	${CC} ${FLAGS} ${OBJS} -o $@

.c.o:
	${CC} ${FLAGS} $< -c

##########################

desencriptar.o:  desencriptar.h desencriptar.c

main.o: desencriptar.h main.h main.c

projso: main.o desencriptar.o

