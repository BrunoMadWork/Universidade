function vecI = vectorInformacaoMutua(Query, Target, Alfabeto, step)

    %Arrendonda para cima
    tamanhoVecI = floor((length(Target)-length(Query))/step) + 1;
    %Coloca tudo a zero no vector
    vecI = zeros(1, tamanhoVecI);

    p = 1;
    for i = 1:tamanhoVecI
        vecI(i) = inforMutua(Query, Target(p:p+length(Query)-1), Alfabeto);
        p = p + step;
    end

end