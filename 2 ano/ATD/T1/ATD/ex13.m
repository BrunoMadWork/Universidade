function [  ] = ex13(  )

ts=0.1;
t=linspace(-pi,pi,500); %gera 500 inteiros entre -pi e pi
x1=2*cos(0)+(3/2)*cos((pi/2)-16*t)+cos(18*t);
%plot da função
subplot(3,1,1);
plot(t,x1);
xlabel('tempo (segundos)');
ylabel('x1(t)');

%defenição dos valores de n
n = -pi:ts :pi; %t=n*ts
x1n=2*cos(0)+(3/2)*cos((pi/2)-16*n)+cos(18*n);

subplot(3,1,2);
plot(n,x1n,'.');
xlabel('tempo(segundos)');
ylabel('x1n(t)');

subplot(3,1,3);
plot(t,x1,n,x1n,'.');
xlabel('tempo (segundos)');
ylabel('x1(t) x1n(t)');
end

