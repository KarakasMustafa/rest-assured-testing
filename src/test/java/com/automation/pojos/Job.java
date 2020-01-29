package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("job_id")  // this will help java to find "job_id" even if we give different name
    private String jobId;           // SerializedName gets "job_id" from database and assign to jobId
    private String job_title;
    private Integer max_salary;
    private Integer min_salary;

    public Job(){

    }

    public Job(String jobId, String job_title, Integer max_salary, Integer min_salary) {
        this.jobId = jobId;
        this.job_title = job_title;
        this.max_salary = max_salary;
        this.min_salary = min_salary;
    }

    public void setJob_id(String jobId) {
        this.jobId = jobId;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public void setMax_salary(Integer max_salary) {
        this.max_salary = max_salary;
    }

    public void setMin_salary(Integer min_salary) {
        this.min_salary = min_salary;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJob_title() {
        return job_title;
    }

    public Integer getMax_salary() {
        return max_salary;
    }

    public Integer getMin_salary() {
        return min_salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", job_title='" + job_title + '\'' +
                ", max_salary=" + max_salary +
                ", min_salary=" + min_salary +
                '}';
    }
}
