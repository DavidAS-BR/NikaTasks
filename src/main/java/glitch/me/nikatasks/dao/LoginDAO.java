package glitch.me.nikatasks.dao;

import glitch.me.nikatasks.network.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class LoginDAO {
    public String authenticate(String usr, String pwd) throws Exception {
        Connection conn = null;
        PreparedStatement getUserAndPass = null;
        PreparedStatement getSessionInf = null;
        PreparedStatement createNewSession = null;
        PreparedStatement deleteSession = null;
        ResultSet rs = null;

        if (pwd.length() > 20) {
            return null;
        }

        if (usr.length() > 254) {
            return null;
        }

        try {
            conn = Database.getConnection();

            getUserAndPass = conn.prepareStatement("SELECT user_uuid, hashed_password FROM users WHERE email=(?) OR user_name=(?)");
            getUserAndPass.setString(1, usr);
            getUserAndPass.setString(2, usr);

            rs = getUserAndPass.executeQuery();

            if (!rs.isBeforeFirst())
                return null;

            rs.next();

            if (BCrypt.checkpw(pwd, rs.getString(2))) {
                UUID user_uuid = UUID.fromString(rs.getString(1));

                getSessionInf = conn.prepareStatement("SELECT session_id, expires_At FROM sessions WHERE user_uuid=(?)");
                getSessionInf.setObject(1, user_uuid);

                rs = getSessionInf.executeQuery();

                if (rs.isBeforeFirst()) {
                    rs.next();

                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    sfd.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

                    Date expiresAtDate = sfd.parse(rs.getString(2));
                    Date actualDate = new Date();

                    if (actualDate.before(expiresAtDate)) { // TODO: Deletar registro da sessão caso tenha expirado.
                        return rs.getString(1);
                    } else {
                        deleteSession = conn.prepareStatement("DELETE FROM sessions WHERE user_uuid=(?)");
                        deleteSession.setObject(1, user_uuid);

                        deleteSession.executeUpdate();
                    }
                }

                System.out.println("Gerando nova sessão");

                createNewSession = conn.prepareStatement("INSERT INTO sessions (session_id, user_uuid, user_agent) VALUES (?, ?, ?)");

                String session_id = UUID.randomUUID().toString().replace("-", "");

                createNewSession.setString(1, session_id);
                createNewSession.setObject(2, user_uuid);
                createNewSession.setString(3, "CelularOuPC?");

                createNewSession.executeUpdate();

                return session_id;

            } else {
                return null;
            }

        } finally {

            try {
                if (getUserAndPass != null)
                    getUserAndPass.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (getSessionInf != null)
                    getSessionInf.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (createNewSession != null)
                    createNewSession.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (deleteSession != null)
                    deleteSession.close();
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