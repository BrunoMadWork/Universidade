function [  ] = ex25(  )


n = -50:50;


h = 0.4 * Dirac(n-2) + 0.4 * Dirac(n-3) - 0.1 * Dirac(n-4);

stem(n, h); %imprime grafico discreto com pontos e traços da origem a ligarem nos com a mesma

function [ x ] = Dirac( n )
    
    x = zeros(size(n));
    
    for i=1:1:length(n)
        if(n(i)==0)
            x(i)=1;
        end
    end
    
   %se um elemento de n for 0, o x com o mesmo indice fica a 1

end
end

