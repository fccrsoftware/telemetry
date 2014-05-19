FCCR Android Telemetry
=========

An Android application with the primary intention of aiding the development of control systems on linux platforms. The process is outlined as follows:
  - Two devices are connected through any TCP-IP link (WLAN or Internet). First is a remote linux computer, typically controlling robot electrics. Second is a local android device in possession of the human user.
  - The remote device contains an arbitrary list of telemetry variables for software or hardware analysis. Each variable can have a read & write or read only permission. Also contained on the robot is a set of functions presented to the user for control or configuration.
  - The local device reads the arbitrary list of values and presents them to the user in a friendly interface which is not dependent on the specific data presented by the robot.

The intention of this specific process is to decouple the robot's design from the user application function and design from the user application's functions. The same user application should be usable for telemetry and basic control of any arbitrary linux robot such as BeagleMIP, BeagleQuad, and BeagleRover.