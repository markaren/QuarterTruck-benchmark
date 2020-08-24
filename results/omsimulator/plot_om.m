clc,clear, close all;

ref = readmatrix("ref.csv");
t_ref = ref(:,1);
idx = find(ref(:,1) == 5);
t_ref = t_ref(1:idx);
chassis_ref = ref(:,2);
chassis_ref = chassis_ref(1:idx);
wheel_ref = ref(:,3);
wheel_ref = wheel_ref(1:idx);

omsimulator = readmatrix("results.csv");
t_omsimulator = omsimulator(:,1);
chassis_omsimulator = omsimulator(:,99);
wheel_omsimulator = omsimulator(:,46);


%% Chassis

figure;
hold on;
grid on;
title("zChassis")
plot(t_ref, chassis_ref)
plot(t_omsimulator(:,1), chassis_omsimulator)

legend("ref", "om");
legend('Location', 'southeast')

%% Wheel

figure;
hold on;
grid on;
title("zWheel")
plot(t_ref, wheel_ref)
plot(t_omsimulator(:,1), wheel_omsimulator)

legend("ref","om")
legend('Location', 'southeast')
