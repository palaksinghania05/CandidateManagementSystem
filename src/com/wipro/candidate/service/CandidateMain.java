package com.wipro.candidate.service;

import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.dao.CandidateDAO;
import com.wipro.candidate.util.WrongDataException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CandidateMain {
    public static void main(String[] args) {
        CandidateMain candidateMain = new CandidateMain();
        Scanner sc = new Scanner(System.in);
        System.out.println("Which operation do you want to perform?\n " +
                "1.Add candidates to record.\n " +
                "2.Show list of candidates filtered according to your criteria.\n " + "3.Exit\n ");
        System.out.println("Enter your choice : ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("How many candidates are there? ");
                int numberOfCandidates = sc.nextInt();
                for (int i = 1; i <= numberOfCandidates; i++) {
                    sc.nextLine();
                    System.out.println("Enter 4-digit candidate RollNo : ");
                    String id = sc.nextLine();
                    System.out.println("Enter candidate Name : ");
                    String name = sc.nextLine();
                    System.out.println("Enter marks in subject 1 : ");
                    int marks1 = sc.nextInt();
                    System.out.println("Enter marks in subject 2 : ");
                    int marks2 = sc.nextInt();
                    System.out.println("Enter marks in subject 3 : ");
                    int marks3 = sc.nextInt();
                    CandidateBean candidateBean = new CandidateBean(id, name, marks1, marks2, marks3, null, null);
                    String result = null;
                    try {
                        result = candidateMain.addCandidate(candidateBean);
                    } catch (NullPointerException e) {
                        try {
                            throw new WrongDataException();
                        } catch (WrongDataException e1) {
                            e1.toString();
                            e1.printStackTrace();
                        }
                    } catch (WrongDataException e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                    System.out.println(result);
                }
                break;
            case 2: ArrayList<CandidateBean> candidateList = new ArrayList<>();
                sc.nextLine();
                System.out.println("Enter your criteria : ");
                String criteria = sc.nextLine();
                try {
                    candidateList = candidateMain.displayAll(criteria);
                } catch (WrongDataException | SQLException e) {
                    e.toString();
                    e.printStackTrace();
                }
                System.out.println(Arrays.toString(candidateList.toArray()));
                break;

            case 3:
                System.out.println("Thank You !!");
                break;
        }
    }

    public ArrayList<CandidateBean> displayAll(String criteria) throws WrongDataException, SQLException {
        if (criteria.equalsIgnoreCase("Pass") || criteria.equalsIgnoreCase("Fail") || criteria.equalsIgnoreCase("All"))
            return new CandidateDAO().getByResult(criteria);
        else
            throw new WrongDataException();
    }

    public String addCandidate(CandidateBean candBean) throws WrongDataException {
        if (candBean.equals(null) || candBean.getName() == null || candBean.getName().length() < 2 || candBean.getM1() < 0 ||
                candBean.getM1() > 100 || candBean.getM2() < 0 || candBean.getM2() > 100 || candBean.getM3() < 0 || candBean.getM3() > 100)
            throw new WrongDataException();
        else {
            String generatedId = new CandidateDAO().generateCandidateId(candBean.getName());
            if (candBean.getM1() + candBean.getM2() + candBean.getM3() >= 240) {
                candBean.setResult("Pass");
                candBean.setGrade("Distinction");
            } else if (candBean.getM1() + candBean.getM2() + candBean.getM3() >= 180 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 240) {
                candBean.setResult("Pass");
                candBean.setGrade("First Class");
            } else if (candBean.getM1() + candBean.getM2() + candBean.getM3() >= 150 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 180) {
                candBean.setResult("Pass");
                candBean.setGrade("Second Class");
            } else if (candBean.getM1() + candBean.getM2() + candBean.getM3() >= 105 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 150) {
                candBean.setResult("Pass");
                candBean.setGrade("Third Class");
            } else {
                candBean.setResult("Fail");
                candBean.setGrade("No Grade");
            }
            String finalStatus = new CandidateDAO().addCandidate(candBean);
            if (finalStatus == "SUCCESS")
                return generatedId + ":" + candBean.getResult();
            else
                return "Error";
        }
    }
}
