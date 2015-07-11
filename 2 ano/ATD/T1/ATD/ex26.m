function [  ] = ex26( )
Hz = ztrans (sym ('0.4 * kroneckerDelta(n-1) + 0.4 * kroneckerDelta(n-3) - 0.1 * kroneckerDelta(n-4)'));
disp(Hz);

end

