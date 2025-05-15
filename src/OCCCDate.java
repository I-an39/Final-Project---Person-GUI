//Ian Moore
//OCCC Spring 2025
//Advanced Java
//Convenient wrapper for GregorianCalendar

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.io.Serializable;

public class OCCCDate implements Serializable 
{
    private int dayOfMonth, monthOfYear, year;
    private GregorianCalendar gc;
    private boolean dateFormat, dateStyle, dateDayName;
    public static final boolean FORMAT_US = true,
                                FORMAT_EURO = false,
                                STYLE_NUMBERS = true,
                                STYLE_NAMES = false,
                                SHOW_DAY_NAME = true,
                                HIDE_DAY_NAME = false;
    
    public OCCCDate()
    {
        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = SHOW_DAY_NAME;
        gc = new GregorianCalendar();
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);
    }
    
    public OCCCDate(int day, int month, int year) throws InvalidOCCCDateException
    {
        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = SHOW_DAY_NAME;
        gc = new GregorianCalendar(year, month - 1, day);
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1;
        this.year = gc.get(Calendar.YEAR);
        
        if(!isDateValid(day, month, year)) {
            throw new InvalidOCCCDateException(month + "/" + day + "/" + year, this);
        }
    }
    
    public OCCCDate(OCCCDate d) throws InvalidOCCCDateException
    {
        dateFormat = d.dateFormat;
        dateStyle = d.dateStyle;
        dateDayName = d.dateDayName;
        dayOfMonth = d.dayOfMonth;
        monthOfYear = d.monthOfYear;
        year = d.year;
        gc = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth);
        if(!isDateValid(dayOfMonth, monthOfYear - 1, year)) {
            throw new InvalidOCCCDateException(monthOfYear + "/" + dayOfMonth + "/" + year, this);
        }
    }
    
    public OCCCDate(GregorianCalendar g)
    {
        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = SHOW_DAY_NAME;
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);
    }
    
    public int getDayOfMonth()
    {
        return dayOfMonth;
    }
    
    public String getDayName()
    {
        return gc.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_STANDALONE, Locale.US);
    }
    
    public int getMonthNumber()
    {
        return monthOfYear;
    }
    
    public String getMonthName()
    {
        return gc.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.US);
    }
    
    public int getYear()
    {
        return year;
    }
    
    public void setDateFormat(boolean df)
    {
        dateFormat = df;
    }
    
    public void setStyleFormat(boolean sf)
    {
        dateStyle = sf;
    }
    
    public void setDayName(boolean dn)
    {
        dateDayName = dn;
    }
    
    public int getDifferenceInYears()
    {
        OCCCDate syncDate = new OCCCDate();
        return year - syncDate.year;
    }
    
    public int getDifferenceInYears(OCCCDate d)
    {
        return year - d.year;
    }
    
    public boolean equals(OCCCDate dob)
    {
        return year == dob.year && monthOfYear == dob.monthOfYear && dayOfMonth == dob.dayOfMonth;
    }
    
    @Override
    public String toString()
    {
        if(dateFormat == FORMAT_US) {
            if(dateStyle == STYLE_NUMBERS) {
                if(dateDayName == SHOW_DAY_NAME) {
                    return getDayName() + ", " + monthOfYear + " / " + dayOfMonth + " / " + year;
                }
                else {
                    return monthOfYear + " / " + dayOfMonth + " / " + year;
                }
            }
            else {
                if(dateDayName == SHOW_DAY_NAME) {
                    return getDayName() + ", " + getMonthName() + " " + dayOfMonth + ", " + year;
                }
                else {
                    return getMonthName() + " " + dayOfMonth + ", " + year;
                }
            }
        }
        else {
             if(dateStyle == STYLE_NUMBERS) {
                if(dateDayName == SHOW_DAY_NAME) {
                    return getDayName() + ", " + dayOfMonth + " / " + monthOfYear + " / " + year;
                }
                else {
                    return dayOfMonth + " / " + monthOfYear + " / " + year;
                }
            }
            else {
                if(dateDayName == SHOW_DAY_NAME) {
                    return getDayName() + ", " + dayOfMonth + " " + getMonthName() + " " + year;
                }
                else {
                    return dayOfMonth + " " + getMonthName() + " " + year;
                }
            }
        }
    }
    
    public Boolean isDateValid(int day, int month, int year)
    {
        return day == dayOfMonth && month == monthOfYear && year == this.year;
    }
}
