///////////////////////////////////////////////////////////////////////////////
//                                                                             
// AS/400 Toolbox for Java - OSS version                                       
//                                                                             
// Filename: DBSQLDescriptorDS.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2000 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

/**
   Create SQL Descriptor data stream
**/
class DBSQLDescriptorDS
extends DBBaseRequestDS

{
  private static final String copyright = "Copyright (C) 1997-2000 International Business Machines Corporation and others.";

  public static final int	FUNCTIONID_CHANGE_DESCRIPTOR     = 0x1E00;
  public static final int	FUNCTIONID_DELETE_DESCRIPTOR     = 0x1E01;


/**
   Constructs a datastream for the SQL Server Descriptor functions.
   @param  requestId the 4 digit code that represents the function being called.
   @param  rpbId   the request parameter block id.
   @param  operationResultsBitmap the bitmap which describes how the results are to be returned.
   @param  parameterMarkerDescriptorHandle the Parameter marker descriptor handle identifier.
**/

  public DBSQLDescriptorDS(int requestId,
                             int rpbId,
			     int operationResultsBitmap,
			     int parameterMarkerDescriptorHandle)
  {
    // Create the datastream header and template
    super(requestId, rpbId, operationResultsBitmap,
		     parameterMarkerDescriptorHandle);
	setServerID(SERVER_SQL);
  }



// Returns the copyright.
  private static String getCopyright()
  {
    return Copyright.copyright;
  }


       //--------------------------------------------------//
       // Create the data stream optional /         	   //
       // variable length data section via addParameters   //
       //--------------------------------------------------//

/**
   Sets the Parameter Marker Data Format parameter in the data stream.
   @param value	the data structure that contains the format
   of the parameter marker data.
   @exception DBDataStreamException If there is not enough space left in the data byte array.
**/
   void setParameterMarkerDataFormat (DBDataFormat value)
		throws DBDataStreamException
   {
        if (value instanceof DBOriginalDataFormat)
            addParameter (0x3801, value);
        else if (value instanceof DBExtendedDataFormat)
            addParameter (0x381E, value);
        else
            throw new DBDataStreamException ();
   }



/**
   Sets the Translate Indicator parameter in the data stream.
   @param value	the value that indicates if the data in the
   operational result set generated by this function should be
   translated to the client's CCSID before the data is returned.
   @exception DBDataStreamException If there is not enough space left in the data byte array.
**/
    void setTranslateIndicator (int value)
		throws DBDataStreamException
	{
		addParameter (0x3805, (byte) value);
	}


}






