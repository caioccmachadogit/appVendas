package vip.softwares.appvendas.utils.convert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by re032629 on 24/07/2015.
 */
public class ConvertType {
    public static double ConvertStringRealToDouble(String real){
        try {
         real = real.replace(",",".");
            return Double.parseDouble(real);
        }
        catch (Exception ex){
            return 0;
        }
    }

    public static String ConvertDoubleToStringReal(double valor){
        DecimalFormat df = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try{
         return df.format(valor);
        }
        catch (Exception ex){
            return "0";
        }
    }

    public static String ConvertDateddMMyyyy_HHmm(String sDate){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = (Date)formatter.parse(sDate);
            return formatter.format(date);
        }
        catch (Exception ex){
            return null;
        }
    }
}
