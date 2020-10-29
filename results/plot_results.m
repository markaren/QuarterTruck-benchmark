clc,clear, close all;

libcosim = readmatrix('cosim.csv') ./ 1000;
%fmigo = readmatrix('fmigo.csv') ./ 1000;
%fmpy = readmatrix('fmpy.csv') ./ 1000;
%om = readmatrix('omsimulator.csv') ./ 1000;
vico = readmatrix('vico.csv') ./ 1000;


x = (1:2);
y = [libcosim vico];

figure;
boxplot(y, 0);
title('Gunnerus performance benchmark');
ylabel('Time[s]')
set(gca,'xticklabel', {'libcosim';'vico';})
grid on;

print('figures/performance.eps', '-depsc')
