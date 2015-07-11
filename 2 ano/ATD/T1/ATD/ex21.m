function [  ] = ex21(  )
n=-50:50;

function u = unit(n) 
u = zeros(size(n));
u(n>=0) = 1;
end
 
function x = fx(n)
x = 1.5*cos(0.025*pi*n).*(unit(n+40)-unit(n-40));
end

x = fx(n);
y1=0.4*fx(n-2)+0.4*fx(n-3)-0.1*fx(n-4);
y2=0.6*fx(2*n-4);
y3=0.5*fx(n-2).*fx(n-3);
y4=(n-2).*fx(n-3);

subplot(5,1,1);
plot(n,x);
xlabel('n');
ylabel('x(n)');

subplot(5,1,2)
plot(n,x,n,y1);
xlabel('n');
ylabel('y1(n)');

subplot(5,1,3)
plot(n,x,n,y2);
xlabel('n');
ylabel('y2(n)');

subplot(5,1,4)
plot(n,x, n,y3);
xlabel('n');
ylabel('y3(n)');

subplot(5,1,5)
plot(n,x,n,y4);
xlabel('n');
ylabel('y4(n)');
end

