package com.mudra.utils;

import java.util.ArrayList;

/**
 * Created by apple on 4/7/17.
 */

public class GetColums {
    ArrayList<String> arrayList;
    CustomPreference golobal_variables;
    public GetColums() {
        arrayList = new ArrayList<>();
        golobal_variables = new CustomPreference();
    }
    public ArrayList<String> getEducations_coloumns() {
        arrayList.clear();
        arrayList.add(golobal_variables.uniquie_id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.s_id);
        arrayList.add(golobal_variables.s_name);
        arrayList.add(golobal_variables.year);
        arrayList.add(golobal_variables.subject);
        arrayList.add(golobal_variables.percentage);
        arrayList.add(golobal_variables.qualification);
        return arrayList;
    }

    public ArrayList<String> getSkill_coloumns() {
        arrayList.clear();
        arrayList.add(golobal_variables.uniquie_id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.id);
        arrayList.add(golobal_variables.status);
        arrayList.add(golobal_variables.score);
        return arrayList;
    }

    public ArrayList<String> getProfessional_coloumns() {
        arrayList.clear();
        arrayList.add(golobal_variables.uniquie_id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.job_profile);
        arrayList.add(golobal_variables.joning_date);
        arrayList.add(golobal_variables.leaving_date);
        arrayList.add(golobal_variables.designation);
        arrayList.add(golobal_variables.salary);
        return arrayList;
    }

    public ArrayList<String> getLanguage_coloumns() {
        arrayList.clear();
        arrayList.add(golobal_variables.uniquie_id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.read_status);
        arrayList.add(golobal_variables.write_status);
        arrayList.add(golobal_variables.speak_status);
        return arrayList;
    }

    public ArrayList<String> getInstitutionColoumns() {
        arrayList.clear();
        arrayList.add(golobal_variables.sync_tym);
        arrayList.add(golobal_variables.name);
       return arrayList;
    }

    public ArrayList<String> getDomain() {
        arrayList.clear();
        arrayList.add(golobal_variables.id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.status);
        return arrayList;
    }

    public ArrayList<String> getCourses() {
        arrayList.clear();
        arrayList.add(golobal_variables.id);
        arrayList.add(golobal_variables.name);
        arrayList.add(golobal_variables.score);
        return arrayList;
    }
}
