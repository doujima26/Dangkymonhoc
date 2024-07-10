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


import vn.viettuts.qlsv.entity.Course;

public class CourseXMLUtil {
    private static final String FILE_PATH = "courses.xml";

    public static void saveCourses(List<Course> courses) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("Courses");
            doc.appendChild(rootElement);

            for (Course course : courses) {
                Element courseElement = doc.createElement("Course");

                Element courseId = doc.createElement("CourseId");
                courseId.appendChild(doc.createTextNode(course.getCourseId()));
                courseElement.appendChild(courseId);

                Element courseName = doc.createElement("CourseName");
                courseName.appendChild(doc.createTextNode(course.getCourseName()));
                courseElement.appendChild(courseName);

                Element courseType = doc.createElement("CourseType");
                courseType.appendChild(doc.createTextNode(course.getCourseType()));
                courseElement.appendChild(courseType);

                Element maxTheoryStudents = doc.createElement("MaxTheoryStudents");
                maxTheoryStudents.appendChild(doc.createTextNode(String.valueOf(course.getMaxTheoryStudents())));
                courseElement.appendChild(maxTheoryStudents);

                Element maxPracticalStudents = doc.createElement("MaxPracticalStudents");
                maxPracticalStudents.appendChild(doc.createTextNode(String.valueOf(course.getMaxPracticalStudents())));
                courseElement.appendChild(maxPracticalStudents);

                Element startTime = doc.createElement("StartTime");
                startTime.appendChild(doc.createTextNode(course.getStartTime()));
                courseElement.appendChild(startTime);

                Element endTime = doc.createElement("EndTime");
                endTime.appendChild(doc.createTextNode(course.getEndTime()));
                courseElement.appendChild(endTime);

                rootElement.appendChild(courseElement);
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

    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            File xmlFile = new File(FILE_PATH);
            if (!xmlFile.exists()) {
                return courses;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Course");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Course course = new Course();
                    course.setCourseId(element.getElementsByTagName("CourseId").item(0).getTextContent());
                    course.setCourseName(element.getElementsByTagName("CourseName").item(0).getTextContent());
                    course.setCourseType(element.getElementsByTagName("CourseType").item(0).getTextContent());
                    course.setMaxTheoryStudents(Integer.parseInt(element.getElementsByTagName("MaxTheoryStudents").item(0).getTextContent()));
                    course.setMaxPracticalStudents(Integer.parseInt(element.getElementsByTagName("MaxPracticalStudents").item(0).getTextContent()));
                    course.setStartTime(element.getElementsByTagName("StartTime").item(0).getTextContent());
                    course.setEndTime(element.getElementsByTagName("EndTime").item(0).getTextContent());
                    courses.add(course);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
}