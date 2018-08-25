package com.example.admin.collegenoti.models;

/**
 * Created by shashi on 16-03-2018.
 */

public class ManagementUploadInput {
    public String m_sem;
    public String m_util_type;
    public String m_util_image;
    public String m_who;

    public ManagementUploadInput(String m_sem, String m_util_type, String m_util_image, String m_who) {
        this.m_sem = m_sem;
        this.m_util_type = m_util_type;
        this.m_util_image = m_util_image;
        this.m_who = m_who;
    }
}
