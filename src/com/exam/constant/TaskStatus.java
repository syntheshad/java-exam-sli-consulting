package com.exam.constant;

public enum TaskStatus {

	TASK_FINISHED("a", "Completed"),

	TASK_ONGOING("b", "Ongoing"),

	TASK_NOT_STARTED("c", "Not Started");

	private final String code;

	private final String message;

	TaskStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public static TaskStatus getProcess(String code) {
		TaskStatus[] processes = TaskStatus.values();
		for (TaskStatus process : processes) {
			if (process.code.equals(code)) {
				return process;
			}
		}
		return null;
	}
}
