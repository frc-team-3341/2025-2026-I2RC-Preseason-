# 2025-2026-I2RC-Preseason-
2025-2026 Intro to robot control preseason curriculum

# How Branches Will Work this Year
You will make a branch (only once; NOT each time a week) and this where you will push code every week. Name your branch off of main: FirstnameLastinitial (Ex. NishkS)

Path: /Users/akshaya_b/Documents/GitHub/2025-2026-I2RC-Preseason-/2025-26-TankDrive/.gradle

Last Week
ShuffleBoard: Gives real-time data with variables, as well as their values. ShuffleBoard is more useful to debug small issues quickly. 
AdvantageScope: Gives real-time data along with graphs that can help visualize logistics. It allows you to
see how the motors work (with graphs)

10/11/2025
Radio: Transfers signal to the Roborio via Ethernet and it has 4 LEDs to indicate its signal and power
VRM: It's a mini PDP for components that need lower currents. It's important because PDP outputs can have
too much current for some compnents (ex. radio)
PDP: Distributes power from batter to the other components (ex. RoboRio, VRM, MCB, and all motors)
Battery: Provides current and power without overheating; powers robot
Talon SRX Motor Controller: gives power and directions for the motor that is wired | it receives code from 
the CAN wires
MCB: It's the main off and on switch and protects compenents by breaking the circut if there is too much current
RoboRio: Brain of the robot | V is voltage, C is calm (ground) | CAN: high is yellow and low is green
Robot Signal Light: Lights up to show robot is on (connected to RoboRio)