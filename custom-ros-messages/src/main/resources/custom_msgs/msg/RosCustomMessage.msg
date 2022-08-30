# A custom ROS topic message to demonstrate usage through Java.
# It also demonstrates how to use existing standard ROS messages by using sensor_msgs/jointState.
# @author Spyros Koukas


string text
bool enabled
sensor_msgs/JointState jointState
uint64 millis
uint32 count