package de.uniba.dsg.jaxrs;

import de.uniba.dsg.jaxrs.resources.BeverageResource;
import de.uniba.dsg.jaxrs.resources.OrderResource;
import de.uniba.dsg.jaxrs.resources.EmployeeResource;
import de.uniba.dsg.jaxrs.resources.HelloResource;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(BeverageResource.class);
        resources.add(OrderResource.class);
        resources.add(EmployeeResource.class);
        resources.add(HelloResource.class);

        return resources;
    }
}
