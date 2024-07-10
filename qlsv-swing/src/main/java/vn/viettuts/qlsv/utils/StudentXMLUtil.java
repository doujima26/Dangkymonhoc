/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.viettuts.qlsv.utils;

/**
 *
 * @author Mr Thang
 */
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import vn.viettuts.qlsv.entity.Student;

public class StudentXMLUtil {
    private static final String FILE_PATH = "students.xml";

    public static void saveStudents(List<Student> students) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("Students");
            doc.appendChild(rootElement);

            for (Student student : students) {
                Element studentElement = doc.createElement("Student");

                Element studentId = doc.createElement("StudentId");
                studentId.appendChild(doc.createTextNode(student.getStudentId()));
                studentElement.appendChild(studentId);

                Element fullName = doc.createElement("FullName");
                fullName.appendChild(doc.createTextNode(student.getFullName()));
                studentElement.appendChild(fullName);

                Element birthDate = doc.createElement("BirthDate");
                birthDate.appendChild(doc.createTextNode(student.getBirthDate()));
                studentElement.appendChild(birthDate);

                Element gender = doc.createElement("Gender");
                gender.appendChild(doc.createTextNode(student.getGender()));
                studentElement.appendChild(gender);

                Element registeredCourses = doc.createElement("RegisteredCourses");
                registeredCourses.appendChild(doc.createTextNode(String.join(",", student.getRegisteredCourses())));
                studentElement.appendChild(registeredCourses);

                rootElement.appendChild(studentElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try {
            File xmlFile = new File(FILE_PATH);
            if (!xmlFile.exists()) {
                return students;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Student");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Student student = new Student();
                    student.setStudentId(element.getElementsByTagName("StudentId").item(0).getTextContent());
                    student.setFullName(element.getElementsByTagName("FullName").item(0).getTextContent());
                    student.setBirthDate(element.getElementsByTagName("BirthDate").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    String registeredCourses = element.getElementsByTagName("RegisteredCourses").item(0).getTextContent();
                    student.setRegisteredCourses(List.of(registeredCourses.split(",")));
                    students.add(student);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}