# 2025-2026-I2RC-Preseason-
2025-2026 Intro to robot control preseason curriculum

# How Branches Will Work this Year
You will make a branch (only once; NOT each time a week) and this where you will push code every week. Name your branch off of main: FirstnameLastinitial (Ex. NishkS)

Explain Shuffleboard, advantagescope, electrical presentation.

Electrical presentation:
-A radio connects to the computer to receive code. It then transfers signal to the RoboRIO via ethernet cable.There are 4 different LEDs to indicate signal and power. Red meanns there is power but not connected. Orange means not being used which is okay. Green is connected and has power.
-Voltage Regulator Module(VRM)
Like a mini PDP that also connects to object. Some electrical can only handle a small amounts of current. PDP supplies a bigger current.Mainly powers the radio.
-Power distributor Panel(PDP)
Distributes power from the battery to components. It connects to the RoboRIo, VRM, MCB, PCM, and others. 
-Battery 
Can provide current without overheating. It is the only legal way to power robots in FRC competitions.
TalonSRX Motor Controller 
Provides forward directios for the motor that is wired to the side. Red and black wires are connected to the PDP. 
-Main circuit Breaker(MCB)
The main on/off switch of the robot. It protects all compotnetns by breaking the circuit. It is connected to the PDP and positive terminals.
-RoboRIO 
Main robot controller, "brain of the robot". It connecctts to the PDP, radio (via ethernet). TAPE UNUSED PORTS FOR NO FOD. 
Robot Signal Light
Lights up to show its on. RSL part connects to RoboRIO. 

SHUFFLEBOARDs
Is a way to display live data. You can have many tabs including your own personal tabs, for example a drive train tab. Shuffleboard is less complex than advantagescope.

ADVANTAGESCOPE
It is more complex than Shuffleboard. It can be used graph data and also simulate the environment that is around the robot. To connect to the robot we must click file and then hit connect robot.