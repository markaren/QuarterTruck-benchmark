clc,clear, close all;

ref = readmatrix("/ref.csv");
t_ref = ref(:,1);
idx = find(ref(:,1) == 5);
t_ref = t_ref(1:idx);
chassis_ref = ref(:,2);
chassis_ref = chassis_ref(1:idx);
wheel_ref = ref(:,3);
wheel_ref = wheel_ref(1:idx);

chassis_cosim = readmatrix("libcosim/chassis.csv");
wheel_cosim = readmatrix("libcosim/wheel.csv");

chassis_vico = readmatrix("vico/chassis.csv");
wheel_vico = readmatrix("vico/wheel.csv");

chassis_fmpy = readmatrix("fmpy/chassis.csv");
wheel_fmpy = readmatrix("fmpy/wheel.csv");

fmigo = readmatrix("fmigo/results.csv");
t_fmigo = fmigo(:,1);
chassis_fmigo = fmigo(:,3);
wheel_fmigo = fmigo(:,6);

omsimulator = readmatrix("omsimulator/results.csv");
t_omsimulator = omsimulator(:,1);
chassis_omsimulator = omsimulator(:,99);
wheel_omsimulator = omsimulator(:,46);


%% Chassis

figure;
hold on;
grid on;
title("zChassis")
plot(t_ref, chassis_ref)
plot(t_fmigo, chassis_fmigo)
plot(chassis_fmpy(:,1), chassis_fmpy(:,2))
plot(chassis_vico(:,1), chassis_vico(:,3))
plot(chassis_cosim(:,1), chassis_cosim(:,3))
plot(t_omsimulator(:,1), chassis_omsimulator)

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")

%% Wheel

figure;
hold on;
grid on;
title("zWheel")
plot(t_ref, wheel_ref)
plot(t_fmigo, wheel_fmigo, '-*')
plot(wheel_fmpy(:,1), wheel_fmpy(:,2), '-.')
plot(wheel_vico(:,1), wheel_vico(:,3), '-o')
plot(wheel_cosim(:,1), wheel_cosim(:,3), '-x')
plot(t_omsimulator(:,1), wheel_omsimulator, '-+')

legend("ref", "fmigo", "fmpy", "vico", "libcosim", "om")
