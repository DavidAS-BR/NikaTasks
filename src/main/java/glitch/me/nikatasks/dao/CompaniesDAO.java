package glitch.me.nikatasks.dao;

import glitch.me.nikatasks.entities.CompanieEntity;
import glitch.me.nikatasks.network.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class CompaniesDAO {
    public ArrayList<CompanieEntity> getUserCompanies(String authtoken) throws Exception {
        Connection conn = null;
        PreparedStatement getUserUUID = null;
        PreparedStatement getUserCompanies = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getUserUUID = conn.prepareStatement("SELECT * FROM sessions WHERE session_id=(?)");
            getUserUUID.setString(1, authtoken);
            rs = getUserUUID.executeQuery();

            if (!rs.isBeforeFirst())
                return null;

            rs.next();

            UUID userUUID = UUID.fromString(rs.getString(2));

            ArrayList<CompanieEntity> userCompaniesIDS = new ArrayList<CompanieEntity>();

            getUserCompanies = conn.prepareStatement("SELECT * FROM companies WHERE id IN (SELECT companie_id FROM companie_members WHERE member_uuid=(?)) OR id IN (SELECT id FROM companies WHERE owner_uuid=(?))");
            getUserCompanies.setObject(1, userUUID);
            getUserCompanies.setObject(2, userUUID);

            rs = getUserCompanies.executeQuery();

            while (rs.next()) {
                int companieID = rs.getInt(1);
                String companieName = rs.getString(2);
                String ownerUUID = rs.getString(3);

                CompanieEntity companie = new CompanieEntity(companieID, userUUID.toString(), companieName, ownerUUID);

                userCompaniesIDS.add(companie);
            }

            return userCompaniesIDS;

        } finally {
            try {
                if (getUserCompanies != null)
                    getUserCompanies.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (getUserUUID != null)
                    getUserUUID.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static int getCompanieTotalMembers(int companieID) throws Exception {
        Connection conn = null;
        PreparedStatement getCompaniesMembers = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getCompaniesMembers = conn.prepareStatement("SELECT COUNT ( * ) FROM companie_members WHERE companie_id=(?)");
            getCompaniesMembers.setInt(1, companieID);

            rs = getCompaniesMembers.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.next();

                return rs.getInt(1);
            } else {
                return 0;
            }

        } finally {
            try {
                if (getCompaniesMembers != null) {
                    getCompaniesMembers.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static int getCompanieTasks(int companieID) throws Exception {
        Connection conn = null;
        PreparedStatement getCompaniesMembers = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getCompaniesMembers = conn.prepareStatement("SELECT COUNT ( * ) FROM companie_tasks WHERE companie_id=(?);");
            getCompaniesMembers.setInt(1, companieID);

            rs = getCompaniesMembers.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.next();

                return rs.getInt(1);
            } else {
                return 0;
            }

        } finally {
            try {
                if (getCompaniesMembers != null) {
                    getCompaniesMembers.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean createCompanie(String userAuthToken, String companieName) throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Database.getConnection();

            statement = conn.prepareStatement("INSERT INTO companies (name, owner_uuid) VALUES (?, (SELECT user_uuid FROM sessions WHERE session_id=(?)));");
            statement.setString(1, companieName);
            statement.setString(2, userAuthToken);

            int result = statement.executeUpdate();

            return result > 0;

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
