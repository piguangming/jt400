///////////////////////////////////////////////////////////////////////////////
//                                                                             
// AS/400 Toolbox for Java - OSS version                                       
//                                                                             
// Filename: ConvTable921.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2000 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

class ConvTable921 extends ConvTableAsciiMap
{
  private static final String copyright = "Copyright (C) 1997-2000 International Business Machines Corporation and others.";

  private static final String toUnicode_ = 
    "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000B\f\r\u000E\u000F" +
    "\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001A\u001B\u001C\u001D\u001E\u001F" +
    "\u0020\u0021\"\u0023\u0024\u0025\u0026\'\u0028\u0029\u002A\u002B\u002C\u002D\u002E\u002F" +
    "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037\u0038\u0039\u003A\u003B\u003C\u003D\u003E\u003F" +
    "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047\u0048\u0049\u004A\u004B\u004C\u004D\u004E\u004F" +
    "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057\u0058\u0059\u005A\u005B\\\u005D\u005E\u005F" +
    "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067\u0068\u0069\u006A\u006B\u006C\u006D\u006E\u006F" +
    "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077\u0078\u0079\u007A\u007B\u007C\u007D\u007E\u007F" +
    "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F" +
    "\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F" +
    "\u00A0\u201D\u00A2\u00A3\u00A4\u201E\u00A6\u00A7\u00D8\u00A9\u0156\u00AB\u00AC\u00AD\u00AE\u00C6" +
    "\u00B0\u00B1\u00B2\u00B3\u201C\u00B5\u00B6\u00B7\u00F8\u00B9\u0157\u00BB\u00BC\u00BD\u00BE\u00E6" +
    "\u0104\u012E\u0100\u0106\u00C4\u00C5\u0118\u0112\u010C\u00C9\u0179\u0116\u0122\u0136\u012A\u013B" +
    "\u0160\u0143\u0145\u00D3\u014C\u00D5\u00D6\u00D7\u0172\u0141\u015A\u016A\u00DC\u017B\u017D\u00DF" +
    "\u0105\u012F\u0101\u0107\u00E4\u00E5\u0119\u0113\u010D\u00E9\u017A\u0117\u0123\u0137\u012B\u013C" +
    "\u0161\u0144\u0146\u00F3\u014D\u00F5\u00F6\u00F7\u0173\u0142\u015B\u016B\u00FC\u017C\u017E\u2019";


  private static final String fromUnicode_ = 
    "\u0001\u0203\u0405\u0607\u0809\u0A0B\u0C0D\u0E0F\u1011\u1213\u1415\u1617\u1819\u1A1B\u1C1D\u1E1F" +
    "\u2021\u2223\u2425\u2627\u2829\u2A2B\u2C2D\u2E2F\u3031\u3233\u3435\u3637\u3839\u3A3B\u3C3D\u3E3F" +
    "\u4041\u4243\u4445\u4647\u4849\u4A4B\u4C4D\u4E4F\u5051\u5253\u5455\u5657\u5859\u5A5B\u5C5D\u5E5F" +
    "\u6061\u6263\u6465\u6667\u6869\u6A6B\u6C6D\u6E6F\u7071\u7273\u7475\u7677\u7879\u7A7B\u7C7D\u7E7F" +
    "\u8081\u8283\u8485\u8687\u8889\u8A8B\u8C8D\u8E8F\u9091\u9293\u9495\u9697\u9899\u9A9B\u9C9D\u9E9F" +
    "\uA01A\uA2A3\uA41A\uA6A7\u1AA9\u1AAB\uACAD\uAE1A\uB0B1\uB2B3\u1AB5\uB6B7\u1AB9\u1ABB\uBCBD\uBE1A" +
    "\u1A1A\u1A1A\uC4C5\uAF1A\u0000\u0003\u1AC9\u1A1A\u1A1A\uD3D5\uD6D7\uA81A\u1A1A\uDC1A\u1ADF\u1A1A" +
    "\u1A1A\uE4E5\uBF1A\u0000\u0003\u1AE9\u1A1A\u1A1A\uF3F5\uF6F7\uB81A\u1A1A\uFC1A\u1A1A\uC2E2\u1A1A" +
    "\uC0E0\uC3E3\u1A1A\u1A1A\uC8E8\u1A1A\u1A1A\uC7E7\u1A1A\uCBEB\uC6E6\uFFFF\u0004\u1A1A\uCCEC\u1A1A" +
    "\u1A1A\u1A1A\uCEEE\u1A1A\uC1E1\u1A1A\u1A1A\u1A1A\uCDED\u1A1A\u1ACF\uEF1A\u1A1A\u1AD9\uF9D1\uF1D2" +
    "\uF21A\u1A1A\u1A1A\uD4F4\uFFFF\u0004\u1A1A\uAABA\u1A1A\uDAFA\u1A1A\u1A1A\uD0F0\uFFFF\u0004\u1A1A" +
    "\uDBFB\u1A1A\u1A1A\u1A1A\uD8F8\u1A1A\u1A1A\u1ACA\uEADD\uFDDE\uFE1A\uFFFF\u0F4C\u1A1A\u1AFF\u1A1A" +
    "\uB4A1\uA51A\uFFFF\u6F70\u1A1A\u1A21\u2223\u2425\u2627\u2829\u2A2B\u2C2D\u2E2F\u3031\u3233\u3435" +
    "\u3637\u3839\u3A3B\u3C3D\u3E3F\u4041\u4243\u4445\u4647\u4849\u4A4B\u4C4D\u4E4F\u5051\u5253\u5455" +
    "\u5657\u5859\u5A5B\u5C5D\u5E5F\u6061\u6263\u6465\u6667\u6869\u6A6B\u6C6D\u6E6F\u7071\u7273\u7475" +
    "\u7677\u7879\u7A7B\u7C7D\u7E1A\uFFFF\u0050\u1A1A";


  ConvTable921()
  {
    super(921, toUnicode_.toCharArray(), fromUnicode_.toCharArray());
  }
}
