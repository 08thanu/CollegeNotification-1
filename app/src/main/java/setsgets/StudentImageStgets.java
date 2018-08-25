package setsgets;

/**
 * Created by Win10 on 15-03-2018.
 */

public class StudentImageStgets {

    public String img_ia;
    public String img_attandence;
    public String img_timetable;
    public String img_shortage_att;
    public String img_fees;
    public String img_events;
    public String img_images;
    public String img_holiday;

    public StudentImageStgets(String img_ia, String img_attandence, String img_timetable, String img_shortage_att, String img_fees, String img_events, String img_images, String img_holiday) {
        this.img_ia = img_ia;
        this.img_attandence = img_attandence;
        this.img_timetable = img_timetable;
        this.img_shortage_att = img_shortage_att;
        this.img_fees = img_fees;
        this.img_events = img_events;
        this.img_images = img_images;
        this.img_holiday = img_holiday;
    }

    public String getImg_ia() {
        return img_ia;
    }

    public String getImg_attandence() {
        return img_attandence;
    }

    public String getImg_timetable() {
        return img_timetable;
    }

    public String getImg_shortage_att() {
        return img_shortage_att;
    }

    public String getImg_fees() {
        return img_fees;
    }

    public String getImg_events() {
        return img_events;
    }

    public String getImg_images() {
        return img_images;
    }

    public String getImg_holiday() {
        return img_holiday;
    }
}
