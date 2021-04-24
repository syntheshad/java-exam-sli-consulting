package com.exam.logic;

import com.exam.Main;
import com.exam.constant.TaskStatus;
import com.exam.object.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class ProcessTaskInfo extends Main {

	private static Map<Integer, Integer> monthClassificationMap = new HashMap<Integer, Integer>();

	private static Task dependentTask = null;

	public static void setTaskInfo() throws Exception {
		Task taskItem = new Task();
		try {
			String taskName = setTaskName();
			String taskDesc = setTaskDescription();
			int taskDuration = setTaskDuration();
			getTaskDependencies(dependentTask);
			LocalDate localStartDate = setStartDate();

			taskItem.setTaskName(taskName);
			taskItem.setTaskDescription(taskDesc);
			//taskItem.setEndDate(localStartDate.plusDays(taskDuration));
			taskItem.setTaskDuration(taskDuration);
			if(dependentTask != null) {
				taskItem.addPreceedingTask(dependentTask);
			}
			taskItem.setStartDate(localStartDate);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred during collection of input data. Message: " + e.getMessage());
		}
		System.out.println("TASK CREATION WAS SUCCESSFUL!");
		allActiveTasks.put(taskItem.getTaskName(), taskItem);
	}

	private static LocalDate setStartDate() throws IOException {
		int startYear = LocalDate.now().getYear();
		int startDay = LocalDate.now().getDayOfMonth();
		int startMonth = LocalDate.now().getMonthValue();
		LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
		while(true) {
			System.out.println("Start Task [A]Today? or [B]Set a Date['X' to Cancel]:");
			//String answer = sc.nextLine();
			String answer = br.readLine();
			if (answer.trim().equalsIgnoreCase("A")) {
				if (hasPrecedingTask(startDate)) {
					continue;
				}
				return startDate;
			} else if (answer.trim().equalsIgnoreCase("B")) {
				break;
			} else if (answer.trim().equalsIgnoreCase("x")) {
				throw new IOException("Operation was cancelled.");
			} else {
				System.out.println("Please answer only with the given choices.");
			}
		}
		while(true) {
			try {
				Month startMonthName;
				while (true) {
					System.out.println("The month this task will start(in number format):");
					//startMonth = sc.nextInt();
					startMonth = Integer.parseInt(br.readLine());
					if (startMonth > 12 || startMonth < 1) {
						System.out.println("Please set a valid value for the month.");
					} else if (startMonth >= LocalDate.now().getMonthValue()) {
						startMonthName = LocalDate.EPOCH.getMonth();
						break;
					} else {
						System.out.println("Please set a month that has not passed yet.");
					}
				}
				while (true) {
					System.out.println("The day of the month this task will start(in number format):");
					//startDay = sc.nextInt();
					startDay = Integer.parseInt(br.readLine());
					int inputMonthRange = monthClassificationMap.get(startMonth);
					if (startDay > inputMonthRange) {
						System.out.println("Please set a day that does not exceed the amount of days of the month you set earlier.");
					} else if (startDay <= 0) {
						System.out.println("Please set a positive value for a day of the month.");
					} else if (startDay < LocalDate.now().getDayOfMonth()) {
						System.out.println("Please set a day of the month that has not passed yet.");
					} else {
						break;
					}

				}
				while (true) {
					System.out.println("The full year wherein this task will start(in number format):");
					//startYear = sc.nextInt();
					startYear = Integer.parseInt(br.readLine());
					if (startYear >= LocalDate.now().getYear()) {
						break;
					} else {
						System.out.println("Please set a year that has not passed yet.");
					}
				}
				startDate = LocalDate.of(startYear, startMonth, startDay);
				if(hasPrecedingTask(startDate)) {
					System.out.println("It has been checked that the entire date you entered collided with this tasks' preceding task. Please try again.");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("Please follow the instructions on the type of input");
			}
		}
		return startDate;
	}

	private static boolean hasPrecedingTask(LocalDate inputDate) {
		if(dependentTask != null && !dependentTask.getStatus().equals(TaskStatus.TASK_FINISHED)) {
			if (inputDate.isBefore(dependentTask.getEndDate())) {
				System.out.println("Please consider the preceding tasks' end date");
				return true;
			}
		}
		return false;
	}

	private static Task getTaskDependencies(Task dependentTask) {
		while(true) {
			try {
				System.out.println("Is this task dependent on any other tasks?[y]|[n]:");
				//String answer = sc.next();
				String answer = br.readLine();
				if (answer.trim().toLowerCase().equals("y")) {
					DisplayTasks.displayTaskList();
					System.out.println("Type the exact task name of one of these already existing task:");
					//String alreadyExistTask = sc.nextLine();
					String alreadyExistTask = br.readLine();
					if (allActiveTasks.containsKey(alreadyExistTask)) {
						dependentTask = allActiveTasks.get(alreadyExistTask);
						StatusChecker();
						if(isTaskAlreadyCompleted(dependentTask)) {
							System.out.println("This task can be started regardless of starting date.");
						} else {
							System.out.println("Please consider the preceding tasks status before setting the task date.");
						}
						break;
					} else {
						System.out.println("This preceding task does not exist.");
					}
				} else if (answer.trim().equalsIgnoreCase("n")) {
					break;
				} else {
					System.out.println("Please answer only with 'y' or 'n'.");
				}

			} catch(Exception e) {
				System.out.println("Message: " + e.getMessage());
			}
		}
		return dependentTask;
	}

	private static boolean isTaskAlreadyCompleted(Task taskItem) {
		if (taskItem.getStatus().equals(TaskStatus.TASK_FINISHED)) {
			return true;
		}
		throw new RuntimeException("The preceding task is not yet finished, this task cannot be added.");
	}

	private static int setTaskDuration() {
		int taskDuration = 0;
		while (true) {
			try {
				System.out.println("The number of days this task will take:");
				//taskDuration = sc.nextInt();
				taskDuration = Integer.parseInt(br.readLine());
				break;
			} catch (RuntimeException | IOException e) {
				System.out.println("Use only integer(whole number) values.");
			}
		}
		return taskDuration;
	}

	private static String setTaskDescription() throws IOException {
		System.out.println("Enter task description[Can be left empty]:");
		//String taskDesc = sc.nextLine();
		String taskDesc = br.readLine();
		return taskDesc;
	}

	private static String setTaskName() throws IOException {
		// Check for duplicates in all active task
		String taskName;
		while(true) {
			System.out.println("Enter task name:");
			//String tempTaskName = sc.nextLine();
			String tempTaskName = br.readLine();
			if (allActiveTasks.containsKey(tempTaskName)) {
				System.out.println("Task name must be unique.");
			} else {
				taskName = tempTaskName;
				break;
			}
		}
		return taskName;
	}

	static {
		monthClassificationMap.put(1, 31); // Jan
		if (LocalDate.now().isLeapYear()) {
			monthClassificationMap.put(2, 28); // Feb
		} else {
			monthClassificationMap.put(2, 29); // Feb
		}
		monthClassificationMap.put(3, 31); // March
		monthClassificationMap.put(4, 30); // April
		monthClassificationMap.put(5, 31); // May
		monthClassificationMap.put(6, 30); // June
		monthClassificationMap.put(7, 31); // July
		monthClassificationMap.put(8, 31); // August
		monthClassificationMap.put(9, 30); // Sept
		monthClassificationMap.put(10, 31); // Oct
		monthClassificationMap.put(11, 30); // Nov
		monthClassificationMap.put(12, 31); // Dec
	}
}
