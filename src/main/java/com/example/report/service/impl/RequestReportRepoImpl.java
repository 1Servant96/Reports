package com.example.report.service.impl;

import com.example.report.dto.RequestReport;
import com.example.report.repo.RequestReportRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestReportRepoImpl implements RequestReportRepo {
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    public List<RequestReport> getList(){



        Connection connection;

        {
            try {
                connection = DriverManager.getConnection(url, username, password);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select rp.id,rp.user_id,pb.text,dt.create_date from reports rp" +
                        "        join publications pb on rp.publication_id = pb.id" +
                        "        join reports_date dt on rp.report_date_id = dt.id where dt.end_date > now()"
                ); {

                    List<RequestReport> requestReportList = new ArrayList<>();

                    while (rs.next()) {
                        RequestReport requestReport = new RequestReport();
//                        requestReport.setReportId(rs.getLong(1));
                        requestReport.setUserId(rs.getLong(1));
                        requestReport.setText(rs.getString(2));
//                        requestReport.setCreatedDate(rs.getDate(3).toLocalDate());
                        requestReportList.add(requestReport);
                    }

                    return requestReportList;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
