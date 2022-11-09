package CoinAnalysis_gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter{
	
	/**
	 * represent the serial version ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * represent the pattern of the date
	 */
	private String pattern = "dd/MM/yyyy";
	/**
	 * represent dateFormatter 
	 */
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
    
    /**
     * change date string to value
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }
    
    /**
     * change data value to string
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
