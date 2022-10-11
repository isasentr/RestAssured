package Projemersys.Base;

public class Base {
   private String name;
   private String Sname;
   private String id;
   private String tenderId;

    public Base(String name, String sname ) {
        setName(name);
        setSname(sname);
        setTenderId(tenderId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getTenderId() {return tenderId;}

    public void setTenderId(String tenderId) {this.tenderId = tenderId;}






}

