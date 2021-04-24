package com.exam;

import com.exam.constant.ProcessEnum;
import com.exam.constant.TaskStatus;
import com.exam.logic.DisplayTasks;
import com.exam.logic.ProcessTaskInfo;
import com.exam.object.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class Main {

    /** Checker for when user decides to exit the program */
    private static boolean runProgram = true;

    //protected static Scanner sc = new Scanner(System.in);

    protected static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    protected static Map<String, Task> allActiveTasks = new HashMap<String, Task>();

    /**
     * Main method for running the console app.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DisplayTasks.displayTaskList();
        // Setup a loop for input/output/display
        while(runProgram) {
            try {
                // Call method to receive action type
                ProcessEnum action = getAction();
                if (action == null) {
                    System.out.println("Missing action type. Try again.");
                    continue;
                }
                switch (action) {
                    case INPUT_TASK_INFO: ProcessTaskInfo.setTaskInfo(); break;
                    case DISPLAY_TASK_INFO: DisplayTasks.displayTaskInfo(); break;
                    case DISPLAY_TASK_LIST: DisplayTasks.displayTaskList(); break;
                    case DISPLAY_CALENDAR: DisplayTasks.displayTaskCalendar(); break;
                    case EXIT_PROGRAM: runProgram = false; break;
                }
                // TODO:
            } catch (Exception e) {
                System.out.println("An error occurred. Message: " + e.getMessage());
            }
        }
        System.out.println("Closing Program.");
    }

    private static ProcessEnum getAction() throws IOException {
        System.out.println("\nWhat do you want to do?"
                + "\n\t[A] Create a task"
                + "\n\t[B] Display a single task information"
                + "\n\t[C] Display current task list"
                + "\n\t[D] Display current task calendar"
                + "\n\t[X] Exit the program");
        String actionString = br.readLine();
        return ProcessEnum.getProcess(actionString.trim().toLowerCase());
    }

    public static void StatusChecker() {
        TaskStatus currentStatus = TaskStatus.TASK_NOT_STARTED;
        Map<String, Task> tempActiveTasks = new HashMap<String, Task>();
        Task tempTask;
        String taskName;
        for (Task taskItem : allActiveTasks.values()) {
            if(LocalDate.now().isBefore(taskItem.getStartDate())) currentStatus = TaskStatus.TASK_NOT_STARTED;
            if(LocalDate.now().isEqual(taskItem.getStartDate())) currentStatus = TaskStatus.TASK_ONGOING;
            if(LocalDate.now().isAfter(taskItem.getStartDate())) currentStatus = TaskStatus.TASK_FINISHED;
            taskName = taskItem.getTaskName();
            tempTask = taskItem;
            tempTask.setStatus(currentStatus);
            tempActiveTasks.put(taskName, tempTask);
        }
        allActiveTasks = tempActiveTasks;
    }

    static {
        /** CHECKER FOR ACTIVE TASK STATUS */
        StatusChecker();

        /** DUMMY DATA TO POPULATE EMPTY DATA STRUCT */
        Task item1 = new Task();
        item1.setTaskName("Dummy Task 1");
        item1.setTaskDescription("Dummy Test");
        item1.setStartDate(LocalDate.of(2021, 5, 20));
        item1.setTaskDuration(3);

        allActiveTasks.put(item1.getTaskName(), item1);

        Task item2 = new Task();
        item2.setTaskName("Dummy Task 2");
        item2.setTaskDescription("Dummy Test");
        item2.setStartDate(LocalDate.of(2021, 6, 12));
        item2.setTaskDuration(3);

        allActiveTasks.put(item2.getTaskName(), item2);
    }
}
