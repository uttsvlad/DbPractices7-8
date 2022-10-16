package com.example.dbpractices78.services;

import com.example.dbpractices78.dto.StatisticDTO;
import com.example.dbpractices78.dto.TaskDTO;
import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.models.Task;
import com.example.dbpractices78.repositories.*;
import com.example.dbpractices78.util.CSVTasksStatisticFileGenerator;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final CSVTasksStatisticFileGenerator csvGenerator;

    public TaskService(TaskRepository taskRepository, StatusRepository statusRepository, PriorityRepository priorityRepository, ContactPersonRepository contactPersonRepository, EmployeeRepository employeeRepository, UserRepository userRepository, ContractRepository contractRepository, ModelMapper modelMapper, CSVTasksStatisticFileGenerator csvGenerator) {
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;
        this.contactPersonRepository = contactPersonRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
        this.csvGenerator = csvGenerator;
    }

    public List<Task> getTasks(Employee employee) {
        return taskRepository.findAllByAuthorOrExecutor(employee, employee);
    }

    public Task getTask(Long id) {
        Optional<Task> byId = taskRepository.findById(id);
        return byId.orElseGet(Task::new);
    }

    public void doneTask(Long id) {
        Optional<Task> byId = taskRepository.findById(id);
        byId.ifPresent(task -> {
            task.setCompleteDate(new Date());
            task.setStatus(statusRepository.findByStatusName("DONE"));
            taskRepository.save(task);
        });
    }

    public void createTask(TaskDTO taskDTO, String username) {
        taskRepository.save(convertToTask(taskDTO, username));
    }

    @SneakyThrows
    private Task convertToTask(TaskDTO taskDTO, String username) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setCreationDate(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date deadlineDate = format.parse(taskDTO.getDeadlineDate());
        task.setDeadlineDate(deadlineDate);
        task.setStatus(statusRepository.findByStatusName("IN_PROGRESS"));
        task.setPriority(priorityRepository.findById(taskDTO.getPriorityId()).get());
        task.setContactPerson(contactPersonRepository.findById(taskDTO.getContactPersonId()).get());
        task.setExecutor(employeeRepository.findById(taskDTO.getExecutorId()).get());
        task.setContract(contractRepository.findById(taskDTO.getContractId()).get());
        Employee employee = employeeRepository
                .findById(userRepository.findByUsername(username)
                        .get().getEmployee()
                        .getEmployeeId()).get();
        task.setAuthor(employee);
        return task;
    }

    public TaskDTO convertToTaskDTO(Long taskId) {
        Task task = taskRepository.findById(taskId).get();

        return modelMapper.map(task, TaskDTO.class);
    }

    public String getTaskPriorityName(Long taskId) {
        return taskRepository.findById(taskId).get().getPriority().getPriorityName();
    }

    public void update(Long taskId, TaskDTO taskDTO) throws ParseException {
        Optional<Task> byId = taskRepository.findById(taskId);
        if (byId.isPresent()) {
            Task task = byId.get();
            task.setDescription(taskDTO.getDescription());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date deadlineDate = format.parse(taskDTO.getDeadlineDate());
            task.setDeadlineDate(deadlineDate);
            task.setPriority(priorityRepository.findById(taskDTO.getPriorityId()).get());
            task.setContactPerson(contactPersonRepository.findById(taskDTO.getContactPersonId()).get());
            task.setExecutor(employeeRepository.findById(taskDTO.getExecutorId()).get());
            task.setContract(contractRepository.findById(taskDTO.getContractId()).get());
            taskRepository.save(task);
        }
    }

    public void writeTasksStatisticsCSV(Employee executor, Date start, Date end, HttpServletResponse response) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tasks_statistic_" + currentDateTime + ".csv";

        StatisticDTO statistic = countTasksStatistic(taskRepository.findAllByExecutorAndCreationDateBetween(executor, start, end));
        response.setContentType("text/csv");
        response.setHeader(headerKey, headerValue);
        csvGenerator.writeTasksStatisticToCsv(statistic,
                response.getWriter());
    }

    private StatisticDTO countTasksStatistic(List<Task> tasks) {
        Date now = new Date();
        int allTasksCount = tasks.size();
        int successCompletedTasksCount = 0;
        int failCompletedTasksCount = 0;
        int notCompletedTasksBeforeDeadline = 0;
        int notCompletedTasksAfterDeadline = 0;

        for (Task task : tasks) {
            if (task.getCompleteDate() != null) {
                if (task.getCompleteDate().after(task.getDeadlineDate()))
                    successCompletedTasksCount++;
                else
                    failCompletedTasksCount++;
            } else {
                if (task.getDeadlineDate().after(now))
                    notCompletedTasksBeforeDeadline++;
                else
                    notCompletedTasksAfterDeadline++;
            }
        }
        return new StatisticDTO(allTasksCount, successCompletedTasksCount, failCompletedTasksCount, notCompletedTasksBeforeDeadline, notCompletedTasksAfterDeadline);
    }
}
