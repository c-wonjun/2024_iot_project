package com.example.myapplication;

public class DataModel {
    public static class TodoInfo1 {
        private TaskInfo gas;
        public TodoInfo1(TaskInfo gas) {
            this.gas = gas;
        }
        public TaskInfo getTodo1() {
            return gas;
        }
        public void setTodo1(TaskInfo gas) {
            this.gas = gas;
        }
    }
    public static class TodoInfo2 {
        private TaskInfo humidity;
        public TodoInfo2(TaskInfo humidity) {
            this.humidity = humidity;
        }
        public TaskInfo getTodo2() {
            return humidity;
        }
        public void setTodo2(TaskInfo humidity) {
            this.humidity = humidity;
        }
    }

    public static class TodoInfo3 {
        private TaskInfo temperature;
        public TodoInfo3(TaskInfo temperature) {
            this.temperature = temperature;
        }
        public TaskInfo getTodo3() {
            return temperature;
        }
        public void setTodo3(TaskInfo temperature) {
            this.temperature = temperature;
        }
    }

    public static class TaskInfo {
        private String task;
        public TaskInfo(String task) {
            this.task = task;
        }
        public String getTask() {
            return task;
        }
        public void setTask(String task) {
            this.task = task;
        }
    }
}

