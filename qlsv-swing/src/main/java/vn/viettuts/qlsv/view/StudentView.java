package vn.viettuts.qlsv.view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.table.TableRowSorter;
import vn.viettuts.qlsv.entity.Course;
import vn.viettuts.qlsv.entity.DateLabelFormatter;
import vn.viettuts.qlsv.entity.Student;
import vn.viettuts.qlsv.utils.CourseXMLUtil;
import vn.viettuts.qlsv.utils.StudentXMLUtil;

public class StudentView extends JFrame {
    private List<Course> courses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    public JLabel jl;
    private JTable courseTable;
    private DefaultTableModel courseTableModel;
    private JTable studentTable;
    private DefaultTableModel studentTableModel;
    private List<Integer> highlightedRows = new ArrayList<>();

    public StudentView() {
        // Load data from XML
        courses = CourseXMLUtil.loadCourses();
        students = StudentXMLUtil.loadStudents();

        setTitle("Quản lý môn học và sinh viên");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton courseMenuItem = new JButton("Nhập thông tin môn học");
        JButton studentMenuItem = new JButton("Đăng ký môn học cho sinh viên");
        JButton assignMenuItem = new JButton("Phân lớp cho sinh viên");
        JButton statsMenuItem = new JButton("Thống kê");

        // Add action listeners
        courseMenuItem.addActionListener(e -> showCourseForm());
        studentMenuItem.addActionListener(e -> showStudentForm());
        assignMenuItem.addActionListener(e -> showAssignForm());
        statsMenuItem.addActionListener(e -> showStatsForm());

        // Default panel
        jl = new JLabel("Chào mừng đến với hệ thống quản lý môn học và sinh viên");
        jl.setFont(new Font("Arial", Font.BOLD, 20));
         
         
        this.setLayout(new FlowLayout());
        this.add(jl);
        this.add(courseMenuItem);
        this.add(studentMenuItem);
        this.add(assignMenuItem);
        this.add(statsMenuItem);

        this.getContentPane().setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));
    }

    private void showCourseForm() {
    JPanel panel = new JPanel(new BorderLayout());
    JPanel formPanel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    JTextField courseIdField = new JTextField(10);
    JTextField courseNameField = new JTextField(10);
    JComboBox<String> courseTypeBox = new JComboBox<>(new String[]{"Lý thuyết", "Thực hành", "Hỗn hợp"});
    JTextField maxTheoryField = new JTextField(10);
    JTextField maxPracticalField = new JTextField(10);

    UtilDateModel startDateModel = new UtilDateModel();
    Properties p = new Properties();
    JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, p);
    JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());

    UtilDateModel endDateModel = new UtilDateModel();
    JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, p);
    JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.LINE_END;
    formPanel.add(new JLabel("Mã môn học:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Tên môn học:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Loại môn học:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Số SV tối đa (LT):"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Số SV tối đa (TH):"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Ngày kết thúc:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.LINE_START;
    formPanel.add(courseIdField, gbc);
    gbc.gridy++;
    formPanel.add(courseNameField, gbc);
    gbc.gridy++;
    formPanel.add(courseTypeBox, gbc);
    gbc.gridy++;
    formPanel.add(maxTheoryField, gbc);
    gbc.gridy++;
    formPanel.add(maxPracticalField, gbc);
    gbc.gridy++;
    formPanel.add(startDatePicker, gbc);
    gbc.gridy++;
    formPanel.add(endDatePicker, gbc);

    courseTypeBox.addActionListener(e -> {
        String selectedType = (String) courseTypeBox.getSelectedItem();
        if (selectedType.equals("Lý thuyết")) {
            maxPracticalField.setText("");
            maxPracticalField.setEnabled(false);
            maxPracticalField.setBackground(Color.LIGHT_GRAY);
            maxTheoryField.setEnabled(true);
            maxTheoryField.setBackground(Color.WHITE);
        } else if (selectedType.equals("Thực hành")) {
            maxTheoryField.setText("");
            maxTheoryField.setEnabled(false);
            maxTheoryField.setBackground(Color.LIGHT_GRAY);
            maxPracticalField.setEnabled(true);
            maxPracticalField.setBackground(Color.WHITE);
        } else {
            maxTheoryField.setEnabled(true);
            maxTheoryField.setBackground(Color.WHITE);
            maxPracticalField.setEnabled(true);
            maxPracticalField.setBackground(Color.WHITE);
        }
    });

    String selectedType = (String) courseTypeBox.getSelectedItem();
    if (selectedType.equals("Lý thuyết")) {
        maxPracticalField.setEnabled(false);
        maxPracticalField.setBackground(Color.LIGHT_GRAY);
    } else if (selectedType.equals("Thực hành")) {
        maxTheoryField.setEnabled(false);
        maxTheoryField.setBackground(Color.LIGHT_GRAY);
    }

    panel.add(formPanel, BorderLayout.NORTH);

    String[] columnNames = {"STT", "Mã môn học", "Tên môn học", "Loại môn học", "SV tối đa (LT)", "SV tối đa (TH)", "Ngày bắt đầu", "Ngày kết thúc"};
    courseTableModel = new DefaultTableModel(columnNames, 0);
    courseTable = new JTable(courseTableModel) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isRowSelected(row)) {
                c.setBackground(getSelectionBackground());
                c.setForeground(getSelectionForeground());
            } else if (highlightedRows.contains(row)) {
                c.setBackground(Color.LIGHT_GRAY);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else {
                c.setBackground(getBackground());
                c.setForeground(getForeground());
                c.setFont(c.getFont().deriveFont(Font.PLAIN));
            }
            return c;
        }
    };
    JScrollPane scrollPane = new JScrollPane(courseTable);
    scrollPane.setPreferredSize(new Dimension(750, 200));

    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit"); // Add Edit button
    JButton searchButton = new JButton("Search");
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(editButton); // Add Edit button to panel
    buttonPanel.add(searchButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    printCourseList();

    JDialog dialog = new JDialog(this, "Nhập thông tin môn học", true);
    dialog.getContentPane().add(panel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);

    addButton.addActionListener(e -> {
        Course course = new Course();
        course.setCourseId(courseIdField.getText());
        course.setCourseName(courseNameField.getText());
        course.setCourseType((String) courseTypeBox.getSelectedItem());
        course.setMaxTheoryStudents(maxTheoryField.isEnabled() ? Integer.parseInt(maxTheoryField.getText()) : 0);
        course.setMaxPracticalStudents(maxPracticalField.isEnabled() ? Integer.parseInt(maxPracticalField.getText()) : 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        course.setStartTime(dateFormat.format(startDateModel.getValue()));
        course.setEndTime(dateFormat.format(endDateModel.getValue()));

        courses.add(course);
        CourseXMLUtil.saveCourses(courses);
        JOptionPane.showMessageDialog(this, "Lưu thông tin môn học thành công!");
        printCourseList();

        courseIdField.setText("");
        courseNameField.setText("");
        maxTheoryField.setText("");
        maxPracticalField.setText("");
        startDateModel.setValue(null);
        endDateModel.setValue(null);
    });

    deleteButton.addActionListener(e -> {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            courses.remove(selectedRow);
            CourseXMLUtil.saveCourses(courses);
            JOptionPane.showMessageDialog(this, "Xóa môn học thành công!");
            printCourseList();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học để xóa!");
        }
    });

    editButton.addActionListener(e -> { // Add Edit button functionality
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            Course course = courses.get(selectedRow);

            // Populate form fields with selected course data
            courseIdField.setText(course.getCourseId());
            courseNameField.setText(course.getCourseName());
            courseTypeBox.setSelectedItem(course.getCourseType());
            maxTheoryField.setText(String.valueOf(course.getMaxTheoryStudents()));
            maxPracticalField.setText(String.valueOf(course.getMaxPracticalStudents()));
            try {
                startDateModel.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(course.getStartTime()));
                endDateModel.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(course.getEndTime()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);

            JButton saveButton = new JButton("Save");
            buttonPanel.add(saveButton);
            dialog.revalidate();
            dialog.repaint();

            saveButton.addActionListener(ev -> {
                course.setCourseId(courseIdField.getText());
                course.setCourseName(courseNameField.getText());
                course.setCourseType((String) courseTypeBox.getSelectedItem());
                course.setMaxTheoryStudents(maxTheoryField.isEnabled() ? Integer.parseInt(maxTheoryField.getText()) : 0);
                course.setMaxPracticalStudents(maxPracticalField.isEnabled() ? Integer.parseInt(maxPracticalField.getText()) : 0);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                course.setStartTime(dateFormat.format(startDateModel.getValue()));
                course.setEndTime(dateFormat.format(endDateModel.getValue()));

                courses.set(selectedRow, course);
                CourseXMLUtil.saveCourses(courses);
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin môn học thành công!");
                printCourseList();

                // Reset form
                courseIdField.setText("");
                courseNameField.setText("");
                maxTheoryField.setText("");
                maxPracticalField.setText("");
                startDateModel.setValue(null);
                endDateModel.setValue(null);

                addButton.setEnabled(true);
                deleteButton.setEnabled(true);
                editButton.setEnabled(true);
                buttonPanel.remove(saveButton);
                dialog.revalidate();
                dialog.repaint();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học để chỉnh sửa!");
        }
    });

    searchButton.addActionListener(e -> {
        String[] options = {"Tên môn học", "Thời gian"};
        int choice = JOptionPane.showOptionDialog(this, "Chọn tiêu chí tìm kiếm:", "Tìm kiếm",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String searchName = JOptionPane.showInputDialog(this, "Nhập tên môn học cần tìm:");
            if (searchName != null && !searchName.trim().isEmpty()) {
                highlightRowsByName(searchName.trim());
            }
        } else if (choice == 1) {
            UtilDateModel startDateSearchModel = new UtilDateModel();
            JDatePanelImpl startDateSearchPanel = new JDatePanelImpl(startDateSearchModel, p);
            JDatePickerImpl startDateSearchPicker = new JDatePickerImpl(startDateSearchPanel, new DateLabelFormatter());

            UtilDateModel endDateSearchModel = new UtilDateModel();
            JDatePanelImpl endDateSearchPanel = new JDatePanelImpl(endDateSearchModel, p);
            JDatePickerImpl endDateSearchPicker = new JDatePickerImpl(endDateSearchPanel, new DateLabelFormatter());

            JPanel dateSearchPanel = new JPanel();
            dateSearchPanel.add(new JLabel("Ngày bắt đầu:"));
            dateSearchPanel.add(startDateSearchPicker);
            dateSearchPanel.add(Box.createHorizontalStrut(15));
            dateSearchPanel.add(new JLabel("Ngày kết thúc:"));
            dateSearchPanel.add(endDateSearchPicker);

            int result = JOptionPane.showConfirmDialog(this, dateSearchPanel, "Nhập thời gian tìm kiếm", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                highlightRowsByDate(startDateSearchModel, endDateSearchModel);
            }
        }
    });
    dialog.getContentPane().setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));

    dialog.setVisible(true);
}

private void highlightRowsByName(String searchName) {
    highlightedRows.clear();
    for (int i = 0; i < courseTable.getRowCount(); i++) {
        String courseName = (String) courseTable.getValueAt(i, 2);
        if (courseName.toLowerCase().contains(searchName.toLowerCase())) {
            highlightedRows.add(i);
        }
    }
    courseTable.repaint();
}

private void highlightRowsByDate(UtilDateModel startDateModel, UtilDateModel endDateModel) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date searchStartDate = startDateModel.getValue();
    Date searchEndDate = endDateModel.getValue();
    highlightedRows.clear();

    for (int i = 0; i < courseTable.getRowCount(); i++) {
        try {
            Date courseStartDate = dateFormat.parse((String) courseTable.getValueAt(i, 6));
            Date courseEndDate = dateFormat.parse((String) courseTable.getValueAt(i, 7));

            if (searchStartDate != null && searchEndDate != null &&
                    !searchStartDate.before(courseStartDate) && !searchEndDate.after(courseEndDate)) {
                highlightedRows.add(i);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    courseTable.repaint();
}

private void printCourseList() {
    courses = CourseXMLUtil.loadCourses();

    courseTableModel.setRowCount(0);
    for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        courseTableModel.addRow(new Object[]{
                i + 1,
                course.getCourseId(),
                course.getCourseName(),
                course.getCourseType(),
                course.getMaxTheoryStudents(),
                course.getMaxPracticalStudents(),
                course.getStartTime(),
                course.getEndTime()
        });
    }
}

   private void showStudentForm() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));
    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    JTextField studentIdField = new JTextField(10);
    JTextField fullNameField = new JTextField(10);

    UtilDateModel model = new UtilDateModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

    JComboBox<String> genderBox = new JComboBox<>(new String[]{"Nam", "Nữ"});

    DefaultListModel<String> courseListModel = new DefaultListModel<>();
    for (Course course : courses) {
        courseListModel.addElement(course.getCourseName());
    }
    JList<String> courseList = new JList<>(courseListModel);
    courseList.setVisibleRowCount(5);
    JScrollPane courseScrollPane = new JScrollPane(courseList);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.LINE_END;
    formPanel.add(new JLabel("Mã sinh viên:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Họ và tên:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Ngày sinh:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Giới tính:"), gbc);
    gbc.gridy++;
    formPanel.add(new JLabel("Môn học đăng ký:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.LINE_START;
    formPanel.add(studentIdField, gbc);
    gbc.gridy++;
    formPanel.add(fullNameField, gbc);
    gbc.gridy++;
    formPanel.add(datePicker, gbc);
    gbc.gridy++;
    formPanel.add(genderBox, gbc);
    gbc.gridy++;
    formPanel.add(courseScrollPane, gbc);

    panel.add(formPanel, BorderLayout.NORTH);

    String[] columnNames = {"STT", "Mã sinh viên", "Họ và tên", "Ngày sinh", "Giới tính", "Môn học đăng ký"};
    studentTableModel = new DefaultTableModel(columnNames, 0);
    studentTable = new JTable(studentTableModel) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isRowSelected(row)) {
                c.setBackground(getSelectionBackground());
                c.setForeground(getSelectionForeground());
            } else if (highlightedRows.contains(row)) {
                c.setBackground(Color.LIGHT_GRAY);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else {
                c.setBackground(getBackground());
                c.setForeground(getForeground());
                c.setFont(c.getFont().deriveFont(Font.PLAIN));
            }
            return c;
        }
    };
    JScrollPane scrollPane = new JScrollPane(studentTable);
    scrollPane.setPreferredSize(new Dimension(750, 200));

    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit"); // Add Edit button
    JButton searchButton = new JButton("Search");
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(editButton); // Add Edit button to panel
    buttonPanel.add(searchButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    printStudentList();

    JDialog dialog = new JDialog(this, "Nhập thông tin sinh viên", true);
    dialog.getContentPane().add(panel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);

    addButton.addActionListener(e -> {
        Student student = new Student();
        student.setStudentId(studentIdField.getText());
        student.setFullName(fullNameField.getText());
        student.setBirthDate(datePicker.getJFormattedTextField().getText());
        student.setGender((String) genderBox.getSelectedItem());

        List<String> selectedCourses = courseList.getSelectedValuesList();
        student.setRegisteredCourses(selectedCourses);

        students.add(student);
        StudentXMLUtil.saveStudents(students);
        JOptionPane.showMessageDialog(this, "Lưu thông tin sinh viên thành công!");
        printStudentList();

        studentIdField.setText("");
        fullNameField.setText("");
        model.setValue(null);
        genderBox.setSelectedIndex(0);
        courseList.clearSelection();
    });

    deleteButton.addActionListener(e -> {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            students.remove(selectedRow);
            StudentXMLUtil.saveStudents(students);
            JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
            printStudentList();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để xóa!");
        }
    });

    editButton.addActionListener(e -> { // Add Edit button functionality
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            Student student = students.get(selectedRow);

            // Populate form fields with selected student data
            studentIdField.setText(student.getStudentId());
            fullNameField.setText(student.getFullName());
            try {
                model.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(student.getBirthDate()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            genderBox.setSelectedItem(student.getGender());
            courseList.clearSelection();
            for (String course : student.getRegisteredCourses()) {
                int index = courseListModel.indexOf(course);
                if (index >= 0) {
                    courseList.addSelectionInterval(index, index);
                }
            }

            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);

            JButton saveButton = new JButton("Save");
            buttonPanel.add(saveButton);
            dialog.revalidate();
            dialog.repaint();

            saveButton.addActionListener(ev -> {
                student.setStudentId(studentIdField.getText());
                student.setFullName(fullNameField.getText());
                student.setBirthDate(datePicker.getJFormattedTextField().getText());
                student.setGender((String) genderBox.getSelectedItem());
                student.setRegisteredCourses(courseList.getSelectedValuesList());

                students.set(selectedRow, student);
                StudentXMLUtil.saveStudents(students);
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin sinh viên thành công!");
                printStudentList();

                // Reset form
                studentIdField.setText("");
                fullNameField.setText("");
                model.setValue(null);
                genderBox.setSelectedIndex(0);
                courseList.clearSelection();

                addButton.setEnabled(true);
                deleteButton.setEnabled(true);
                editButton.setEnabled(true);
                buttonPanel.remove(saveButton);
                dialog.revalidate();
                dialog.repaint();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để chỉnh sửa!");
        }
    });

    searchButton.addActionListener(e -> {
        String[] options = {"Tên học sinh", "Tên môn học đã đăng ký"};
        int choice = JOptionPane.showOptionDialog(this, "Chọn tiêu chí tìm kiếm:", "Tìm kiếm",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String searchName = JOptionPane.showInputDialog(this, "Nhập tên học sinh cần tìm:");
            if (searchName != null && !searchName.trim().isEmpty()) {
                highlightRowsByStudentName(searchName.trim());
            }
        } else if (choice == 1) {
            String searchCourse = JOptionPane.showInputDialog(this, "Nhập tên môn học cần tìm:");
            if (searchCourse != null && !searchCourse.trim().isEmpty()) {
                highlightRowsByRegisteredCourse(searchCourse.trim());
            }
        }
    });

    dialog.setVisible(true);
}

private void highlightRowsByStudentName(String searchName) {
    highlightedRows.clear();
    for (int i = 0; i < studentTable.getRowCount(); i++) {
        String studentName = (String) studentTable.getValueAt(i, 2);
        if (studentName.toLowerCase().contains(searchName.toLowerCase())) {
            highlightedRows.add(i);
        }
    }
    studentTable.repaint();
}

private void highlightRowsByRegisteredCourse(String searchCourse) {
    highlightedRows.clear();
    for (int i = 0; i < studentTable.getRowCount(); i++) {
        String registeredCourses = (String) studentTable.getValueAt(i, 5);
        if (registeredCourses.toLowerCase().contains(searchCourse.toLowerCase())) {
            highlightedRows.add(i);
        }
    }
    studentTable.repaint();
}

private void printStudentList() {
    students = StudentXMLUtil.loadStudents();

    studentTableModel.setRowCount(0);
    for (int i = 0; i < students.size(); i++) {
        Student student = students.get(i);
        studentTableModel.addRow(new Object[]{
                i + 1,
                student.getStudentId(),
                student.getFullName(),
                student.getBirthDate(),
                student.getGender(),
                String.join(", ", student.getRegisteredCourses())
        });
    }
}

private void showAssignForm() {
    JPanel panel = new JPanel(new BorderLayout());

    JComboBox<String> courseComboBox = new JComboBox<>();
    for (Course course : courses) {
        courseComboBox.addItem(course.getCourseId() + " - " + course.getCourseName());
    }

    String[] columnNames = {"STT", "Mã sinh viên", "Họ và tên", "Lớp", "Thời gian học"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable studentTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(studentTable);
    scrollPane.setPreferredSize(new Dimension(900, 200));

    JTextField searchField = new JTextField(20);
    JButton searchButton = new JButton("Tìm kiếm");

    JButton assignButton = new JButton("Phân lớp");
    assignButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            if (selectedCourse != null) {
                String courseName = selectedCourse.split(" - ")[1];
                Course course = courses.stream()
                        .filter(c -> c.getCourseName().equals(courseName))
                        .findFirst()
                        .orElse(null);

                if (course != null) {
                    List<Student> theoryClass = new ArrayList<>();
                    List<Student> practicalClass = new ArrayList<>();
                    for (Student student : students) {
                        if (student.getRegisteredCourses().contains(courseName)) {
                            if (course.getCourseType().equals("Hỗn hợp")) {
                                theoryClass.add(student);
                                practicalClass.add(student);
                            } else {
                                if (course.getCourseType().equals("Lý thuyết") && theoryClass.size() < course.getMaxTheoryStudents()) {
                                    theoryClass.add(student);
                                }
                                if (course.getCourseType().equals("Thực hành") && practicalClass.size() < course.getMaxPracticalStudents()) {
                                    practicalClass.add(student);
                                }
                            }
                        }
                    }

                    tableModel.setRowCount(0);

                    if (course.getCourseType().equals("Hỗn hợp")) {
                        for (int i = 0; i < theoryClass.size(); i++) {
                            Student s = theoryClass.get(i);
                            tableModel.addRow(new Object[]{
                                    i + 1,
                                    s.getStudentId(),
                                    s.getFullName(),
                                    "Hỗn hợp",
                                    course.getStartTime() + " -> " + course.getEndTime()
                            });
                        }
                    } else {
                        if (!course.getCourseType().equals("Thực hành")) {
                            for (int i = 0; i < theoryClass.size(); i++) {
                                Student s = theoryClass.get(i);
                                tableModel.addRow(new Object[]{
                                        i + 1,
                                        s.getStudentId(),
                                        s.getFullName(),
                                        "Lý thuyết",
                                        course.getStartTime() + " -> " + course.getEndTime()
                                });
                            }
                        }

                        if (!course.getCourseType().equals("Lý thuyết")) {
                            int startIndex = theoryClass.size();
                            for (int i = 0; i < practicalClass.size(); i++) {
                                Student s = practicalClass.get(i);
                                tableModel.addRow(new Object[]{
                                        startIndex + i + 1,
                                        s.getStudentId(),
                                        s.getFullName(),
                                        "Thực hành",
                                        course.getStartTime() + " -> " + course.getEndTime()
                                });
                            }
                        }
                    }

                    tableModel.fireTableDataChanged();
                }
            }
        }
    });

    searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            studentTable.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
        }
    });

    JPanel topPanel = new JPanel();
    topPanel.setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));
    topPanel.add(courseComboBox);
    topPanel.add(searchField);
    topPanel.add(searchButton);

    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(assignButton, BorderLayout.SOUTH);

    JOptionPane.showMessageDialog(this, panel, "Phân lớp cho sinh viên", JOptionPane.PLAIN_MESSAGE);
}



  private void showStatsForm() {
    JPanel panel = new JPanel(new BorderLayout());

    JComboBox<String> statsComboBox = new JComboBox<>(new String[]{"Theo học phần", "Theo sinh viên"});
    JTable statsTable = new JTable();
    JScrollPane scrollPane = new JScrollPane(statsTable);

    JButton statsButton = new JButton("Thống kê");
    statsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedStat = (String) statsComboBox.getSelectedItem();
            if (selectedStat != null) {
                String[] columnNames;
                Object[][] data;

                switch (selectedStat) {
                    case "Theo học phần":
                        columnNames = new String[]{"Mã môn học", "Tên môn học", "Số lượng sinh viên"};
                        data = new Object[courses.size()][3];
                        for (int i = 0; i < courses.size(); i++) {
                            Course course = courses.get(i);
                            long count = students.stream()
                                    .filter(s -> s.getRegisteredCourses().contains(course.getCourseName()))
                                    .map(s -> s.getStudentId() + s.getFullName()) // Combining ID and name to ensure uniqueness
                                    .distinct()
                                    .count();
                            data[i][0] = course.getCourseId();
                            data[i][1] = course.getCourseName();
                            data[i][2] = count;
                        }
                        break;

                    case "Theo sinh viên":
                        columnNames = new String[]{"Mã sinh viên", "Tên sinh viên", "Số môn đăng ký"};
                        // Use a map to group students by ID and name
                        Map<String, Integer> studentCourseCountMap = new HashMap<>();
                        for (Student student : students) {
                            String key = student.getStudentId() + "|" + student.getFullName();
                            studentCourseCountMap.put(key, studentCourseCountMap.getOrDefault(key, 0) + student.getRegisteredCourses().size());
                        }
                        data = new Object[studentCourseCountMap.size()][3];
                        int index = 0;
                        for (Map.Entry<String, Integer> entry : studentCourseCountMap.entrySet()) {
                            String[] keyParts = entry.getKey().split("\\|");
                            data[index][0] = keyParts[0]; // Student ID
                            data[index][1] = keyParts[1]; // Student Name
                            data[index][2] = entry.getValue(); // Course count
                            index++;
                        }
                        break;

                    default:
                        columnNames = new String[]{};
                        data = new Object[][]{};
                }

                statsTable.setModel(new DefaultTableModel(data, columnNames));
            }
        }
    });

    panel.add(statsComboBox, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(statsButton, BorderLayout.SOUTH);
    panel.setBackground(Color.getHSBColor(0.5f, 0.2f, 1.0f));

    JOptionPane.showMessageDialog(this, panel, "Thống kê", JOptionPane.PLAIN_MESSAGE);
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new StudentView().setVisible(true));
}


}
