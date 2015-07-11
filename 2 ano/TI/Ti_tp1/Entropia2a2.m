function resultado = Entropia2a2(P, A)
    %Determina as ocorrencias dado um determinado alfabeto
    %------------------------------
    Ocorrencias = zeros(length(A));
    
%     disp(Ocorrencias);
    
    tamanho_p = size(P);
    tamanho = tamanho_p(1)*tamanho_p(2);
    if (mod(tamanho,2) == 1)
        tamanho = tamanho - 1;
    end
    
    %Transforma numa linha...
    P = P';  
    
    for i = 1:2:tamanho
        findFirst = find(A == P(i));
        findSecond = find(A == P(i+1));
        Ocorrencias(findFirst, findSecond) = Ocorrencias(findFirst, findSecond) + 1;
    end
    %Calcula a entropia conjunta , com base nas ocorrencias acima
    %encontradas
    %---------------------------------------
    
    prob = Ocorrencias /(tamanho/2);
    
    % Remover os 0 das probabilidades (porque não existe logaritmo de 0):
    prob(prob == 0) = [];
    
    % Calcular e devolver a entropia:
    resultado = - sum ((prob) .* log2(prob));
    resultado = sum (resultado);
    
end