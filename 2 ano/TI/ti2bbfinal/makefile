OBJS = huffman.o gzip.o
CC = g++
DEBUG = -g
CFLAGS = -Wall -c $(DEBUG)
LFLAGS = -Wall  $(DEBUG)

gzip : $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) -o gzip

gzip.o : gzip.h huffman.h gzip.cpp
	$(CC) $(CFLAGS) gzip.cpp

huffman.o : huffman.h huffman.cpp
	$(CC) $(CFLAGS) huffman.cpp

clean:
	\rm *.o *~ test
