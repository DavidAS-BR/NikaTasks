package glitch.me.nikatasks.entities;

import glitch.me.nikatasks.dao.CompaniesDAO;

import java.util.Objects;

public class CompanieEntity {
    private final int companieID;
    private final String requesteerUUID;
    private final String companieName;
    private final String companieOwner;

    public CompanieEntity(int companieID, String requesteerUUID, String companieName, String companieOwner) {
        this.companieID = companieID;
        this.requesteerUUID = requesteerUUID;
        this.companieName = companieName;
        this.companieOwner = companieOwner;
    }

    public int getCompanieID() {
        return companieID;
    }

    public int getCompanieTotalMembers() {
        try {
            return CompaniesDAO.getCompanieTotalMembers(companieID);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getCompanieTotalTasks() {
        try {
            return CompaniesDAO.getCompanieTasks(companieID);
        } catch (Exception e) {
            return 0;
        }
    }

    public String getCompanieName() {
        return companieName;
    }

    public String getCompanieOwner() {
        return companieOwner;
    }

    public boolean isOwner() {
        return Objects.equals(requesteerUUID, companieOwner);
    }
}
