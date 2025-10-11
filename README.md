# 2025-2026-I2RC-Preseason-
2025-2026 Intro to robot control preseason curriculum

# How Branches Will Work this Year
You will make a branch (only once; NOT each time a week) and this where you will push code every week. Name your branch off of main: FirstnameLastinitial (Ex. NishkS)

Path: C:\Users\Ryan He\Documents\GitHub\2025-2026-I2RC-Preseason-\2025-26-TankDrive

# 10/11:

## Advantage Scope:
Advantage scope shows data over time.

## Shuffleboard:
Shuffleboard shows the individual points of data.
<br>
<hr>
<br>

## Electrical:

### Battery:
Provides power to entire robot

### Radio: 
Allows computers to connect to robot via WIFI. Connections: RoboRIO VRM

### VRM:
Voltage regulator module, connects to pdp and provides stable voltage and current to devices Connections: PDP

### PDP:
Power distribition panel, distributes power to smaller components. Connections: Battery VRM CAN Talon srx spark max

### Talon SRX:
Motor controller which converts CAN instructions to motor instructions Connections: CAN PDP

### MCB
On/Off switch for robot. Connections: Battery PDP

### RoboRIO
main computer of robot. Connections: CAN RSL RADIO VRM PDP

### RSL
Radio Signal Light, warns if robot is active. Connections: RoboRIO


 