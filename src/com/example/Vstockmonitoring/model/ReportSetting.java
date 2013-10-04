package com.example.Vstockmonitoring.model;

/**
 * Created by DanielY on 10/4/13.
 */
public class ReportSetting  {
   private String  id;
   private String  report_media;
   private String  report_phone;
   private  String url_address;
   private  String report_period;

    public ReportSetting() {
    }

    public ReportSetting(String id, String report_media, String report_phone, String url_address, String report_period) {
        this.id = id;
        this.report_media = report_media;
        this.report_phone = report_phone;
        this.url_address = url_address;
        this.report_period = report_period;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReport_media() {
        return report_media;
    }

    public void setReport_media(String report_media) {
        this.report_media = report_media;
    }

    public String getReport_phone() {
        return report_phone;
    }

    public void setReport_phone(String report_phone) {
        this.report_phone = report_phone;
    }

    public String getUrl_address() {
        return url_address;
    }

    public void setUrl_address(String url_address) {
        this.url_address = url_address;
    }

    public String getReport_period() {
        return report_period;
    }

    public void setReport_period(String report_period) {
        this.report_period = report_period;
    }
}
