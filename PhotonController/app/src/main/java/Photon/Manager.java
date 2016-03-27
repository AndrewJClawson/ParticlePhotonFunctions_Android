package Photon;

/**
 * Created by andrewclawson on 3/26/16.
 */
public class Manager {
    //public ParticleDevice Device;
    private String email;
    private String password;
    private String deviceName;

    public Manager(){
        setEmail("andrewjclawson18@gmail.com");
        setPassword("clawson1");
        setDeviceName("aclawson");
    }
    public void setEmail(String string){
        this.email = string;
    }

    public String getEmail(){
        return this.getPassword();
    }

    public void setPassword(String string){
        this.password = string;
    }

    public String getPassword(){
        return this.password;
    }

    public void setDeviceName(String string){ this.deviceName = string;}

    public String getDeviceName(){ return this.deviceName;}
}
