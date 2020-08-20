clc,clear, close all;

x = (1:5);
y = [73.4; 81.1; 37.4; 172; 45];

figure;
bar(x,y);
set(gca,'xticklabel', {'libcosim';'fmpy';'vico';'om';'fmigo'})

ylabel('Time[s]')

%legend('100hz', '1000hz')