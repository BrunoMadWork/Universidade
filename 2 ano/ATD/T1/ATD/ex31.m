function [ ] = ex31( )
Fs=44100;
t=0:(1/Fs):1;
for f=200:100:18000
xt = sin(2*pi*f*t);
wavplay(xt,Fs,'async');
end