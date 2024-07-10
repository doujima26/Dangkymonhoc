package vn.viettuts.qlsv.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import vn.viettuts.qlsv.entity.Student;
import vn.viettuts.qlsv.view.StudentView;

public class StudentController implements ActionListener{
    
    private StudentView studentView;

    public StudentController(StudentView view) {
        this.studentView = view;
        
        
}

    void showStudentForm() {
        studentView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
    }

   
}