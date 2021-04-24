package com.exam.constant;

public enum ProcessEnum {

	INPUT_TASK_INFO("a"),

	DISPLAY_TASK_INFO("b"),

	DISPLAY_TASK_LIST("c"),

	DISPLAY_CALENDAR("d"),

	EXIT_PROGRAM("x");

	private final String code;

	ProcessEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static ProcessEnum getProcess(String code) {
		ProcessEnum[] processes = ProcessEnum.values();
		for (ProcessEnum process : processes) {
			if (process.code.equals(code)) {
				return process;
			}
		}
		return null;
	}
}
