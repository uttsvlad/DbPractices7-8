package com.example.dbpractices78.controllers;

import com.example.dbpractices78.dto.StatisticFormDTO;
import com.example.dbpractices78.dto.TaskDTO;
import com.example.dbpractices78.services.ContactPersonService;
import com.example.dbpractices78.services.ContractService;
import com.example.dbpractices78.services.EmployeeService;
import com.example.dbpractices78.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vlad Utts
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final EmployeeService employeeService;
    private final ContactPersonService contactPersonService;
    private final ContractService contractService;
    private final TaskService taskService;

    public ManagerController(EmployeeService employeeService, ContactPersonService contactPersonService, ContractService contractService, TaskService taskService) {
        this.employeeService = employeeService;
        this.contactPersonService = contactPersonService;
        this.contractService = contractService;
        this.taskService = taskService;
    }

    @GetMapping("/info")
    public String info() {
        return "manager/info";
    }

    @GetMapping("/newTask")
    public String newTask(Model model, @ModelAttribute("taskDTO") TaskDTO taskDTO) {

        model.addAttribute("contactPersons", contactPersonService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("contracts", contractService.findAll());
        return "manager/newTask";
    }

    @PostMapping("/createTask")
    public String createTask(Model model, @ModelAttribute("taskDTO") @Valid TaskDTO taskDTO,
                             BindingResult bindingResult,
                             Principal principal) {
        model.addAttribute("contactPersons", contactPersonService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("contracts", contractService.findAll());
        if (bindingResult.hasErrors())
            return "/manager/newTask";
        taskService.createTask(taskDTO, principal.getName());
        return "manager/info";
    }

    @GetMapping("updateTask/{taskId}")
    public String updateTaskGET(@PathVariable Long taskId,
                                Model model) {
        setModelAttributesForUpdate(model, taskId);
        return "manager/updateTask";
    }

    @PostMapping("/updateTask/{taskId}")
    public String updateTaskPOST(Model model, @PathVariable Long taskId,
                                 @Valid TaskDTO taskDTO, BindingResult bindingResult) throws ParseException {
        setModelAttributesForUpdate(model, taskId);//TODO
        if (bindingResult.hasErrors()) {
            return "manager/updateTask";
        }
        taskService.update(taskId, taskDTO);
        return "manager/info";
    }

    @GetMapping("/createTasksStatistic")
    public String createTasksStatistic(Model model, @ModelAttribute("statisticDTO") StatisticFormDTO statisticDTO) {
        model.addAttribute("employees", employeeService.findAll());
        return "manager/createTasksStatistic";
    }

    @PostMapping("/saveTasksStatistic")
    public String saveTaskStatistic(Model model, @ModelAttribute("statisticDTO") @Valid StatisticFormDTO statisticDTO,
                                    BindingResult bindingResult, HttpServletResponse response) throws ParseException, IOException {
        model.addAttribute("employees", employeeService.findAll());
        if (bindingResult.hasErrors()) {
            return "manager/createTasksStatistic";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(statisticDTO.getStartDate());
        Date end = format.parse(statisticDTO.getEndDate());
        taskService.writeTasksStatisticsCSV(employeeService.findOne(statisticDTO.getEmployeeId()),
                start, end, response);

        return "manager/info";
    }

    private void setModelAttributesForUpdate(Model model, Long taskId) {
        model.addAttribute("taskId", taskId);
        model.addAttribute("taskDTO", taskService.convertToTaskDTO(taskId));
        model.addAttribute("priorityName", taskService.getTaskPriorityName(taskId));
        model.addAttribute("contactPersons", contactPersonService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("contracts", contractService.findAll());
    }
}

