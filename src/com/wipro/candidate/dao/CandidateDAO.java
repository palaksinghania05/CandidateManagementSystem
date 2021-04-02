package com.wipro.candidate.dao;

import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.util.DBUtil;
import com.wipro.candidate.util.WrongDataException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class CandidateDAO {
    public String addCandidate(CandidateBean studentBean)
    {
        String status="";
        //write code here
        Connection connection = DBUtil.getDBConn();
        try {
            String sql = "INSERT INTO CANDIDATE_TBL (ID, Name, M1, M2, M3, Result, Grade) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentBean.getId());
            statement.setString(2, studentBean.getName());
            statement.setInt(3, studentBean.getM1());
            statement.setInt(4, studentBean.getM2());
            statement.setInt(5, studentBean.getM3());
            statement.setString(6, studentBean.getResult());
            statement.setString(7, studentBean.getGrade());
            int rows = statement.executeUpdate();
            if(rows != 0)
                status = "SUCCESS";
            else
                status = "FAIL";
        } catch (SQLException e) {
            status = "FAIL";
            e.printStackTrace();
        }
        return status;
    }
    public ArrayList<CandidateBean> getByResult(String criteria) {
        Connection connection = DBUtil.getDBConn();
        ArrayList<CandidateBean> list=new ArrayList<>();
        //write code here
        try{
                String sql = "SELECT * FROM candidate_tbl where Result=?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                if(criteria.equalsIgnoreCase("pass"))
                    statement.setString(1,"Pass");
                else if (criteria.equalsIgnoreCase("fail"))
                    statement.setString(1,"Fail");
                else if(criteria.equalsIgnoreCase("all"))
                    statement.setString(1,"All");
                else
                    throw new WrongDataException("Data Incorrect");
                ResultSet resultSet = statement.executeQuery(sql);
                if(resultSet != null) {
                    while (resultSet.next()) {
                        list.add(new CandidateBean(resultSet.getString("ID"), resultSet.getString("Name"),
                                resultSet.getInt("M1"), resultSet.getInt("M2"),
                                resultSet.getInt("M3"), resultSet.getString("Result"), resultSet.getString("Grade")));
                    }

                } else
                    return null;
        } catch (SQLException | WrongDataException e) {
            return null;
        }
        return list;
    }
    public String generateCandidateId (String name)
    {
        StringBuilder newId= new StringBuilder();
        //write code here
        String boldname = name.toUpperCase();
        newId.append(boldname.charAt(0)).append(boldname.charAt(1));
        //System.out.println(newId);
        newId.append(new Random().nextInt(7000-5000) + 5000);
        String id = new String(newId);
        return id;
    }
}
