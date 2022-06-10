package glitch.me.nikatasks.entities;

import glitch.me.nikatasks.dao.CompaniesDAO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CompanieAccessEntity extends CompanieEntity{

    private List<TaskEntity> tasklist;
    private List<Map<UUID, String>> memberList;

    public CompanieAccessEntity(int companieID, String requesteerUUID, String companieName, String companieOwner) {
        super(companieID, requesteerUUID, companieName, companieOwner);
    }

    public List<TaskEntity> getTasklist() {
        try {
            return CompaniesDAO.getTaskList(this.getCompanieID());
        } catch (Exception ignored) {
            return null;
        }
    }

    public List<Map<UUID, String>> getMemberList() {
        try {
            return CompaniesDAO.getMemberList(this.getCompanieID());
        } catch (Exception ignored) {
            return null;
        }
    }
}