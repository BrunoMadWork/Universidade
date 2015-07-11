function ocorrencias = Histograma_sem_grafico( Simbolos, Alfabeto )
%Devolve um array com o número de ocorrências de cada símbolo do alfabeto
%dado

ocorrencias = zeros(1, length(Alfabeto));
for k = 1 : length(Alfabeto)
    ocorrencias(k) = length(find(Simbolos == Alfabeto(k)));
end
 
% bar(Alfabeto,ocorrencias);
% xlabel('Simbolos');
% ylabel('Ocorrências');
% title('Histograma de Ocorrências');

end
