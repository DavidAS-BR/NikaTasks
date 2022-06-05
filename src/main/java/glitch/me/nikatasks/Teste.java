package glitch.me.nikatasks;

import glitch.me.nikatasks.dao.CompaniesDAO;
import glitch.me.nikatasks.entities.CompanieEntity;

import java.util.ArrayList;

class Teste {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.home"));

        CompaniesDAO getUserComanies = new CompaniesDAO();

        ArrayList<CompanieEntity> userCompanies = getUserComanies.getUserCompanies("awdjsjhajhjdws");

        if (userCompanies == null) {
            System.out.println("null");
            return;
        }

        if (userCompanies.isEmpty()) {
            System.out.println("Teste");
        }

        for (CompanieEntity companie : userCompanies) {
            System.out.println(companie.getCompanieName());
        }
    }
}