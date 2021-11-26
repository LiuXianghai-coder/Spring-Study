package org.xhliu.springwebflux.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class JmxApplication {
    public static void main(String[] args) {
        try {
            ObjectName objectName = new ObjectName(
                    "org.xhliu.springwebflux.jmx:type=basic,name=game"
            );
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new Game(), objectName);
        } catch (MalformedObjectNameException
                | NotCompliantMBeanException
                | InstanceAlreadyExistsException
                | MBeanRegistrationException e) {
            e.printStackTrace();
        }

        while (true);
    }
}
