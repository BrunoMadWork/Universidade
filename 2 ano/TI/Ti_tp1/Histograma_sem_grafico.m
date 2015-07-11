function ocorrencias = Histograma_sem_grafico( Simbolos, Alfabeto )
%Devolve um array com o n�mero de ocorr�ncias de cada s�mbolo do alfabeto
%dado

ocorrencias = zeros(1, length(Alfabeto));
for k = 1 : length(Alfabeto)
    ocorrencias(k) = length(find(Simbolos == Alfabeto(k)));
end
 
% bar(Alfabeto,ocorrencias);
% xlabel('Simbolos');
% ylabel('Ocorr�ncias');
% title('Histograma de Ocorr�ncias');

end
