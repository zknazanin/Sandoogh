package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by foroughM on 7/13/2016.
 */
public class Loan {
    int amount;
    Date StartDate;
    User user;
    Sandoogh sandoogh;
    ArrayList<LoanReport> reports; // dates user pay money back

}

class LoanReport{
   Date date;
   int amount;
}
