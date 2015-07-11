function resultado = Entropia( Simbolos, Alfabeto )
%determina o limite m�nimo te�rico para o n�mero m�dio de bits por s�mbolo
%dado Simbolos e um alfabeto

     
[m,n] = size(Simbolos); %dimensoes da fonte de entrada
probabilidade = Histograma_sem_grafico(Simbolos,Alfabeto)/ (m*n); %m*n dao o numero de ocorrencias

% P (P ~= 0); Este m�todo devolve um array 1*n contendo todos os n n�meros
% do array P original que n�o s�o iguais a zero

resultado = -sum((probabilidade(probabilidade ~= 0)).* log2(probabilidade(probabilidade ~= 0)));

end
