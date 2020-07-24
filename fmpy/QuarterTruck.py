"""
Example that demonstrates the simulation of SSPs (System Structure and Parameterization)
"""

from fmpy.ssp.simulation import simulate_ssp
from fmpy.util import plot_result, write_csv

step_size = 0.01
stop_time = 5


def simulate_quarter_truck(show_plot=False):
    """Simulate QuarterTruck.ssp

    Parameters:
        show_plot     plot the results
    """

    ssp_filename = r'QuarterTruck_FMPy.ssp'

    print("Simulating QuarterTruck_FMPy...")
    result = simulate_ssp(ssp_filename, stop_time=stop_time, step_size=step_size, parameter_set=None)

    if show_plot:
        print("Plotting results...")
        plot_result(result, names=['chassis.zChassis', 'wheel.zWheel', 'ground.zGround'], window_title=ssp_filename)

    write_csv(r'..\results\fmpy\chassis.csv', result, columns=['chassis.zChassis'])
    write_csv(r'..\results\fmpy\wheel.csv', result,columns=['wheel.zWheel'])

    print('Done.')

    return result


if __name__ == '__main__':

    simulate_quarter_truck()
