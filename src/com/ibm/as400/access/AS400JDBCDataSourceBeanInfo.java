///////////////////////////////////////////////////////////////////////////////
//                                                                             
// JTOpen (IBM Toolbox for Java - OSS version)                              
//                                                                             
// Filename: AS400JDBCDataSourceBeanInfo.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2001 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;


/**
*  The AS400JDBCDataSourceBeanInfo class provides bean information
*  for the AS400JDBCDataSource class.
**/
public class AS400JDBCDataSourceBeanInfo extends SimpleBeanInfo
{
  private static final String copyright = "Copyright (C) 1997-2001 International Business Machines Corporation and others.";


   // Class this bean info represents.
   private final static Class beanClass = AS400JDBCDataSource.class;

   // Handles loading the appropriate resource bundle
   private static ResourceBundleLoader loader_;
   private static EventSetDescriptor[] events_;
   private static PropertyDescriptor[] properties_;

   static
   {
     try
     {
       EventSetDescriptor changed = new EventSetDescriptor(beanClass,
                                                           "propertyChange",
                                                           java.beans.PropertyChangeListener.class,
                                                           "propertyChange");
       changed.setDisplayName(loader_.getText("EVT_NAME_PROPERTY_CHANGE"));
       changed.setShortDescription(loader_.getText("EVT_DESC_PROPERTY_CHANGE"));

       EventSetDescriptor[] events = { changed };

       events_ = events;

       // ***** PROPERTIES
       PropertyDescriptor access = new PropertyDescriptor("access", beanClass, "getAccess", "setAccess");       
       access.setBound(true);
       access.setConstrained(false);
       access.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_ACCESS"));
       access.setShortDescription(AS400JDBCDriver.getResource("ACCESS_DESC"));

       PropertyDescriptor bidiStringType = new PropertyDescriptor("bidiStringType", beanClass, "getBidiStringType", "setBidiStringType"); // @A3A
       bidiStringType.setBound(true);                                                                                       // @A3A
       bidiStringType.setConstrained(false);                                                                                // @A3A
       bidiStringType.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_BIDI_STRING_TYPE"));                            // @A3A
       bidiStringType.setShortDescription(AS400JDBCDriver.getResource("BIDI_STRING_TYPE_DESC"));			    // @A3A

       PropertyDescriptor bigDecimal = new PropertyDescriptor("bigDecimal", beanClass, "isBigDecimal", "setBigDecimal");
       bigDecimal.setBound(true);
       bigDecimal.setConstrained(false);
       bigDecimal.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_BIG_DECIMAL"));
       bigDecimal.setShortDescription(AS400JDBCDriver.getResource("BIG_DECIMAL_DESC"));

       PropertyDescriptor blockCriteria = new PropertyDescriptor("blockCriteria", beanClass, "getBlockCriteria", "setBlockCriteria");
       blockCriteria.setBound(true);
       blockCriteria.setConstrained(false);
       blockCriteria.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_BLOCK_CRITERIA"));
       blockCriteria.setShortDescription(AS400JDBCDriver.getResource("BLOCK_CRITERIA_DESC"));

       PropertyDescriptor blockSize = new PropertyDescriptor("blockSize", beanClass, "getBlockSize", "setBlockSize");
       blockSize.setBound(true);
       blockSize.setConstrained(false);
       blockSize.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_BLOCK_SIZE"));
       blockSize.setShortDescription(AS400JDBCDriver.getResource("BLOCK_SIZE_DESC"));

       PropertyDescriptor cursorHold = new PropertyDescriptor("cursorHold", beanClass, "isCursorHold", "setCursorHold");
       cursorHold.setBound(true);
       cursorHold.setConstrained(false);
       cursorHold.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_CURSOR_HOLD"));
       cursorHold.setShortDescription(AS400JDBCDriver.getResource("CURSOR_HOLD_DESC"));

       PropertyDescriptor databaseName = new PropertyDescriptor("databaseName", beanClass, "getDatabaseName", "setDatabaseName");
       databaseName.setBound(true);
       databaseName.setConstrained(false);
       databaseName.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATABASE_NAME"));
       databaseName.setShortDescription(AS400JDBCDriver.getResource("DATABASE_NAME_DESC"));

       PropertyDescriptor dataCompression = new PropertyDescriptor("dataCompression", beanClass, "isDataCompression", "setDataCompression");
       dataCompression.setBound(true);
       dataCompression.setConstrained(false);
       dataCompression.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATA_COMPRESSION"));
       dataCompression.setShortDescription(AS400JDBCDriver.getResource("DATA_COMPRESSION_DESC"));

       PropertyDescriptor dataSourceName = new PropertyDescriptor("dataSourceName", beanClass, "getDataSourceName", "setDataSourceName");
       dataSourceName.setBound(true);
       dataSourceName.setConstrained(false);
       dataSourceName.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATASOURCE_NAME"));
       dataSourceName.setShortDescription(AS400JDBCDriver.getResource("DATASOURCE_NAME_DESC"));

       PropertyDescriptor dataTruncation = new PropertyDescriptor("dataTruncation", beanClass, "isDataTruncation", "setDataTruncation");
       dataTruncation.setBound(true);
       dataTruncation.setConstrained(false);
       dataTruncation.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATA_TRUNCATION"));
       dataTruncation.setShortDescription(AS400JDBCDriver.getResource("DATA_TRUNCATION_DESC"));

       PropertyDescriptor dateFormat = new PropertyDescriptor("dateFormat", beanClass, "getDateFormat", "setDateFormat");
       dateFormat.setBound(true);
       dateFormat.setConstrained(false);
       dateFormat.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATE_FORMAT"));
       dateFormat.setShortDescription(AS400JDBCDriver.getResource("DATE_FORMAT_DESC"));

       PropertyDescriptor dateSeparator = new PropertyDescriptor("dateSeparator", beanClass, "getDateSeparator", "setDateSeparator");
       dateSeparator.setBound(true);
       dateSeparator.setConstrained(false);
       dateSeparator.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DATE_SEPARATOR"));
       dateSeparator.setShortDescription(AS400JDBCDriver.getResource("DATE_SEPARATOR_DESC"));

       PropertyDescriptor decimalSeparator = new PropertyDescriptor("decimalSeparator", beanClass, "getDecimalSeparator", "setDecimalSeparator");
       decimalSeparator.setBound(true);
       decimalSeparator.setConstrained(false);
       decimalSeparator.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DECIMAL_SEPARATOR"));
       decimalSeparator.setShortDescription(AS400JDBCDriver.getResource("DECIMAL_SEPARATOR_DESC"));

       PropertyDescriptor description = new PropertyDescriptor("description", beanClass, "getDescription", "setDescription");
       description.setBound(true);
       description.setConstrained(false);
       description.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DESCRIPTION"));
       description.setShortDescription(AS400JDBCDriver.getResource("DESCRIPTION_DESC"));

       PropertyDescriptor driver = new PropertyDescriptor("driver", beanClass, "getDriver", "setDriver");   // @A2A
       driver.setBound(true);                                                                               // @A2A
       driver.setConstrained(false);                                                                        // @A2A
       driver.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_DRIVER"));                              // @A2A
       driver.setShortDescription(AS400JDBCDriver.getResource("DRIVER_DESC"));                              // @A2A

       PropertyDescriptor errors = new PropertyDescriptor("errors", beanClass, "getErrors", "setErrors");
       errors.setBound(true);
       errors.setConstrained(false);
       errors.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_ERRORS"));
       errors.setShortDescription(AS400JDBCDriver.getResource("ERRORS_DESC"));

       PropertyDescriptor extendedDynamic = new PropertyDescriptor("extendedDynamic", beanClass, "isExtendedDynamic", "setExtendedDynamic");
       extendedDynamic.setBound(true);
       extendedDynamic.setConstrained(false);
       extendedDynamic.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_EXTENDED_DYNAMIC"));
       extendedDynamic.setShortDescription(AS400JDBCDriver.getResource("EXTENDED_DYNAMIC_DESC"));

       PropertyDescriptor fullOpen = new PropertyDescriptor("fullOpen", beanClass, "isFullOpen", "setFullOpen");    // @W1A
       fullOpen.setBound(true);                                                                                     // @W1A
       fullOpen.setConstrained(false);                                                                              // @W1A
       fullOpen.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_FULL_OPEN"));                                 // @W1A
       fullOpen.setShortDescription(AS400JDBCDriver.getResource("FULL_OPEN_DESC"));                                 // @W1A

       PropertyDescriptor lazyClose = new PropertyDescriptor("lazyClose", beanClass, "isLazyClose", "setLazyClose");    // @A1A
       lazyClose.setBound(true);                                                                                       // @A1A
       lazyClose.setConstrained(false);                                                                                 // @A1A
       lazyClose.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_LAZY_CLOSE"));                                  // @A1A
       lazyClose.setShortDescription(AS400JDBCDriver.getResource("LAZY_CLOSE_DESC"));                                  // @A1A

       PropertyDescriptor libraries = new PropertyDescriptor("libraries", beanClass, "getLibraries", "setLibraries");
       libraries.setBound(true);
       libraries.setConstrained(false);
       libraries.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_LIBRARIES"));
       libraries.setShortDescription(AS400JDBCDriver.getResource("LIBRARIES_DESC"));

       PropertyDescriptor lobThreshold = new PropertyDescriptor("lobThreshold", beanClass, "getLobThreshold", "setLobThreshold");
       lobThreshold.setBound(true);
       lobThreshold.setConstrained(false);
       lobThreshold.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_LOB_THRESHOLD"));
       lobThreshold.setShortDescription(AS400JDBCDriver.getResource("LOB_THRESHOLD_DESC"));

       PropertyDescriptor naming = new PropertyDescriptor("naming", beanClass, "getNaming", "setNaming");
       naming.setBound(true);
       naming.setConstrained(false);
       naming.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_NAMING"));
       naming.setShortDescription(AS400JDBCDriver.getResource("NAMING_DESC"));

       PropertyDescriptor packageName = new PropertyDescriptor("package", beanClass, "getPackage", "setPackage");
       packageName.setBound(true);
       packageName.setConstrained(false);
       packageName.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE"));
       packageName.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_DESC"));

       PropertyDescriptor packageAdd = new PropertyDescriptor("packageAdd", beanClass, "isPackageAdd", "setPackageAdd");
       packageAdd.setBound(true);
       packageAdd.setConstrained(false);
       packageAdd.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_ADD"));
       packageAdd.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_ADD_DESC"));

       PropertyDescriptor packageCache = new PropertyDescriptor("packageCache", beanClass, "isPackageCache", "setPackageCache");
       packageCache.setBound(true);
       packageCache.setConstrained(false);
       packageCache.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_CACHE"));
       packageCache.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_CACHE_DESC"));

       PropertyDescriptor packageClear = new PropertyDescriptor("packageClear", beanClass, "isPackageClear", "setPackageClear");
       packageClear.setBound(true);
       packageClear.setConstrained(false);
       packageClear.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_CLEAR"));
       packageClear.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_CLEAR_DESC"));

       PropertyDescriptor packageCriteria = new PropertyDescriptor("packageCriteria", beanClass, "getPackageCriteria", "setPackageCriteria");
       packageCriteria.setBound(true);
       packageCriteria.setConstrained(false);
       packageCriteria.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_CRITERIA"));
       packageCriteria.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_CRITERIA_DESC"));

       PropertyDescriptor packageError = new PropertyDescriptor("packageError", beanClass, "getPackageError", "setPackageError");
       packageError.setBound(true);
       packageError.setConstrained(false);
       packageError.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_ERROR"));
       packageError.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_ERROR_DESC"));

       PropertyDescriptor packageLibrary = new PropertyDescriptor("packageLibrary", beanClass, "getPackageLibrary", "setPackageLibrary");
       packageLibrary.setBound(true);
       packageLibrary.setConstrained(false);
       packageLibrary.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PACKAGE_LIBRARY"));
       packageLibrary.setShortDescription(AS400JDBCDriver.getResource("PACKAGE_LIBRARY_DESC"));

       PropertyDescriptor password = new PropertyDescriptor("password", beanClass, null, "setPassword");
       password.setBound(true);
       password.setConstrained(false);
       password.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PASSWORD"));
       password.setShortDescription(AS400JDBCDriver.getResource("PASSWORD_DESC"));

       PropertyDescriptor prefetch = new PropertyDescriptor("prefetch", beanClass, "isPrefetch", "setPrefetch");
       prefetch.setBound(true);
       prefetch.setConstrained(false);
       prefetch.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PREFETCH"));
       prefetch.setShortDescription(AS400JDBCDriver.getResource("PREFETCH_DESC"));

       PropertyDescriptor prompt = new PropertyDescriptor("prompt", beanClass, "isPrompt", "setPrompt");
       prompt.setBound(true);
       prompt.setConstrained(false);
       prompt.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PROMPT"));
       prompt.setShortDescription(AS400JDBCDriver.getResource("PROMPT_DESC"));

       PropertyDescriptor proxyServer = new PropertyDescriptor("proxyServer", beanClass, "getProxyServer", "setProxyServer");
       proxyServer.setBound(true);
       proxyServer.setConstrained(false);
       proxyServer.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_PROXY_SERVER"));
       proxyServer.setShortDescription(AS400JDBCDriver.getResource("PROXY_SERVER_DESC"));

       PropertyDescriptor remarks = new PropertyDescriptor("remarks", beanClass, "getRemarks", "setRemarks");
       remarks.setBound(true);
       remarks.setConstrained(false);
       remarks.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_REMARKS"));
       remarks.setShortDescription(AS400JDBCDriver.getResource("REMARKS_DESC"));

       PropertyDescriptor secondaryUrl = new PropertyDescriptor("secondaryUrl", beanClass, "getSecondaryUrl", "setSecondaryUrl");
       secondaryUrl.setBound(true);
       secondaryUrl.setConstrained(false);
       secondaryUrl.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SECONDARY_URL"));
       secondaryUrl.setShortDescription(AS400JDBCDriver.getResource("SECONDARY_URL_DESC"));

       PropertyDescriptor secure = new PropertyDescriptor("secure", beanClass, "isSecure", "setSecure");
       secure.setBound(true);
       secure.setConstrained(false);
       secure.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SECURE"));
       secure.setShortDescription(AS400JDBCDriver.getResource("SECURE_DESC"));

       PropertyDescriptor serverName = new PropertyDescriptor("serverName", beanClass, "getServerName", "setServerName");
       serverName.setBound(true);
       serverName.setConstrained(false);
       serverName.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SERVER_NAME"));
       serverName.setShortDescription(AS400JDBCDriver.getResource("SERVER_NAME_DESC"));

       PropertyDescriptor sort = new PropertyDescriptor("sort", beanClass, "getSort", "setSort");
       sort.setBound(true);
       sort.setConstrained(false);
       sort.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SORT"));
       sort.setShortDescription(AS400JDBCDriver.getResource("SORT_DESC"));

       PropertyDescriptor sortLanguage = new PropertyDescriptor("sortLanguage", beanClass, "getSortLanguage", "setSortLanguage");
       sortLanguage.setBound(true);
       sortLanguage.setConstrained(false);
       sortLanguage.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SORT_LANGUAGE"));
       sortLanguage.setShortDescription(AS400JDBCDriver.getResource("SORT_LANGUAGE_DESC"));

       PropertyDescriptor sortTable = new PropertyDescriptor("sortTable", beanClass, "getSortTable", "setSortTable");
       sortTable.setBound(true);
       sortTable.setConstrained(false);
       sortTable.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SORT_TABLE"));
       sortTable.setShortDescription(AS400JDBCDriver.getResource("SORT_TABLE_DESC"));

       PropertyDescriptor sortWeight = new PropertyDescriptor("sortWeight", beanClass, "getSortWeight", "setSortWeight");
       sortWeight.setBound(true);
       sortWeight.setConstrained(false);
       sortWeight.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_SORT_WEIGHT"));
       sortWeight.setShortDescription(AS400JDBCDriver.getResource("SORT_WEIGHT_DESC"));

       PropertyDescriptor threadUsed = new PropertyDescriptor("threadUsed", beanClass, "isThreadUsed", "setThreadUsed");
       threadUsed.setBound(true);
       threadUsed.setConstrained(false);
       threadUsed.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_THREAD_USED"));
       threadUsed.setShortDescription(AS400JDBCDriver.getResource("THREAD_USED_DESC"));

       PropertyDescriptor timeFormat = new PropertyDescriptor("timeFormat", beanClass, "getTimeFormat", "setTimeFormat");
       timeFormat.setBound(true);
       timeFormat.setConstrained(false);
       timeFormat.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_TIME_FORMAT"));
       timeFormat.setShortDescription(AS400JDBCDriver.getResource("TIME_FORMAT_DESC"));

       PropertyDescriptor timeSeparator = new PropertyDescriptor("timeSeparator", beanClass, "getTimeSeparator", "setTimeSeparator");
       timeSeparator.setBound(true);
       timeSeparator.setConstrained(false);
       timeSeparator.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_TIME_SEPARATOR"));
       timeSeparator.setShortDescription(AS400JDBCDriver.getResource("TIME_SEPARATOR_DESC"));

       PropertyDescriptor trace = new PropertyDescriptor("trace", beanClass, "isTrace", "setTrace");  // @W2c
       trace.setBound(true);
       trace.setConstrained(false);
       trace.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_TRACE"));
       trace.setShortDescription(AS400JDBCDriver.getResource("TRACE_DESC"));

       PropertyDescriptor transactionIsolation = new PropertyDescriptor("transactionIsolation", beanClass, "getTransactionIsolation", "setTransactionIsolation");
       transactionIsolation.setBound(true);
       transactionIsolation.setConstrained(false);
       transactionIsolation.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_TRANSACTION_ISOLATION"));
       transactionIsolation.setShortDescription(AS400JDBCDriver.getResource("TRANSACTION_ISOLATION_DESC"));

       PropertyDescriptor translateBinary = new PropertyDescriptor("translateBinary", beanClass, "isTranslateBinary", "setTranslateBinary");
       translateBinary.setBound(true);
       translateBinary.setConstrained(false);
       translateBinary.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_TRANSLATE_BINARY"));
       translateBinary.setShortDescription(AS400JDBCDriver.getResource("TRANSLATE_BINARY_DESC"));

       PropertyDescriptor user = new PropertyDescriptor("user", beanClass, "getUser", "setUser");
       user.setBound(true);
       user.setConstrained(false);
       user.setDisplayName(AS400JDBCDriver.getResource("PROP_NAME_USER"));
       user.setShortDescription(AS400JDBCDriver.getResource("USER_DESC"));

       properties_ = new PropertyDescriptor[] { access, bidiStringType, bigDecimal, blockCriteria, blockSize, cursorHold, databaseName, dataCompression, dataSourceName, dataTruncation, dateFormat, dateSeparator, //@A4C 
          decimalSeparator, description, driver, errors, extendedDynamic, fullOpen, lazyClose, libraries, lobThreshold, naming, packageName, packageAdd, packageCache, packageClear,                                //@W1c
          packageCriteria, packageError, packageLibrary, password, prefetch, prompt, proxyServer, remarks, secondaryUrl, secure, serverName, sort,
          sortLanguage, sortTable, sortWeight, threadUsed, timeFormat, timeSeparator, trace, transactionIsolation, translateBinary, user };
     }
     catch (Exception e)
     {
       throw new Error(e.toString());
     }
   }

   /**
     Returns the bean descriptor.
     @return The bean descriptor.
   **/
   public BeanDescriptor getBeanDescriptor()
   {
       return new BeanDescriptor(beanClass);
   }


   /**
     Returns the index of the default event.
     @return The index to the default event.
   **/
   public int getDefaultEventIndex()
   {
       return 0;
   }

   /**
     Returns the index of the default property.
     @return The index to the default property.
   **/
   public int getDefaultPropertyIndex()
   {
       return 0;
   }

   /**
     Returns the descriptors for all events.
     @return The descriptors for all events.
   **/
   public EventSetDescriptor[] getEventSetDescriptors()
   {
       return events_;
   }

   /**
     Returns an image for the icon.

     @param icon    The icon size and color.
     @return        The image.
   **/
   public Image getIcon (int icon)
   {
       Image image = null;
       switch (icon) {
           case BeanInfo.ICON_MONO_16x16:
           case BeanInfo.ICON_COLOR_16x16:
               image = loadImage ("AS400JDBCDataSource16.gif");
               break;
           case BeanInfo.ICON_MONO_32x32:
           case BeanInfo.ICON_COLOR_32x32:
               image = loadImage ("AS400JDBCDataSource32.gif");
               break;
       }
       return image;
   }
   
   /**
     Returns the descriptors for all properties.
     @return The descriptors for all properties.
   **/
   public PropertyDescriptor[] getPropertyDescriptors()
   {
       return properties_;
   }
}
