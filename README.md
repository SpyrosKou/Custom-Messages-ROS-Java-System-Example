# [Custom Messages ROS Java System Example](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example.git)

This is an example ROS system running completely on Java with no ROS instalation required that demonstrates the usage of **custom ROS topic messages** and **custom ROS Service definitions**.
It is an example that demonstrates running a roscore and four rosjava ROS nodes that use custom ROS messages using the ROS Java roscore. 
ROS Java contains a plain java, standalone version of roscore that does not require any ROS instalation to be present.

In this repository, a Topic Publisher, a ROS Topic Subscriber, a ROS Service Server and a Service Client are created using the custom ROS messages.
A simpler, version of this repository that uses predefined ROS messages only is available [here](https://github.com/SpyrosKou/Plain-ROS-Java-System-Example.git).
This repository builds on top of the [Plain ROS Java System Example](https://github.com/SpyrosKou/Plain-ROS-Java-System-Example.git).

This example project demonstrates how to do the following **programmatically** through the ROS Java API:
1. Create and use a **reusable java jar library** with the generated ROS Java definitions for **custom ROS messages**
2. Publish and subscribe to a ROS topic that is created with a **custom ROS topic message**.
3. Create a ROS Service Server and call it from a ROS Service Client that is created with a **custom ROS service definition**.
4. Create, start and shutdown the Java roscore
5. Create, start and shutdown ROS nodes


This repository also provides an example that demonstrates how to run rosjava with an another roscore. E.g. cpp roscore from **ROS noetic**. 

## Requirements

In order to compile and run this project only Java needs to be installed. [GIT](https://git-scm.com/downloads) also makes getting the source very easy. Links are provided for convenience.
- [GIT](https://git-scm.com/downloads) , in order to clone the [project repository](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example.git)
- JDK, the [project](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example.git) has been developed and tested using [JDK 17](https://jdk.java.net/17/)
- Gradle, is used for project compilation. Installing it is not required. Following the instructions below will automatically download gradle 7.5 and ignore any existing installation.

## Quick Instructions for Windows [Power Shell](https://github.com/PowerShell/PowerShell/releases/)
1. [Clone](https://git-scm.com/docs/git-clone) the [project repository](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example.git):
`git clone https://github.com/SpyrosKou/Plain-ROS-Java-System-Example.git`

2. Go into the cloned repository directory:
`cd .\Custom-Messages-ROS-Java-System-Example\`

3. Compile the project and prepare for running:
`gradlew installDist`

5. Build and run in a single command using the [gradle application plugin](https://docs.gradle.org/current/userguide/application_plugin.html):
`gradlew run`

## Quick Instructions for Linux
1. [Clone](https://git-scm.com/docs/git-clone) the [project repository](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example.git):
`git clone https://github.com/SpyrosKou/Plain-ROS-Java-System-Example.git`

2. Go into the cloned repository directory:
`cd Custom-Messages-ROS-Java-System-Example/`

3. [Add execute permission](http://manpages.ubuntu.com/manpages/focal/man1/chmod.1.html) to gradlew script:
`sudo chmod +x gradlew`

4. Compile the project and install it locally:
`gradlew installDist`

6. Build and run in a single command using using the [gradle application plugin](https://docs.gradle.org/current/userguide/application_plugin.html):
`gradlew run`

## Using with an existing, external, non-rosjava roscore
It is possible to use rosjava to run rosjava nodes in an environment where a ros system is already running. E.g. a cpp noetic ros instance.
An example on how to run rosjava nodes programmatically without starting roscore is provided in [MainExternal](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example/blob/ros-app/main/src/main/java/eu.spyros.koukas.ros.examples/MainExternal.java)
Note that if a roscore is not running, then this example will not run correctly.
In order to use it do the following:

1. Set `ROS_MASTER_URI` and `ROS_IP` environment variables. Both these variables are needed. If these environment variables are missing, you will see some errors in step 4 below.
The following commands assumes the example and roscore run in `127.0.0.1` aka `localhost`.   
`export ROS_MASTER_URI=http://127.0.0.1:11311`   
`export ROS_IP=http://127.0.0.1:11311`   

2. Start roscore
Run the following:
`roscore`   

3. With the roscore already started externaly run [MainExternal](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example/blob/ros-app/main/src/main/java/eu.spyros.koukas.ros.examples/MainExternal.java)
4. You can start directly the [MainExternal](https://github.com/SpyrosKou/Custom-Messages-ROS-Java-System-Example/blob/ros-app/main/src/main/java/eu.spyros.koukas.ros.examples/MainExternal.java) from the gradle by running:
`./gradlew runWithExternalRos`

