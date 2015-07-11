function [prob] = Probabilidade(fonte, objecto)
n_ocorrencias = 0;
[x,y] = size(fonte); %x=numero de linhas ; y=numero colunas
for i=1:x
    for j=1:y
        if((fonte(i,j) == objecto(i,1))) %verificar se o objecto é igual a posiçao da matriz
            n_ocorrencias = n_ocorrencias + 1; %incrementa caso seja
        end
    end 
end
    
    prob=(n_ocorrencias/((x*y))); %calcula a probabilidade do objecto na fonte
end