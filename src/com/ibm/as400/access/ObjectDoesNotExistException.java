///////////////////////////////////////////////////////////////////////////////
//                                                                             
// AS/400 Toolbox for Java - OSS version                                       
//                                                                             
// Filename: ObjectDoesNotExistException.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2000 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;



/**
   The ObjectDoesNotExistException class represents an exception 
   that indicates that an AS/400 object does not exists.
**/
public class ObjectDoesNotExistException extends Exception
                              implements ReturnCodeException
{   
  private static final String copyright = "Copyright (C) 1997-2000 International Business Machines Corporation and others.";

   private int rc_;  // Return code associated with this exception

   //  Handles loading the appropriate resource bundle
   private static ResourceBundleLoader loader_;

   // Return code values used by this class. 
   // If a value is added here, it must also be added to MRI.properties.
 
   /**
       The return code indicating that
       the library does not exist.
   **/
   public static final int LIBRARY_DOES_NOT_EXIST = 1;

   /**
       The return code indicating that
       the object does not exist.
   **/
   public static final int OBJECT_DOES_NOT_EXIST = 2;





    /**
       Constructs an ObjectDoesNotExistException object.
       It indicates that an AS/400 object does not exist.
       Exception message will look like this: Object does not exist. 
       @param returnCode The return code which identifies the message to be returned.
    **/
    
    ObjectDoesNotExistException(int returnCode)
    {
      // Create the message
      super(loader_.getText(getMRIKey(returnCode)));
      rc_ =  returnCode;        
    }



   /**
      Constructs an ObjectDoesNotExistException object.
      It indicates that an AS/400 object does not exist.
      Exception message will look like this:
      dataQueue (/QSYS.LIB/MYLIB.LIB/MYQUEUE.DTAQ): Object does not exist. 
      @param objectName The object that does not exist.
                        It should be in the format: argumentName (value).
                        For example: dataQueue (mydataqueue).
      @param returnCode The return code which identifies the message to be returned.
   **/
   
   ObjectDoesNotExistException(String objectName, int returnCode)
   {
     // Create the message
     super(objectName + ": " + loader_.getText(getMRIKey(returnCode)));
     rc_ =  returnCode;        
   }



    
  /**
     Returns the text associated with the return code.
     @param returnCode  The return code associated with this exception.
     @return The text string which describes the error. 
  **/
  // This method is required so the message can be created and sent in super()
  static String getMRIKey (int returnCode)
  {
     switch(returnCode)
     {
         case LIBRARY_DOES_NOT_EXIST:
            return "EXC_LIBRARY_DOES_NOT_EXIST";
	 case OBJECT_DOES_NOT_EXIST:
            return "EXC_OBJECT_DOES_NOT_EXIST";
	 default:
             return "EXC_UNKNOWN";   // Bad return code was provided. 
     }
  }  



   /**
      Returns the return code associated with this exception.
      @return The return code.
   **/
   public int getReturnCode ()
   {
     return rc_;		
   }




}  // End of ObjectDoesNotExistException class
