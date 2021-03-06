package virtua.training.polymer.mvc.todo.controller;

import virtua.training.polymer.mvc.todo.model.Task;
import virtua.training.polymer.mvc.todo.model.User;
import virtua.training.polymer.mvc.todo.service.TaskService;

import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.mvc.annotation.View;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Primary controller -- handles all MVC Controller and REST-based functionality for managing tasks.
 * <p>
 * Created: 21 Oct 2015
 *
 * @author Kito D. Mann
 */
@Path("/tasks")
public class TaskController {
    private final static Logger logger = Logger.getLogger(TaskController.class.getName());

    @Inject
    private User user;

    @Inject
    private TaskService taskService;

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") String id, Task task) {
        Optional<Task> currentTask = taskService.get(id);
        if (currentTask.isPresent()) {
            taskService.update(task);
        } else {
            taskService.add(task);
        }
        return Response.ok().build();
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTask(Task task) {
        taskService.add(task);
        return Response.ok(task).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") String id) {
        Optional<Task> task = taskService.get(id);
        if (task.isPresent()) {
            return Response.ok(task).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeTask(@PathParam("id") String id) {
        taskService.remove(id);
        return Response.ok().build();
    }

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        Task[] tasks = taskService.getTasks();
        return Response.ok(tasks).build();
    }

    /**
     * Example of using a controller method in the same class with JAX-RS resource methods, and the @View annotation.
     */
    @Controller
    @Path("listPage")
    @GET
    @View("redirect:../facelets/todo.html#!/home/")
    public void getTaskPage() {
        // do some work
    }
}
