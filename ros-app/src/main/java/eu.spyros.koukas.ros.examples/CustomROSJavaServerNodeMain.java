/**
 * Copyright 2020 Spyros Koukas
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.spyros.koukas.ros.examples;

import com.google.common.base.Preconditions;
import custom_msgs.CustomAddition;
import custom_msgs.CustomAdditionRequest;
import custom_msgs.CustomAdditionResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceServer;

/**
 * An object of this class will be a ROS Node with the given rosNodeName that will server a specific service at the specified graph name
 * Created at 2022-08-30
 *
 * @author Spyros Koukas
 */
public final class CustomROSJavaServerNodeMain extends AbstractNodeMain {
    private final String rosServiceName;
    private final String rosNodeName;
    private final long SLEEP_DURATION_MILLIS = 1000;
    private Log log;

    /**
     * @param rosServiceName the name of the topic to publish
     * @param rosNodeName    the name of the node
     */
    public CustomROSJavaServerNodeMain(final String rosServiceName, final String rosNodeName) {
        //Let's require that rosNodeName and rosService are not null to eagerly identify this error
        //These checks are completely optional
        Preconditions.checkArgument(StringUtils.isNotBlank(rosNodeName));
        Preconditions.checkArgument(StringUtils.isNotBlank(rosServiceName));

        this.rosServiceName = rosServiceName;
        this.rosNodeName = rosNodeName;
    }

    /**
     * @return
     */
    @Override
    public final GraphName getDefaultNodeName() {
        return GraphName.of(this.rosNodeName);
    }

    /**
     * This method is called every time the ROS Service is called.
     * Note that the response is provided and is modified by the service server logic
     *
     * @param customAdditionRequest  this object contains the request of the service client, this is defined by a custom message
     * @param customAdditionResponse this object is provided by rosjava to be modified by the service logic and is then returned to the client, this is defined by a custom message
     */
    private final void createResponse(final custom_msgs.CustomAdditionRequest customAdditionRequest, final custom_msgs.CustomAdditionResponse customAdditionResponse) {
        //Perform the addition
        final int sum = customAdditionRequest.getNumberA() + customAdditionRequest.getNumberB();
        final String text = customAdditionRequest.getText();

        //write the result back to the provided response
        customAdditionResponse.setResult(sum);
        customAdditionResponse.setSuccess(true);
        customAdditionResponse.setDescription("Result for " + text );

        //Log some details to demonstrate the call
        if (log != null) {
            log.info("Server: " + customAdditionRequest.getNumberA() + " + " + customAdditionRequest.getNumberB() + " = " + customAdditionResponse.getResult() + " {" + customAdditionResponse.getSuccess() + "," + customAdditionResponse.getDescription() + "}");
        }

    }

    /**
     * @param connectedNode
     */
    @Override
    public final void onStart(final ConnectedNode connectedNode) {

        //Once connected, store the log for future usage.
        this.log = connectedNode.getLog();
        //This line create the service server
        //It needs:
        //1. The service name, this is the ROS graph name where the service will be published. This is provided as an input.
        //2. The service type which is provided by the ROS Java Service Definition in the _TYPE static variable.
        //3. The service server logic that reads the request and provides the response. In this case it is implemented in createResponse
        final ServiceServer<CustomAdditionRequest, CustomAdditionResponse> serviceServer = connectedNode.newServiceServer(this.rosServiceName, CustomAddition._TYPE, this::createResponse);


        //Log the creation of the server for demonstration purposes.
        this.log.info("Created ROS Service Server[" + serviceServer.getName() + "] in URI:" + serviceServer.getUri());
    }
}
