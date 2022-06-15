package glitch.me.nikatasks.dao;

import glitch.me.nikatasks.entities.CompanieAccessEntity;
import glitch.me.nikatasks.entities.CompanieEntity;
import glitch.me.nikatasks.entities.TaskEntity;
import glitch.me.nikatasks.network.Database;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

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
            Database.handleCloseConnection(getUserCompanies);
            Database.handleCloseConnection(getUserUUID);
            Database.handleCloseConnection(conn);
        }
    }

    public static boolean toggleTaskStatus(int companieID, int taskID, String userSessionID) throws Exception {
        Connection conn = null;
        PreparedStatement actualTaskStatus = null;
        PreparedStatement toggleTask = null;
        PreparedStatement getUserUUID = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getUserUUID = conn.prepareStatement("SELECT user_uuid FROM sessions WHERE session_id=(?)");
            getUserUUID.setString(1, userSessionID);

            System.out.println("TEST1 " + userSessionID);

            rs = getUserUUID.executeQuery();

            System.out.println("TEST2 " + userSessionID);

            if (!rs.isBeforeFirst())
                return false;

            rs.next();

            String userUUID = rs.getString(1);

            System.out.println("TEST3 " + userSessionID);

            rs.close();

            actualTaskStatus = conn.prepareStatement("SELECT status FROM companie_tasks WHERE companie_id=(?) AND id=(?);");
            actualTaskStatus.setInt(1, companieID);
            actualTaskStatus.setInt(2, taskID);

            rs = actualTaskStatus.executeQuery();
            rs.next();

            String changeStatus = (rs.getString(1).equals("U")) ? "C" : "U";

            toggleTask = conn.prepareStatement("UPDATE companie_tasks SET status=(?), completed_by=(?) WHERE companie_id=(?) AND id=(?);");
            toggleTask.setString(1, changeStatus);
            toggleTask.setObject(2, UUID.fromString(userUUID));
            toggleTask.setInt(3, companieID);
            toggleTask.setInt(4, taskID);

            int result = toggleTask.executeUpdate();

            return result > 0;

        } finally {
            Database.handleCloseConnection(conn);
            Database.handleCloseConnection(toggleTask);
            Database.handleCloseConnection(getUserUUID);
        }
    }

    public static boolean addTask (int companieID, String taskDescription) throws Exception {
        Connection conn = null;
        PreparedStatement addTask = null;

        try {
            conn = Database.getConnection();

            addTask = conn.prepareStatement("INSERT INTO companie_tasks (companie_id, description) VALUES (?, ?);");
            addTask.setInt(1, companieID);
            addTask.setString(2, taskDescription);

            int result = addTask.executeUpdate();

            return result > 0;

        } finally {
            Database.handleCloseConnection(conn);
            Database.handleCloseConnection(addTask);
        }
    }

    public static boolean deleteTask (int companieID, int taskID) throws Exception {
        Connection conn = null;
        PreparedStatement deleteTask = null;

        try {
            conn = Database.getConnection();

            deleteTask = conn.prepareStatement("DELETE FROM companie_tasks WHERE id=(?) AND companie_id=(?);");
            deleteTask.setInt(1, taskID);
            deleteTask.setInt(2, companieID);

            int result = deleteTask.executeUpdate();

            return result > 0;

        } finally {
            Database.handleCloseConnection(conn);
            Database.handleCloseConnection(deleteTask);
        }
    }

    public static boolean addMember(int companieID, String username) throws Exception {
        Connection conn = null;
        PreparedStatement getUserUUID = null;
        PreparedStatement companieAddMember = null;
        PreparedStatement checkMemberExistence = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            getUserUUID = conn.prepareStatement("SELECT user_uuid FROM users WHERE email=(?) OR user_name=(?)");
            getUserUUID.setString(1, username);
            getUserUUID.setString(2, username);

            System.out.println(username);

            rs = getUserUUID.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Usuário não existe");
                return false;
            }

            rs.next();

            UUID userUUID = UUID.fromString(rs.getString(1));

            checkMemberExistence = conn.prepareStatement("SELECT id FROM companie_members WHERE companie_id=(?) AND member_uuid=(?);");
            checkMemberExistence.setInt(1, companieID);
            checkMemberExistence.setObject(2, userUUID);

            rs.close();

            rs = checkMemberExistence.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("Usuário já existe!");
                return false;
            }

            companieAddMember = conn.prepareStatement("INSERT INTO companie_members (companie_id, member_uuid) VALUES (?, ?)");
            companieAddMember.setInt(1, companieID);
            companieAddMember.setObject(2, userUUID);

            int result = companieAddMember.executeUpdate();

            return result > 0;

        } finally {
            Database.handleCloseConnection(conn);
            Database.handleCloseConnection(getUserUUID);
            Database.handleCloseConnection(companieAddMember);
            Database.handleCloseConnection(checkMemberExistence);
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
            Database.handleCloseConnection(getCompaniesMembers);
            Database.handleCloseConnection(conn);
        }
    }

    public static int getCompanieTasksQuant(int companieID) throws Exception {
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
            Database.handleCloseConnection(getCompaniesMembers);
            Database.handleCloseConnection(conn);
        }
    }

    public static String getUserNameFromUUID(UUID userUUID) throws Exception {
        Connection conn = null;
        PreparedStatement getUserName = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getUserName = conn.prepareStatement("SELECT user_name FROM users WHERE user_uuid=(?)");
            getUserName.setObject(1, userUUID);

            rs = getUserName.executeQuery();

            rs.next();

            return rs.getString(1);

        } finally {
            Database.handleCloseConnection(getUserName);
            Database.handleCloseConnection(conn);
        }
    }

    public static List<TaskEntity> getTaskList(int companieID) throws Exception {
        Connection conn = null;
        PreparedStatement getCompanieTasks = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            getCompanieTasks = conn.prepareStatement("SELECT * FROM companie_tasks WHERE companie_id=(?)");
            getCompanieTasks.setInt(1, companieID);

            rs = getCompanieTasks.executeQuery();

            List<TaskEntity> taskList = new ArrayList<>();

            while (rs.next()) {
                int taskID = rs.getInt(1);
                String taskDesc = rs.getString(3);
                boolean taskStatus = (rs.getString(4).equals("U")) ? false : true;
                String completedBy = rs.getString(5);

                TaskEntity task = new TaskEntity(taskID, taskStatus, taskDesc, completedBy);

                taskList.add(task);
            }

            rs.close();

            return taskList;

        } finally {
            Database.handleCloseConnection(getCompanieTasks);
            Database.handleCloseConnection(conn);
        }
    }

    public static List<Map<UUID, String>> getMemberList(int companieID) throws Exception {
        Connection conn = null;
        PreparedStatement getCompanieMembers = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            List<Map<UUID, String>> memberList = new ArrayList<>();

            getCompanieMembers = conn.prepareStatement("SELECT user_uuid, user_name FROM users WHERE user_uuid IN (SELECT member_uuid FROM companie_members WHERE companie_id=(?))");
            getCompanieMembers.setInt(1, companieID);

            rs = getCompanieMembers.executeQuery();

            while (rs.next()) {
                Map<UUID, String> member = new HashMap<>();

                member.put(UUID.fromString(rs.getString(1)), rs.getString(2));

                memberList.add(member);
            }

            rs.close();

            return memberList;

        } finally {
            Database.handleCloseConnection(getCompanieMembers);
            Database.handleCloseConnection(conn);
        }
    }

    public static CompanieAccessEntity getCompanieAccess(String authtoken, int companieID) throws Exception {
        Connection conn = null;
        PreparedStatement validateUser = null;
        PreparedStatement getCompanieName = null;
        PreparedStatement getRequesteerUUID = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();

            validateUser = conn.prepareStatement("SELECT * FROM companies LEFT JOIN companie_members ON companies.id=companie_members.companie_id WHERE (SELECT user_uuid FROM sessions WHERE session_id=(?)) IN (owner_uuid, member_uuid) AND companies.id=(?) OR companie_members.companie_id=(?)");
            validateUser.setString(1, authtoken);
            validateUser.setInt(2, companieID);
            validateUser.setInt(3, companieID);

            rs = validateUser.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Member can not access this companie!");
                return null;
            }

            rs.close();

            getCompanieName = conn.prepareStatement("SELECT \"name\", owner_uuid FROM companies WHERE id=(?)");
            getCompanieName.setInt(1, companieID);

            rs = getCompanieName.executeQuery();
            rs.next();

            String companieName = rs.getString(1);
            String ownerUUID = rs.getString(2);

            rs.close();

            getRequesteerUUID = conn.prepareStatement("SELECT user_uuid FROM sessions WHERE session_id=(?)");
            getRequesteerUUID.setString(1, authtoken);

            rs = getRequesteerUUID.executeQuery();
            rs.next();

            String requesteerUUID = rs.getString(1);

            rs.close();

            return new CompanieAccessEntity(
                    companieID, requesteerUUID, companieName, ownerUUID
            );

        } finally {
            // Fechando todas as conexões que foram abertas.

            Database.handleCloseConnection(validateUser);
            Database.handleCloseConnection(getCompanieName);
            Database.handleCloseConnection(getRequesteerUUID);
            Database.handleCloseConnection(conn);
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
            Database.handleCloseConnection(statement);
            Database.handleCloseConnection(conn);
        }
    }
}
