function I = inforMutua(Query, Trecho, Alfabeto)

    Ocorrencias = zeros(length(Alfabeto));
    
    s = double(Alfabeto(2) - Alfabeto(1));
    
    for i = 1:length(Query)
        findQuery = (Query(i)-Alfabeto(1))/s + 1;
        findTrecho = (Trecho(i)-Alfabeto(1))/s + 1;
        Ocorrencias(findQuery, findTrecho) = Ocorrencias(findQuery, findTrecho) + 1;
    end
    
    tamanho_q = size(Query);
    tamanho = tamanho_q(1)*tamanho_q(2);
    
    prob = Ocorrencias / tamanho;

    prob(prob == 0) = [];

    % Obter o resultado pela fórmula (note que: H(X,Y) = H(X) + H(Y) - I(X,Y))
    I = Entropia(Query, Alfabeto) + Entropia(Trecho, Alfabeto) + sum(prob.*log2(prob));

end