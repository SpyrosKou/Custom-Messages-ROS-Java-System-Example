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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.ros.concurrent.CancellableLoop;
import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;


/**
 * An example client
 * An object of this class is a ROS Node with a specific,  given rosNodeName that targets a ros service server published at the given serviceName and  of type {@link rosjava_test_msgs.CustomAddition}
 * This object makes service calls to a ros service of type {@link rosjava_test_msgs.CustomAddition} published at the specified serviceName . After each successful service call it prints and logs the messages at info level.
 * Created at 2022-08-30
 *
 * @author Spyros Koukas
 */
public final class CustomROSJavaClientNodeMain extends AbstractNodeMain {
    private final long SLEEP_DURATION_MILLIS = 1000;
    private final java.lang.String rosServiceName;
    private final java.lang.String rosNodeName;

    /**
     * @param rosServiceName the name of the service to call
     * @param rosNodeName    the name of the ROS node
     */
    public CustomROSJavaClientNodeMain(final java.lang.String rosServiceName, final java.lang.String rosNodeName) {
        //Let's require that rosNodeName and rosServiceName are not null to eagerly identify this error
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
     * Is executed once after node is connected.
     *
     * @param connectedNode a {@link ConnectedNode} that will be provided as an argument
     *
     * @see AbstractNodeMain#onStart(ConnectedNode)
     */
    @Override
    public final void onStart(final ConnectedNode connectedNode) {

        final Log log = connectedNode.getLog();

        //Create a client, for the rosServiceName, that is of type rosjava_test_msgs.CustomAddition._TYPE.
        //ROS Java service definition interfaces  have a public static String _TYPE field where the ros type is saved.
        //Also, ROS Java service definition interfaces have a public static String _DEFINITION filed where the source of the service is saved.
        try {
            final ServiceClient<CustomAdditionRequest, CustomAdditionResponse> serviceClient = connectedNode.newServiceClient(this.rosServiceName, CustomAddition._TYPE);
            serviceClient.isConnected();

            //Create a response listener for consuming the service server response
            final ServiceResponseListener<CustomAdditionResponse> responseListener=new ServiceResponseListener<>() {
                @Override
                public final void onSuccess(CustomAdditionResponse customAdditionResponse) {
                    //log the service response
                    connectedNode.getLog().info("Client: Thanks, now I know that the result is:"+customAdditionResponse.getResult()+", success:"+customAdditionResponse.getSuccess()+" description:"+customAdditionResponse.getDescription());
                }

                @Override
                public final void onFailure(final RemoteException remoteException) {
                    //example logging error
                    connectedNode.getLog().error(ExceptionUtils.getStackTrace(remoteException));
                }
            };


            //The CancellableLoop will run again and again until the node is stopped.
            connectedNode.executeCancellableLoop(new CancellableLoop() {
                //Take a note of the system time when started. Note that we do not use ROS time here.
                private final long startMillis = System.currentTimeMillis();

                /**
                 *
                 * @throws InterruptedException
                 */
                @Override
                protected void loop() throws InterruptedException {
                    //Create a new service request message
                    final CustomAdditionRequest request = serviceClient.newMessage();
                    request.setNumberA(1);
                    request.setNumberB(2);
                    request.setText("Question!");

                    //log the service call
                    log.info("Client: How much is the sum " + request.getNumberA() +"+"+request.getNumberB()+"?");
                    serviceClient.call(request,responseListener);


                    //wait for SLEEP_DURATION_MILLIS to throttle the rate of the  published messages
                    Thread.sleep(SLEEP_DURATION_MILLIS);
                }
            });

        } catch (final ServiceNotFoundException serviceNotFoundException) {
            connectedNode.getLog().error(ExceptionUtils.getStackTrace(serviceNotFoundException));
            throw new RosRuntimeException(serviceNotFoundException);
        }


    }
}