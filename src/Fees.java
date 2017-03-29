/**
 * Created by dapel on 9/2/2017.
 */
public class Fees {



    public int Fees(String m, String p){
        int fee=0;

        if("Deluxe".equals(m) && "Registration".equals(p)){
            fee = 500;
        }
        else if("Non-Deluxe".equals(m) && "Registration".equals(p)){
            fee = 300;
        }
        else if("Weekday".equals(m) && "Registration".equals(p)){
            fee = 180;
        }
        else if("Deluxe".equals(m) && "Monthly".equals(p)){
            fee = 120;
        }
        else if("Non-Deluxe".equals(m) && "Monthly".equals(p)){
            fee = 100;
        }
        else if("Weekday".equals(m) && "Monthly".equals(p)){
            fee = 75;
        }
        return fee;
    }




}






