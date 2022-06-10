package glitch.me.nikatasks.entities;

import glitch.me.nikatasks.dao.CompaniesDAO;

public class TaskEntity {
    private final int taskID;
    private final boolean taskStatus;
    private final String taskDescription;
    private final String completedBy;

    public TaskEntity(int taskID, boolean taskStatus, String taskDescription, String completedBy) {
        this.taskID = taskID;
        this.taskStatus = taskStatus;
        this.taskDescription = taskDescription;
        this.completedBy = completedBy;
    }

    public int getTaskID() {
        return taskID;
    }

    public boolean isTaskCompleted() {
        return taskStatus;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getCompletedBy() {
        return this.completedBy;
    }
}
