## This is a sample CMakeLists.txt, similar to what you might find in a ROS interfaces Package
## This file is completely ignored by ROS Java, but is here to demonstrate that you can use an existing ROS interfaces package, as is, to generate ROS Java artifacts.
## @author Spyros Koukas

cmake_minimum_required(VERSION 2.8.3)
project(custom_msgs)
find_package(catkin
  REQUIRED COMPONENTS
  actionlib_msgs
  std_msgs
  geometry_msgs
  message_generation
  sensor_msgs
  )

add_message_files(
  DIRECTORY msg
)

 add_service_files(
  DIRECTORY srv
 )

add_action_files(
  DIRECTORY action
)

generate_messages(
   DEPENDENCIES
   actionlib_msgs
   std_msgs
   geometry_msgs
   sensor_msgs
 )


catkin_package(
  CATKIN_DEPENDS actionlib_msgs std_msgs message_runtime geometry_msgs sensor_msgs
)


include_directories(
)

