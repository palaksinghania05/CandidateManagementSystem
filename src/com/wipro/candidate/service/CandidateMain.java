package com.wipro.candidate.service;

import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.dao.CandidateDAO;
import com.wipro.candidate.util.WrongDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CandidateMain {
    public static void main(String[] args) {
        try{
            CandidateMain candidateMain = new CandidateMain();
            Scanner sc = new Scanner(System.in);
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
                String result = candidateMain.addCandidate(candidateBean);
                System.out.println(result);}
            } catch(NullPointerException e){
                try {
                    throw new WrongDataException("Data Incorrect");
                } catch (WrongDataException e1) {
                    e1.getMessage();
                    e1.printStackTrace();
                }
            } catch(WrongDataException e){
                e.getMessage();
                e.printStackTrace();
            }
        CandidateMain candidateMain = new CandidateMain();
        ArrayList<CandidateBean> candidateList = new ArrayList<>();
        try {
            candidateList = candidateMain.displayAll("pass");
        } catch (WrongDataException e) {
            e.getMessage();
            e.printStackTrace();
        }
        System.out.println(candidateList);
        }

    public String addCandidate(CandidateBean candBean) throws WrongDataException {
        if(candBean.equals(null) || candBean.getName() == null || candBean.getName().length() < 2 || candBean.getM1() < 0 ||
        candBean.getM1() > 100 || candBean.getM2() < 0 || candBean.getM2() > 100 || candBean.getM3() < 0 || candBean.getM3() > 100 )
            throw new WrongDataException("Data Incorrect");
        else {
            String generatedId = new CandidateDAO().generateCandidateId(candBean.getName());
            if(candBean.getM1() + candBean.getM2() + candBean.getM3() >= 240){
                candBean.setResult("Pass");
                candBean.setGrade("Distinction");
            }
            else if(candBean.getM1() + candBean.getM2() + candBean.getM3() >= 180 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 240){
                candBean.setResult("Pass");
                candBean.setGrade("First Class");
            }
            else if(candBean.getM1() + candBean.getM2() + candBean.getM3() >= 150 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 180){
                candBean.setResult("Pass");
                candBean.setGrade("Second Class");
            }
            else if(candBean.getM1() + candBean.getM2() + candBean.getM3() >= 105 &&
                    candBean.getM1() + candBean.getM2() + candBean.getM3() < 150){
                candBean.setResult("Pass");
                candBean.setGrade("Third Class");
            }
            else{
                candBean.setResult("Fail");
                candBean.setGrade("No Grade");
            }
            String finalStatus = new CandidateDAO().addCandidate(candBean);
            if(finalStatus == "SUCCESS")
                return generatedId + ":" + candBean.getResult();
            else
                return "Error";
        }
    }

    public ArrayList<CandidateBean> displayAll(String criteria) throws WrongDataException {
        if(criteria.equalsIgnoreCase("Pass") || criteria.equalsIgnoreCase("Fail") || criteria.equalsIgnoreCase("All"))
            return new CandidateDAO().getByResult(criteria);
        else
            throw new WrongDataException("Data Incorrect");
    }

}
