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
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (pwd.length() > 20) {
            return null;
        }

        if (usr.length() > 254) {
            return null;
        }

        try {
            conn = Database.getConnection();

            preparedStatement = conn.prepareStatement("SELECT user_uuid, hashed_password FROM users WHERE email=(?) OR user_name=(?)");
            preparedStatement.setString(1, usr);
            preparedStatement.setString(2, usr);

            rs = preparedStatement.executeQuery();

            if (!rs.isBeforeFirst())
                return null;

            rs.next();

            if (BCrypt.checkpw(pwd, rs.getString(2))) {
                UUID user_uuid = UUID.fromString(rs.getString(1));

                preparedStatement = conn.prepareStatement("SELECT session_id, expires_At FROM sessions WHERE user_uuid=(?)");
                preparedStatement.setObject(1, user_uuid);

                rs = preparedStatement.executeQuery();

                if (rs.isBeforeFirst()) {
                    rs.next();

                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    sfd.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

                    Date expiresAtDate = sfd.parse(rs.getString(2));
                    Date actualDate = new Date();

                    if (actualDate.before(expiresAtDate)) { // TODO: Deletar registro da sessão caso tenha expirado.
                        return rs.getString(1);
                    } else {
                        preparedStatement = conn.prepareStatement("DELETE FROM sessions WHERE user_uuid=(?)");
                        preparedStatement.setObject(1, user_uuid);

                        preparedStatement.executeUpdate();
                    }
                }

                System.out.println("Gerando nova sessão");

                preparedStatement = conn.prepareStatement("INSERT INTO sessions (session_id, user_uuid, user_agent) VALUES (?, ?, ?)");

                String session_id = UUID.randomUUID().toString().replace("-", "");

                preparedStatement.setString(1, session_id);
                preparedStatement.setObject(2, user_uuid);
                preparedStatement.setString(3, "CelularOuPC?");

                preparedStatement.executeUpdate();

                return session_id;

            } else {
                return null;
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
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