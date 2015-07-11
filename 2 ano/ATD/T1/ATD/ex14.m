function [  ] = ex14(  )

t=linspace(-pi,pi,500); %gera 500 inteiros entre -pi e pi
x1=2*cos(0)+(3/2)*cos((pi/2)-16*t)+cos(18*t);

%regra do trapezio TRAPZ
a=trapz(t,x1);

syms x;
func=@(x) 2*cos(0)+(3/2)*cos((pi/2)-16*x)+cos(18*x);
b=quad(func,-pi,pi);



t=trapezio(-pi,pi,56); %56
s=simpson(-pi,pi,82);  %82
fprintf('trapezio pela defenição do matlab:,%f\n',a)
fprintf('trapezio pela nossa função:%f\n',t)
fprintf('simpson pela defenição do matlab:%f\n',b)
fprintf('simpson pela nossa função:%f\n',s)
%regra do trapezio quadrado

end

function[somatorio]=trapezio(a,b,n) %valores de x, f é a função 
t=linspace(a,b,n);
x1=2*cos(0)+(3/2)*cos((pi/2)-16*t)+cos(18*t);
deltax=((b-a)/n)/2;
somatorio=0;
%ciclo da soma
for i=1:1:length(t)
   if((i==1)||i==length(t))
       somatorio=somatorio+abs(x1(i));
   
   else
         somatorio=somatorio+2*abs(x1(i));       
     
           
   end
    
end
somatorio=somatorio*deltax;
end
function[somatorio]=simpson(a,b,n)

t=linspace(a,b,n);
x1=2*cos(0)+(3/2)*cos((pi/2)-16*t)+cos(18*t);
h=((b-a)/n);
somatorio=0;
%ciclo da soma
for i=1:1:length(t)
   if((i==1)||i==length(t))
       somatorio=somatorio+abs(x1(i));
   elseif(mod(i,2)==0) %par
       somatorio=somatorio+4*abs(x1(i));
   
   elseif(mod(i,2)~=0) %impar
         somatorio=somatorio+2*abs(x1(i));       
     
           
   end
    
end
somatorio=somatorio*(h/3);
end

