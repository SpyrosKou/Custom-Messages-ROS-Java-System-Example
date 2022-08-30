package msgscreation;

import custom_msgs.RosCustomMessage;
import org.junit.Assert;
import org.junit.Test;
import org.ros.internal.message.DefaultMessageFactory;
import org.ros.internal.message.definition.MessageDefinitionReflectionProvider;
import org.ros.message.MessageDefinitionProvider;
import org.ros.message.MessageFactory;


/**
 * A small test that ensures the java classes are created for the custom ROS messages.
 *
 * @author Spyros Koukas
 */
public class MessagesTest {

    @Test
    public void testRosJavaDefinitionsCreated() {
        try {
            final MessageDefinitionProvider messageDefinitionProvider = new MessageDefinitionReflectionProvider();

            final MessageFactory factory = new DefaultMessageFactory(messageDefinitionProvider);
            final RosCustomMessage customMessage = factory.newFromType(RosCustomMessage._TYPE);
            final String message = "TestMessage";
            customMessage.setText(message);
            Assert.assertEquals(customMessage.getText(), message);
        } catch (final Exception exception) {
            Assert.fail("Failed creating ROS message : [" + exception.getMessage() + "]");
        }

    }


}
