///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  AS400Timestamp.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 2010-2010 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 Provides a converter between a {@link java.sql.Timestamp java.sql.Timestamp} object and an IBM i <i>timestamp</i> value such as "1997-12-31-23.59.59.999999".
 In the IBM i programming reference, this type is referred to as the "<b>Timestamp</b> Data Type", or DDS data type <tt>Z</tt>.
 <p>
 The minimum value for an IBM i timestamp is <tt>0001-01-01-00.00.00.000000</tt>, and the maximum value is <tt>9999-12-31-24.00.00.000000</tt>.
 <p>
 An IBM i <i>timestamp</i> value simply indicates a year/month/day/hour/minute/second/microsecond, and does not indicate a contextual time zone.  Internally, this class interprets all date- and time-related strings as relative to the GMT time zone.
 <p>
 Suggestion: To avoid confusion and unexpected results when crossing time zones:
 <br>Whenever creating or interpreting instances of {@link java.sql.Date java.sql.Date}, {@link java.sql.Time java.sql.Time}, or {@link java.sql.Timestamp java.sql.Timestamp}, always assume that the reference time zone for the object is GMT, and avoid using any deprecated methods.  If it is necessary to convert date/time values between GMT and other time zones, use methods of <tt>Calendar</tt>.  Rather than using <tt>toString()</tt> to display the value of a date/time object, use <tt>DateFormat.format()</tt> after specifying a GMT TimeZone.

 @see AS400Date
 @see AS400Time
 **/
public class AS400Timestamp extends AS400AbstractTime
{
  // Constants used when processing *DTS timestamp values.
  private static final BigInteger ONE_THOUSAND = new BigInteger("1000");
  private static final BigInteger ONE_MILLION  = new BigInteger("1000000");

  // Conversion factor: Microseconds elapsed from [1970-01-01 00:00:00] to [2000-01-01 00:00:00].  We've calculated this value previously.
  private static final BigInteger DTS_CONVERSION_FACTOR = new BigInteger("946684800000000");
  private static final String PARSING_PATTERN = "yyyy-MM-dd-HH.mm.ss";

  private java.sql.Timestamp defaultValue_;
  private transient SimpleDateFormat dateFormatterWithMillis_;



  /** The default Timestamp format.
   <ul>
   <li>Example: 1997-04-25-23.59.59.999999
   <li>Range of years: 0001-9999
   <li>Default separator: '-' and '.'
   <li>Length: 26 bytes
   <li>Note: The time zone context is not specified. It is typically assumed to match that of the IBM i system.
   </ul>
   **/
  public static final int FORMAT_DEFAULT = 0;


  /** Timestamp format *DTS ("Standard Time Format").
   <ul>
   <li>Example: The timestamp value 0x8000000000000000 represents 2000-01-01 00:00:00.000000, in the time zone context of the IBM i system.
   <li>Range of years: 1928-2071
   <br>(Date range: 1928-07-25 00:00:00.000000 to 2071-05-09 00:00:00.000000)
   <li>Default separator: not applicable (none)
   <li>Length: 8 bytes
   <li>Note: The time zone context is the time zone of the IBM i system, rather than GMT.
   <br>The base date and time for the TOD clock, or the date and time represented by hex value 0x0000000000000000, is August 23, 1928 12:03:06.314752 (in the time zone of the IBM i system).
   </ul>
   **/
  static final int FORMAT_DTS = 1;  // for internal Toolbox use only

  // From the spec for the QWCCVTDT API:
  // "the supported date range [for format *DTS is] from August 25, 1928, 00:00:00.000000 to May 09, 2071, 00:00:00.000000".

  // From the MI instructions spec:
  //
  // The Standard Time Format [*DTS] is defined as a 64-bit (8-byte) unsigned binary value as follows:
  //
  // Offset 	
  // Dec  Hex   Field Name             Data Type and Length
  // ___  ___   ____________________   ____________________
  // 0    0     Standard Time Format   UBin(8)
  // 0    0     Time                   Bits 0-51  (52 bits)
  // 0    0     Uniqueness bits        Bits 52-63 (12 bits)
  // 8    8     --- End --- 	
  //
  //
  // The time field is a binary number which can be interpreted as a time value in units of 1 microsecond. A binary 1 in bit 51 is equal to 1 microsecond.
  //
  // The "uniqueness bits" field may contain any combination of binary 1s and 0s. These bits do not provide additional granularity for a time value; they merely allow unique 64-bit values to be returned, such as when the value of the time-of-day (TOD) clock is materialized. When the uniqueness bits all contain binary 0s, then the 64-bit value returned is not unique. Unless explicitly stated otherwise, MI instructions which materialize the TOD clock return a unique 64-bit value.


  // Design note: According to the IBM i datatype spec:
  // "Microseconds (.mmmmmm) are optional for timestamp literals and if not provided will be padded on the right with zeros. Leading zeros are required for all timestamp data."
  // For simplicity, we will assume that the "timestamp" fields encountered by the Toolbox, will always occupy exactly 26 bytes on the system, and will always specify microseconds.


  /**
   Constructs an AS400Timestamp object.
   **/
  public AS400Timestamp()
  {
    setFormat(0, '-');  // this data type has only one format
  }


  // Overrides method of superclass.
  /**
   Returns a Java object representing the default value of the data type.
   @return a <tt>java.sql.Timestamp</tt> object with a value of January 1, 1970, 00:00:00 GMT
   **/
  public Object getDefaultValue()
  {
    // Design note: According to the IBM i datatype spec:
    // "The default initialization value for a timestamp is midnight of January 1, 0001 (0001-01-01-00.00.00.000000)."
    // However, for simplicity, we will stay consistent with our other "date" classes on the default value, until/unless we get a requirement to do otherwise.

    if (defaultValue_ == null) {
      defaultValue_ = new java.sql.Timestamp(0L); // January 1, 1970, 00:00:00.000000 GMT
    }

    return defaultValue_;
  }


  // Implements abstract method of superclass.
  /**
   Returns {@link AS400DataType#TYPE_TIMESTAMP TYPE_TIMESTAMP}.
   @return <tt>AS400DataType.TYPE_TIMESTAMP</tt>.
   **/
  public int getInstanceType()
  {
    return AS400DataType.TYPE_TIMESTAMP;
  }

  // Implements abstract method of superclass.
  /**
   Returns the Java class that corresponds with this data type.
   @return <tt>java.sql.Timestamp.class</tt>.
   **/
  public Class getJavaType()
  {
    return java.sql.Timestamp.class;
  }


  // Used by class 'User'.
  /**
   Sets the format of this AS400Timestamp object.
   The specified format's default separator is used.
   @param format  The format for this object.
   Valid values are:
   <ul>
   <li>{@link #FORMAT_DEFAULT FORMAT_DEFAULT}</li>
   <li>{@link #FORMAT_DTS FORMAT_DTS}</li>
   </ul>
   **/
  void setFormat(int format)
  {
    super.setFormat(format);
  }


  // Overrides method of superclass.  This allows us to be more specific in the javadoc.
  /**
   Converts the specified Java object into IBM i format in the specified byte array.
   @param javaValue The object corresponding to the data type.  It must be an instance of {@link java.sql.Timestamp java.sql.Timestamp}.
   @param as400Value The array to receive the data type in IBM i format.  There must be enough space to hold the IBM i value.
   @param offset The offset into the byte array for the start of the IBM i value.  It must be greater than or equal to zero.
   @return The number of bytes in the IBM i representation of the data type.
   **/
  public int toBytes(Object javaValue, byte[] as400Value, int offset)
  {
    return super.toBytes(javaValue, as400Value, offset);
  }


  // Implements abstract method of superclass.
  /**
   Converts the specified IBM i data type to a Java object.
   @param as400Value The array containing the data type in IBM i format.  The entire data type must be represented.
   @param offset The offset into the byte array for the start of the IBM i value. It must be greater than or equal to zero.
   @return a {@link java.sql.Timestamp java.sql.Timestamp} object corresponding to the data type.
   The reference time zone for the object is GMT.
   **/
  public Object toObject(byte[] as400Value, int offset)
  {
    java.sql.Timestamp dateObj = null;

    switch (getFormat())
    {
      case FORMAT_DEFAULT:
        {
          try
          {
            String timestampString = getCharConverter().byteArrayToString(as400Value, offset, getLength());
            if (DEBUG) System.out.println("AS400Timestamp.toObject(): Timestamp string from byte array: |" + timestampString + "|");
            // Our SimpleDateFormat formatter doesn't handle microseconds.
            // Strip out the fractional seconds before parsing.
            // The default IBM i "timestamp" format is:  yyyy-mm-dd-hh.mm.ss.mmmmmm
            java.util.Date dateObjWithoutMicros = getDateFormatter().parse(timestampString.substring(0,19)); // disregard fractional seconds for now

            // Now add the fractional seconds back in.
            int microsIntoSecond = Integer.parseInt(timestampString.substring(20));
            dateObj = new java.sql.Timestamp(dateObjWithoutMicros.getTime());
            ((java.sql.Timestamp)dateObj).setNanos(1000*microsIntoSecond); // 1 microsec == 1000 nanosecs
          }
          catch (NumberFormatException e) {
            // Assume that the exception is because we got bad input.
            Trace.log(Trace.ERROR, e.getMessage(), as400Value);
            throw new ExtendedIllegalArgumentException("as400Value", ExtendedIllegalArgumentException.PARAMETER_VALUE_NOT_VALID);
          }
          catch (ParseException e) {
            // Assume that the exception is because we got bad input.
            Trace.log(Trace.ERROR, e.getMessage(), as400Value);
            throw new ExtendedIllegalArgumentException("as400Value", ExtendedIllegalArgumentException.PARAMETER_VALUE_NOT_VALID);
          }
          break;
        }


      // This case is only used internally by Toolbox classes such as 'User'.
      case FORMAT_DTS:
        {
          // Determine the "elapsed microseconds" value represented by the *DTS value.
          // Note that *DTS values, in theory, specify microseconds elapsed since August 23, 1928 12:03:06.314752.
          // However, the real reference point is January 1, 2000, 00:00:00.000000,
          // which is represented by *DTS value 0x8000000000000000.

          // In the returned *DTS value, only the first 8 bytes are meaningful.
          // Of those 8 bytes, only bits 0-51 are used to represent "elapsed microseconds".

          // To prevent sign-extension when we right-shift the bits:
          // Copy the first 8 bytes into a 9-byte array, preceded by 0x00.
          byte[] bytes9 = new byte[9];
          System.arraycopy(as400Value, offset, bytes9, 1, 8); // right-justify
          BigInteger bits0to63 = new BigInteger(bytes9); // bits 0-63

          // Convert base of date from August 23, 1928 12:03:06.314752 to January 1, 2000, 00:00:00.000000.
          byte[] dts2000 = { 0, (byte)0x80, 0,0,0,0,0,0,0 };  // 0x8000000000000000
          BigInteger basedOn2000 = bits0to63.subtract(new BigInteger(dts2000));

          // Eliminate the "uniqueness bits" (bits 52-63).
          // Right-shift 12 bits, without sign-extension, leaving bits 0-51.
          BigInteger microsElapsedSince2000 = basedOn2000.shiftRight(12);

          // Convert the above value to "microseconds elapsed since January 1, 1970, 00:00:00".  That gets us closer to a value we can use to create a Java timestamp object.
          BigInteger microsElapsedSince1970 = microsElapsedSince2000.add(DTS_CONVERSION_FACTOR);

          // Milliseconds elapsed since January 1, 1970, 00:00:00
          long millisSince1970 = microsElapsedSince1970.divide(ONE_THOUSAND).longValue(); // 1 millisec == 1000 microsecs

          dateObj = new java.sql.Timestamp(millisSince1970); // GMT time zone

          // Set the "nanoseconds into the second".
          int microsIntoSecond = microsElapsedSince1970.mod(ONE_MILLION).intValue();
          dateObj.setNanos(1000*microsIntoSecond); // 1 microsec == 1000 nanosecs
          break;
        }
      default:  // this will never happen
        throw new InternalErrorException(InternalErrorException.UNKNOWN, "Unrecognized format: " + getFormat());

    } // switch

    return dateObj;
  }


  /**
   Converts the specified IBM i data type to a Java object.
   This method is simply a convenience front-end to the {@link #toObject(byte[]) toObject(byte[])} method.
   @param as400Value The array containing the data type in IBM i format.  The entire data type must be represented.
   @return a {@link java.sql.Timestamp java.sql.Timestamp} object corresponding to the data type.
   The reference time zone for the object is GMT.
   **/
  public java.sql.Timestamp toTimestamp(byte[] as400Value)
  {
    return (java.sql.Timestamp)toObject(as400Value, 0);
  }


  /**
   Converts the specified IBM i data type to a Java object.
   This method is simply a convenience front-end to the {@link #toObject(byte[],int) toObject(byte[],int)} method.
   @param as400Value The array containing the data type in IBM i format.  The entire data type must be represented.
   @param offset The offset into the byte array for the start of the IBM i value. It must be greater than or equal to zero.
   @return a {@link java.sql.Timestamp java.sql.Timestamp} object corresponding to the data type.
   The reference time zone for the object is GMT.
   **/
  public java.sql.Timestamp toTimestamp(byte[] as400Value, int offset)
  {
    return (java.sql.Timestamp)toObject(as400Value, offset);
  }


  /**
   Creates a new Date object representing the specified Timestamp object's nominal value, in the context of the specified time zone.
   That is, the timestamp is re-interpreted as if its reference context is the specified time zone.
   <p>For example, if this Timestamp object represents timestamp value "2000-01-01-00.00.00.000000 <b>GMT</b>", and the <tt>timezone</tt> parameter specifies CST, then this method will return a java.util.Date object representing timestamp value "2000-01-01-00.00.00.000000 <b>CST</b>".
   <p>Note that Date has precision of milliseconds, whereas Timestamp has nanosecond precision. When converting from Timestamp to Date, nanoseconds are rounded to the nearest millisecond.
   @param timezone The desired reference time zone for the returned Date object.
   @return A Date object representing the same nominal timestamp value as specified by <tt>timestamp</tt>, with time zone context <tt>timezone</tt>.
   **/
  public Date toDate(java.sql.Timestamp timestamp, TimeZone timezone)
  {
    if (timestamp == null) throw new NullPointerException("timestamp");
    if (timezone == null)  throw new NullPointerException("timezone");

    // We assume that the default/implied contextual timezone of all Timestamp objects is GMT.
    // This is certainly the case for all Timestamp objects created by this class.
    if (timezone.equals(TIMEZONE_GMT)) return (Date)timestamp;

    long millisSince1970 = timestamp.getTime();
    int nanosIntoSecond  = timestamp.getNanos();

    // For consistency with the QWCCVTDT API (used by the DateTimeConverter class), round up/down if fractional milliseconds are 500 microseconds or greater.
    int nanosIntoMillisecond = nanosIntoSecond % 1000000; // 1 millisec == 1,000,000 nanosecs
    if (nanosIntoMillisecond >= 500000) {  // half a million
      millisSince1970 += 1;  // round up to the next millisecond
    }
    else {} // truncate the partial milliseconds


    Date dateObj;
    synchronized (this) {
      getCalendar().setTimeInMillis(millisSince1970);
      dateObj = getCalendar().getTime();  // this object is based in GMT time zone
    }

    String dateAsString = getDateFormatterWithMillis(TIMEZONE_GMT).format(dateObj);

    // Create a new Date object in the desired timezone, representing the same timestamp string expression as above.

    // Note: In the US, depending on the year, Daylight Saving Time begins between March 8-14, and ends between November 1-7. Different JVMs may have different understandings of exactly when Daylight Saving Time starts and ends. This may lead to inconsistent setting of the time zone of the returned Date object, on dates that fall in those DST transition periods.
    try {
      return getDateFormatterWithMillis(timezone).parse(dateAsString);
    }
    catch (java.text.ParseException e) { // should never happen
      Trace.log(Trace.ERROR, e);
      throw new InternalErrorException(InternalErrorException.UNEXPECTED_EXCEPTION, e.getMessage());
    }
  }


  // Implements abstract method of superclass.
  /**
   Converts the specified Java object into a String representation that is consistent with the format of this data type.
   @param javaValue The object corresponding to the data type. This must be an instance of {@link java.sql.Timestamp java.sql.Timestamp}, and must be within the range specifiable by this data type.
   @return A String representation of the specified value, formatted appropriately for this data type.
   @throws ExtendedIllegalArgumentException if the specified date is outside of the range representable by this data type.
   **/
  public String toString(Object javaValue)
  {
    if (javaValue == null) throw new NullPointerException("javaValue");
    java.sql.Timestamp timeObj;
    try { timeObj = (java.sql.Timestamp)javaValue; }
    catch (ClassCastException e) {
      Trace.log(Trace.ERROR, "javaValue is of type " + javaValue.getClass().getName());
      throw e;
    }

    // Verify that the 'year' value from the date is within the range of our format.

    int year, era;
    synchronized (this) {
      Calendar cal = getCalendar(timeObj);
      year = cal.get(Calendar.YEAR);
      era = cal.get(Calendar.ERA);
    }
    if (year < 1 || year > 9999) {
      throw new ExtendedIllegalArgumentException("javaValue (year=" + year + ")", ExtendedIllegalArgumentException.RANGE_NOT_VALID);
    }
    if (era == 0) {  // we can't represent years BCE
      throw new ExtendedIllegalArgumentException("javaValue (era=0)", ExtendedIllegalArgumentException.RANGE_NOT_VALID);
    }

    String micros = to6Digits(timeObj.getNanos()/1000); // microseconds
    return ( getDateFormatter().format(timeObj) + "." + micros );  // append microseconds
  }

  // Implements abstract method of superclass.
  String patternFor(int format, char sep)
  {
    // SimpleDateFormat has a "milliseconds" pattern, but no "microseconds" or "nanoseconds" pattern.
    // Therefore, to generate a pattern consumable by SimpleDateFormat, we omit the fractional seconds entirely here.  We re-append the fractional seconds elsewhere in the code.
    return PARSING_PATTERN;
  }

  // Implements abstract method of superclass.
  char defaultSeparatorFor(int format)
  {
    return '-';
  }


  // Utility method used internally.
  private synchronized SimpleDateFormat getDateFormatterWithMillis(TimeZone timezone)
  {
    if (dateFormatterWithMillis_ == null) {
      dateFormatterWithMillis_ = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
      dateFormatterWithMillis_.setTimeZone(timezone);
    }
    else if (!dateFormatterWithMillis_.getTimeZone().equals(timezone)) {
      dateFormatterWithMillis_.setTimeZone(timezone);
    }
    return dateFormatterWithMillis_;
  }

  // Implements abstract method of superclass.
  boolean isValidFormat(int format)
  {
    return true;
  }

  // Implements abstract method of superclass.
  boolean isValidSeparator(char separator, int format)
  {
    return true;
  }



  // Implements abstract method of superclass.
  int lengthFor(int format)
  {
    switch (format)
    {
      case FORMAT_DTS:
        return 8;   // field length is 8 bytes

      default:  // FORMAT_DEFAULT
        return 26;   // field length is 26 bytes
    }
  }


  // Utility method.
  // Creates a 6-digit decimal string to represent an integer, prepending 0's as needed.  The value must be in the range 0 - 999999.
  static final String to6Digits(int value)
  {
    if (value < 0 || value > 999999) {
      throw new InternalErrorException(InternalErrorException.UNKNOWN, "to6Digits("+value+")");
    }
    StringBuffer buf = new StringBuffer(Integer.toString(value));
    int zerosToPrepend = 6 - buf.length();
    for (int i=0; i<zerosToPrepend; i++) {
      buf.insert(0, '0');
    }
    return buf.toString();
  }

}
