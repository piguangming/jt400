///////////////////////////////////////////////////////////////////////////////
//                                                                             
// AS/400 Toolbox for Java - OSS version                                       
//                                                                             
// Filename: ConvTable1089.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2000 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

class ConvTable1089 extends ConvTableBidiMap
{
  private static final String copyright = "Copyright (C) 1997-2000 International Business Machines Corporation and others.";

  private static final String toUnicode_ = 
    "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000B\f\r\u000E\u000F" +
    "\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001A\u001B\u001C\u001D\u001E\u001F" +
    "\u0020\u0021\"\u0023\u0024\u066A\u0026\'\u0028\u0029\u066D\u002B\u002C\u002D\u002E\u002F" +
    "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037\u0038\u0039\u003A\u003B\u003C\u003D\u003E\u003F" +
    "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047\u0048\u0049\u004A\u004B\u004C\u004D\u004E\u004F" +
    "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057\u0058\u0059\u005A\u005B\\\u005D\u005E\u005F" +
    "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067\u0068\u0069\u006A\u006B\u006C\u006D\u006E\u006F" +
    "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077\u0078\u0079\u007A\u007B\u007C\u007D\u007E\u007F" +
    "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F" +
    "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F" +
    "\u00A0\u001A\u001A\u001A\u00A4\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u060C\u00AD\u001A\u001A" +
    "\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u061B\u001A\u001A\u001A\u061F" +
    "\u001A\u0621\u0622\u0623\u0624\u0625\u0626\u0627\u0628\u0629\u062A\u062B\u062C\u062D\u062E\u062F" +
    "\u0630\u0631\u0632\u0633\u0634\u0635\u0636\u0637\u0638\u0639\u063A\u001A\u001A\u001A\u001A\u001A" +
    "\u0640\u0641\u0642\u0643\u0644\u0645\u0646\u0647\u0648\u0649\u064A\u064B\u064C\u064D\u064E\u064F" +
    "\u0650\u0651\u0652\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A\u001A";


  private static final String fromUnicode_ = 
    "\u0001\u0203\u0405\u0607\u0809\u0A0B\u0C0D\u0E0F\u1011\u1213\u1415\u1617\u1819\u1A1B\u1C1D\u1E1F" +
    "\u2021\u2223\u241A\u2627\u2829\u1A2B\u2C2D\u2E2F\u3031\u3233\u3435\u3637\u3839\u3A3B\u3C3D\u3E3F" +
    "\u4041\u4243\u4445\u4647\u4849\u4A4B\u4C4D\u4E4F\u5051\u5253\u5455\u5657\u5859\u5A5B\u5C5D\u5E5F" +
    "\u6061\u6263\u6465\u6667\u6869\u6A6B\u6C6D\u6E6F\u7071\u7273\u7475\u7677\u7879\u7A7B\u7C7D\u7E7F" +
    "\u8081\u8283\u8485\u8687\u8889\u8A8B\u8C8D\u8E8F\u9091\u9293\u9495\u9697\u9899\u9A9B\u9C9D\u9E9F" +
    "\uA01A\u1A1A\uA41A\u1A1A\u1A1A\u1A1A\u1AAD\uFFFF\u02AF\u1A1A\uAC1A\uFFFF\u0006\u1A1A\u1ABB\u1A1A" +
    "\u1ABF\u1AC1\uC2C3\uC4C5\uC6C7\uC8C9\uCACB\uCCCD\uCECF\uD0D1\uD2D3\uD4D5\uD6D7\uD8D9\uDA1A\u1A1A" +
    "\u1A1A\uE0E1\uE2E3\uE4E5\uE6E7\uE8E9\uEAEB\uECED\uEEEF\uF0F1\uF21A\uFFFF\u000B\u1A1A\u251A\u1A2A" +
    "\uFFFF\u7CC9\u1A1A";


  ConvTable1089()
  {
    super(1089, toUnicode_.toCharArray(), fromUnicode_.toCharArray());
  }
}
