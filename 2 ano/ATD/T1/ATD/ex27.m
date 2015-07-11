function [  ] = ex27(  )


for k=-5:0.01:5
    den = [1 0.4*k 0 0.4*k -0.1*k];
    
    if(all(abs(roots(den)) < 1))
        disp(k);
    end
end



end

