package com.example.dbpractices78.controllers;

/**
 * @author Vlad Utts
 */

import com.example.dbpractices78.services.TaskService;
import com.example.dbpractices78.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/clerk")
public class ClerkController {
    private final TaskService taskService;
    private final UserService userService;

    public ClerkController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/info")
    public String info() {
        return "clerk/info";
    }

    @GetMapping("/showTasks")
    public String showTasks(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("tasks", taskService.getTasks(userService.getEmployeeByUsername(username)));

        return "clerk/showTasks";
    }

    @GetMapping("/showTask/{id}")
    public String show(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("task", taskService.getTask(id));
        model.addAttribute("employee", userService.getEmployeeByUsername(principal.getName()));

        return "clerk/showTask";
    }

    @GetMapping("/taskDone/{id}")
    public String doneTask(@PathVariable Long id) {
        taskService.doneTask(id);

        return "redirect:/clerk/showTask/" + id;
    }
}
