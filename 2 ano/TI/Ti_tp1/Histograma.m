function ocorrencias = Histograma( Simbolos, Alfabeto )
%Dado um simboloa P e um alfabeto A mostra o histograma de ocorrencia

ocorrencias = zeros(1, length(Alfabeto));%pre alocacao para garantir velocidade
for k = 1 : length(Alfabeto)
    ocorrencias(k) = length(find(Simbolos == Alfabeto(k))); %find devolve array com os indices de cada ocorrencia
end
 
 bar(Alfabeto,ocorrencias);
 xlabel('Simbolos');
 ylabel('Ocorrências');
 title('Histograma de Ocorrências');

end
