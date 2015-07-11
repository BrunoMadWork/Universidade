function resultado = Entropia( Simbolos, Alfabeto )
%determina o limite mínimo teórico para o número médio de bits por símbolo
%dado Simbolos e um alfabeto

     
[m,n] = size(Simbolos); %dimensoes da fonte de entrada
probabilidade = Histograma_sem_grafico(Simbolos,Alfabeto)/ (m*n); %m*n dao o numero de ocorrencias

% P (P ~= 0); Este método devolve um array 1*n contendo todos os n números
% do array P original que não são iguais a zero

resultado = -sum((probabilidade(probabilidade ~= 0)).* log2(probabilidade(probabilidade ~= 0)));

end
