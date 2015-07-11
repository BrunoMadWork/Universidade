function [soma  ] = ex15(  )
ts=0.1;

n=-pi:ts:pi;
x1n=2*cos(0)+(3/2)*cos((pi/2)-16*n)+cos(18*n);
soma= sum(abs(x1n).^2)*ts;


end

