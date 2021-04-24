package com.exam.object;

import com.exam.constant.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This object carries the properties of a task.
 */
public class Task {

	/**
	 * Task name that better represents the task description.
	 */
	private String taskName;

	/**
	 * Short description of the task, this can be NULL.
	 */
	private String taskDescription;

	/**
	 * Start Date in LocalDate format.
	 */
	private LocalDate startDate;

	/**
	 * End Date in LocalDate format.
	 */
	private LocalDate endDate;

	/**
	 * Duration of the task in days.
	 */
	private int taskDuration;

	/**
	 * Status of the task
	 */
	private TaskStatus status;

	/**
	 * List of preceeding tasks before this one
	 */
	private List<Task> precedingTasks = new ArrayList<Task>();

	/**
	 * Method for retrieving the task description.
	 * @return the task description in String format
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * Method for setting the detailed information for the task.
	 * @param taskDescription
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/**
	 * Method for retrieving the task name.
	 * @return the task name in String format
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Method for setting the task name.
	 * @param taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Method for retrieving the starting date for this task.
	 * @return the startDate property in LocalDate format.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Method for setting the starting date for this task.
	 * @param startDate
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Method for retrieving the end date for this task.
	 * @return the endDate property in LocalDate format.
	 */
	public LocalDate getEndDate() {
		return startDate.plusDays(taskDuration);
	}

	/**
	 * Method for setting the end date for this task.
	 * @param endDate
	 */
	/*public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}*/

	/**
	 * Method for retrieving the task duration of this task in days.
	 * @return the taskDuration property in primitive int format.
	 */
	public int getTaskDuration() {
		return taskDuration;
	}

	/**
	 * Method for setting the task duration of this task in days.
	 * @param taskDuration
	 */
	public void setTaskDuration(int taskDuration) {
		this.taskDuration = taskDuration;
	}

	/**
	 * Method for retrieving the status of this task
	 * @return COMPLETED, ONGOING, NOT STARTED.
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * Method for setting the status of this task.
	 * @param status
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	/**
	 * Method for getting the list of preceeding tasks for this task.
	 * @return the preceedingTasks property in List<> format
	 */
	public List<Task> getPreceedingTasks() {
		return precedingTasks;
	}

	/**
	 * Method for setting the list of preceeding tasks before this task.
	 * @param precedingTasks
	 */
	public void setPrecedingTasks(List<Task> precedingTasks) {
		this.precedingTasks = precedingTasks;
	}

	/**
	 * Method for adding a single preceeding task to the list of tasks before this one.
	 * @param preceedingTask
	 */
	public void addPreceedingTask(Task preceedingTask) {
		this.precedingTasks.add(preceedingTask);
	}
}
