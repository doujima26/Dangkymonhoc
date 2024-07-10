package vn.viettuts.qlsv.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "courses ")
@XmlAccessorType(XmlAccessType.FIELD)
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;


    private String courseId;
    private String courseName;
    private String courseType;
    private int maxTheoryStudents;
    private int maxPracticalStudents;
     private String startTime;
    private String endTime;
    

    // Constructors, getters, and setters

    public Course(String courseId, String courseName, String courseType, int maxTheoryStudents, int maxPracticalStudents, String startTime, String endTime) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseType = courseType;
        this.maxTheoryStudents = maxTheoryStudents;
        this.maxPracticalStudents = maxPracticalStudents;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Course(String courseId, String courseName, String courseType, int maxTheoryStudents, int maxPracticalStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseType = courseType;
        this.maxTheoryStudents = maxTheoryStudents;
        this.maxPracticalStudents = maxPracticalStudents;
    }

    public Course() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getMaxTheoryStudents() {
        return maxTheoryStudents;
    }

    public void setMaxTheoryStudents(int maxTheoryStudents) {
        this.maxTheoryStudents = maxTheoryStudents;
    }

    public int getMaxPracticalStudents() {
        return maxPracticalStudents;
    }

    public void setMaxPracticalStudents(int maxPracticalStudents) {
        this.maxPracticalStudents = maxPracticalStudents;
    }
    
}

