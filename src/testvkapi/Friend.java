
package testvkapi;

public class Friend {
    public String fname;
    public String sname;
    public String bdate;
    public String city;
    
    public Friend(String[] strs){
        fname = strs[0];
        sname = strs[1];
        bdate = strs[2];
        city = strs[3];
    }
}
