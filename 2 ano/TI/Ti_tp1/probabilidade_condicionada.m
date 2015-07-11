function [prob] = probabilidade_condicionada(fonte, objecto)
n_ocorrencias = 0;
[a,b] = size(fonte); %a=numero de linhas ; b=numero colunas
for i=1:a
    for j=1:2:b-1
        if((fonte(i,j) == objecto(i,1))&&(fonte(i,j+1)==objecto(i,2))) %verificar se o objecto ? igual a posi?ao da matriz
            n_ocorrencias = n_ocorrencias + 1; %incrementa caso seja
        end
    end 
end
    
    prob=(n_ocorrencias/((a*b)/2)); %calcula a probabilidade do objecto na fonte
end
