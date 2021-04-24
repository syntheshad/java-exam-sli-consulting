package com.exam.logic;

import com.exam.Main;
import com.exam.constant.TaskStatus;
import com.exam.object.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayTasks extends Main {

	public static void displayTaskInfo() throws IOException {
		StatusChecker();
		System.out.println("All active tasks:");
		for (Task item : allActiveTasks.values()) {
			System.out.println("\t\tTask: " + item.getTaskName());
		}
		Task item = null;
		while(true) {
			System.out.println("Which of the current tasks would you like to display detailed information?[Please use task name][Type 'X' to cancel]");
			String taskName = br.readLine();

			if (taskName.equalsIgnoreCase("x")) {
				return;
			}
			item = allActiveTasks.get(taskName);
			if (item == null) {
				System.out.println("That Task does not exist. Try again.");
			} else {
				break;
			}
		}
		System.out.println("\nTask Name: " + item.getTaskName()
				+ "\n\tTask Description: " + item.getTaskDescription()
				+ "\n\tTask Duration: " + item.getTaskDuration() + " days"
				+ "\n\tStart Date: " + item.getStartDate()
				+ "\n\tEnd Date: " + item.getEndDate()
				+ "\n\tStatus: " + item.getStatus().getMessage());
		if(item.getPreceedingTasks().size() > 0) {
			System.out.println("\tTasks needed to execute before this task: ");
			for(Task preceedTask : item.getPreceedingTasks()) {
				System.out.println("\t\tTask: " + preceedTask.getTaskName() + ", Start Date: " + preceedTask.getStartDate()
						+ ", End Date: " + preceedTask.getEndDate()
						+ ", Status: " + item.getStatus().getMessage());
			}
		} else {
			System.out.println("No Preceeding Tasks:");
		}
	}

	public static void displayTaskList() {
		StatusChecker();
		System.out.println("All active tasks:");
		for (Task item : allActiveTasks.values()) {
			System.out.println("\t\tTask: " + item.getTaskName() + ", Start Date: " + item.getStartDate()
					+ ", End Date:" + item.getEndDate() + ", Number of preceding tasks: " + item.getPreceedingTasks().size()
					+ ", Status:" + item.getStatus().getMessage());
		}
	}

	public static void displayTaskCalendar() {
		StatusChecker();
		int month = 1;
		System.out.println("================= Task Calendar =================");
		while(month <= 12) {
			System.out.println("=======\t " + Month.of(month).name() + " \t=======");
			for(Task item : sortTasksInCalendar(month)) {
				System.out.println("\tTask: " + item.getTaskName()
						+ ", Start Date: " + item.getStartDate()
						+ ", End Date:" + item.getEndDate()
						+ ", Number of preceding tasks: " + item.getPreceedingTasks().size()
						+ ", Status:" + item.getStatus().getMessage());
			}
			month++;
		}
	}

	private static List<Task> sortTasksInCalendar(int month) {
		List<Task> tempTask = new ArrayList<>();
		for (Task item : allActiveTasks.values()) {
			if(item.getStartDate().getMonthValue() == month) {
				tempTask.add(item);
			}
		}
		return tempTask;
	}
}
