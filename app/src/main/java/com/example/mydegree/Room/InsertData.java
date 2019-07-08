package com.example.mydegree.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InsertData {

    public static ArrayList<Course> getCourses(){
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("INFS1602","Digital Transformation in Business","Enter description here",1,6,4.5, true,true,true, "https://www.handbook.unsw.edu.au/undergraduate/courses/2019/INFS1602/", null, "Face-to-face"));
        return courses;
    }

    public static ArrayList<Program> getPrograms(){
        ArrayList<Program> programs = new ArrayList<>();
        programs.add(new Program("3979", "Information Systems", "The Bachelor of Information Systems (BIS) degree is a highly prized qualification which provides students with information systems expertise and business skills. The program is intended to develop conceptual and practical skills. After an introductory first stage, students will learn about business systems analysis and design, data management, enterprise systems, business process management, big data business analytics, business systems infrastructure and security as well as mathematics, management accounting and commercial programming.\n" + "\n" +
                "This three year degree is designed for students interested in the use and application of IS and IT commercial environment. It combines courses that assist students to learn about the technical environment of IS as well as develop an understanding of the business implications of these systems. Organisations are always looking for 'business savvy' IS graduates who understand the business and technical issues which surround and impact their information systems.\n" + "\n" +
                "BIS graduates from this degree can target careers in areas such as: business systems analysis, information system development, communications networks analysis and development; IS security development; e-business systems development; business intelligence systems construction, business analytics and IS and IT architecture and infrastructure development and maintenance. The program includes courses from many disciplines including: Information Systems, Technology and Management, and other Commerce and Economics courses.", 144,3,"https://www.handbook.unsw.edu.au/undergraduate/programs/2019/3979"));
        programs.add(new Program("3964","Information Systems (Co-op) (Honours)","The BIS (Co-op) (Hons) is a full-time four year Honours degree program. It is an industry linked education program leading to the award of the qualification Bachelor of Information Systems (Co-op) (Honours). The program draws on both Information Systems and business and is intended to develop conceptual and practical skills. After an introductory first stage, students will learn about business analysis systems and design, data management, business systems infrastructure and security as well as commercial programming statistics, management accounting and commercial programming.\n" + "\n" +
                "The BIS (Co-op) (Hons) program has been designed in conjunction with Information Systems and Information Technology industry professionals to provide for the needs of Australian businesses. The program combines the requirements for the award of the degree with 18 months of coordinated industrial experience at three different sponsoring organisations (24 weeks at each). Industry Training extends outside university semesters. \n" + "\n" +
                "The program combines courses that assist students to learn about the technical environment of IS as well as develop an understanding of the business implications of these systems. Organisations are always looking for business savvy IS graduates who understand the business and technical issues which surround and impact their information systems. IS graduates from this degree can target careers in areas such as: business systems analysis, information system development, communications networks analysis and development; IS security development; e-business systems development; business intelligence systems construction, and IS and IT architecture and infrastructure development and maintenance.",
                192,4,"https://www.handbook.unsw.edu.au/undergraduate/programs/2019/3964"));
        programs.add(new Program("3584","Commerce / Information Systems","This 4 year dual program leads to the award of a Bachelor of Commerce/Bachelor of Information Systems (BCom/BIS) and will meet the needs of students who want a strong, focused and highly regarded business degree combined with an Information Systems degree. Organisations are always looking for \"business savvy\"? IS graduates who understand the business and technical issues which surround and impact their information systems. As part of the degree program students will complete a major stream in both Information Systems (IS) and an approved disciplinary stream within the UNSW Business School.\n" + "\n" +
                "In selecting their combination of majors, students should note that while there is a wide range of choice, not every combination may be able to be completed in 4 years of full-time study. Students are also not able to take a modern language as their major.",144,3,"https://www.handbook.unsw.edu.au/undergraduate/programs/2019/3584"));
        return programs;
    }

    public static ArrayList<Prereq> getPrereqs(){
        ArrayList<Prereq> prereqs = new ArrayList<>();

        // insert prereq objects here

        return prereqs;
    }
}
