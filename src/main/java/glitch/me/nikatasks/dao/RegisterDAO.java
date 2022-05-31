package glitch.me.nikatasks.dao;

import glitch.me.nikatasks.network.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterDAO {
    public String registrar(String usr, String email, String pwd) throws Exception {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (pwd.length() > 20) {
            return "A senha deve possuir no máximo 20 caracteres.";
        }

        if (usr.length() > 10) {
            return "O nome de usuário deve possuir no máximo 10 caracteres";
        }

        if (email.length() > 254) {
            return "O e-mail não deve ultrapassar o limite de 254 caracteres";
        }

        try {
            conn = Database.getConnection();

            preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE user_name=(?) OR email=(?)");
            preparedStatement.setString(1, usr);
            preparedStatement.setString(2, email);

            rs = preparedStatement.executeQuery();

            if (!rs.isBeforeFirst()) {
                UUID userUUID = UUID.randomUUID();

                String hashedPassword = BCrypt.hashpw(pwd, BCrypt.gensalt(12));

                preparedStatement = conn.prepareStatement("INSERT INTO users (user_uuid, user_name, email, hashed_password) VALUES (?, ?, ?, ?)");
                preparedStatement.setObject(1, userUUID);
                preparedStatement.setString(2, usr);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, hashedPassword);

                preparedStatement.executeUpdate();

                return "Conta criada com sucesso!";
            } else {
                return "Este nome de usuário já está sendo utilizado ou e-mail já está cadastrado!";
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
