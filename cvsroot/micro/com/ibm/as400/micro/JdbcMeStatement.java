///////////////////////////////////////////////////////////////////////////////
//                                                                             
// JTOpen (IBM Toolbox for Java - OSS version)                                 
//                                                                             
// Filename: JdbcMeStatement.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2001 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.micro;

import java.sql.*;
import java.io.*;

/**
 *  The AS400JDBCStatement class provides a mechanism for
 *  executing static SQL statements. Use Connection.createStatement()
 *  to create new Statement objects.
 *
 *  <p>Only one result set per statement can be open at any point in time.
 *  Therefore, if an application needs to read from multiple result sets,
 *  then each must be generated by a different statement.
 *
 *  <p><b>Note:</b> Since Java 2 Micro-Edition does not include java.sql,
 *  JdbcMeStatement implements the java.sql package that is also part 
 *  of this driver.
 **/
public class JdbcMeStatement implements Statement
{
    JdbcMeConnection connection_ = null;
    private int statementId_ = -1;

    // The update count is only valid after an execute()
    // returns false, or executeUpdate() is used.
    private int updateCount_ = -1;

    // The following are only valid when execute() returns
    // true, or executeQuery() is used.
    private JdbcMeLiveResultSet rs_ = null;
    int numColumns_ = 0;
    int columnTypes_[] = null;
    int type_ = ResultSet.TYPE_FORWARD_ONLY;
    int concurrency_ = ResultSet.CONCUR_READ_ONLY;

    /**
     *  Constructs an JdbcMeStatement object.
     *
     *  @param  connection  The connection to the system.
     *  @param  statementId  The statement ID handle.
     *
     *  @exception JdbcMeException If an error occurs.
     **/
    JdbcMeStatement(JdbcMeConnection connection, int statementId) throws JdbcMeException 
    {
        connection_  = connection;
        statementId_ = statementId;
    }

    /**
     *  Releases the statement's resources.  This closes the current
     *  result set.
     *
     *  @exception JdbcMeException If an error occurs.
     **/
    public void close() throws JdbcMeException 
    {
        /**
         *  Line flow out:
         *    Function ID
         *    Statement Handle ID
         * Line flow in:
         *    None
         **/
        try
        {
            connection_.system_.toServer_.writeInt(MEConstants.STMT_CLOSE);
            connection_.system_.toServer_.writeInt(statementId_);
            connection_.system_.toServer_.flush();

            if (rs_ != null)
                rs_.closeHard();

            // Don't wait for ack.
            return;
        }
        catch (IOException e)
        {
            // If an IOException occurs, our connection to the database
            // has been toasted. Lets reset it.
            connection_.disconnected();

            throw new JdbcMeException(e.toString(), null);
        }
    }

    /**
     *  Runs an SQL statement that returns a single result set.
     *  This closes the current result set and clears warnings
     *  before executing the new SQL statement.
     *
     *  @param  sql     The SQL statement.
     *  
     *  @return         The result set that contains the data produced by the query.
     *
     *  @exception      JdbcMeException    If the statement is not open,
     *                           the SQL statement contains a syntax
     *                           error, no result set is returned by the
     *                           database, the query timeout limit is
     *                           exceeded, or an error occurs.
     **/
    public ResultSet executeQuery(String sql) throws JdbcMeException 
    {
        boolean results = execute(sql);

        if (results)
            return getResultSet();
        else
            return null;
    }

    /**
     *  Runs an SQL INSERT, UPDATE, or DELETE statement, or any
     *  SQL statement that does not return a result set.
     *  This closes the current result set and clears warnings
     *  before executing the new SQL statement.
     *
     *  @param  sql     The SQL statement.
     *  
     *  @return         Either the row count for INSERT, UPDATE, or
     *                      DELETE, or 0 for SQL statements that
     *                      return nothing.
     *
     *  @exception      JdbcMeException    If the statement is not open,
     *                           the SQL statement contains a syntax
     *                           error, the query timeout limit is
     *                           exceeded, the statement returns
     *                           a result set, or an error occurs.
     **/
    public int executeUpdate(String sql) throws JdbcMeException 
    {
        boolean results = execute(sql);

        if (results)
            return -1;
        else
            return updateCount_;
    }


    /**
     *  Runs an SQL statement that may return multiple result sets.
     *  This closes the current result set and clears warnings before
     *  executing the new SQL statement.
     *
     *  <p>Under some situations, a single SQL statement may return
     *  multiple result sets, an update count, or both.  This might occur
     *  when executing a stored procedure that returns multiple
     *  result sets or when dynamically executing an unknown SQL string.
     *  
     *  <p>Use getResultSet() and getUpdateCount()
     *  to navigate through multiple result sets, an update count, or both.
     *
     *  @param  sql     The SQL statement.
     *
     *  @return         true if a result set was returned, false if an update count was returned or nothing was returned.
     *
     *  @exception      JdbcMeException    If the statement is not open,
     *                                                      the SQL statement contains a syntax
     *                                                      error, the query timeout limit is
     *                                                      exceeded, or an error occurs.
     **/
    public boolean execute(String sql) throws JdbcMeException 
    {
        /**
         *  Line flows out:
         *    Function ID
         *    Statement Handle ID
         *    UTF String for SQL statement
         * Line flows in:
         *    -1 and exception data follows
         *      OR
         *    0 and update count follows as integer
         *      OR
         *    1 and result set data follows as:
         *      Result set handle ID
         *      Integer number of columns
         *      Integer column type for each column
         **/
        try
        {
            updateCount_ = -1;

            if (rs_ != null)
            {
                rs_.closeHard();
                rs_ = null;
            }

            connection_.system_.toServer_.writeInt(MEConstants.STMT_EXECUTE);
            connection_.system_.toServer_.writeInt(statementId_);
            connection_.system_.toServer_.writeUTF(sql);
            connection_.system_.toServer_.flush();

            int   isRs = connection_.system_.fromServer_.readInt();

            if (isRs == -1)
                JdbcMeDriver.processException(connection_);

            if (isRs == 0)
            {
                updateCount_ = connection_.system_.fromServer_.readInt();

                return false;
            }
            else
            {
                int   rsId = connection_.system_.fromServer_.readInt();

                rs_ = new JdbcMeLiveResultSet(this, rsId);

                numColumns_ = connection_.system_.fromServer_.readInt();
                columnTypes_ = new int[numColumns_];

                for (int i=0; i<numColumns_; ++i)
                {
                    columnTypes_[i] = connection_.system_.fromServer_.readInt();
// We use to eliminate unsupported column types. Now, the server
// generates a string for any unsupported column. Only integer
// columns are treated differently.
// Eventually we'll support more column types.
// Allow all column types. Currently we'll treat everything
// as a string on this side of the pipe, and let JDBC server
// do the right thin.
//             switch (columnTypes[i]) {
//             case Types.CHAR:
//             case Types.VARCHAR:
//             case Types.INTEGER:
//             break;
//             default :
//                throw new IllegalArgumentException("Bad Type: " +
//                                                   columnTypes[i]);
//             }
                }
                return true;
            }
        }
        catch (IOException e)
        {
            // If an IOException occurs, our connection to the database
            // has been toasted. Lets reset it.
            connection_.disconnected();
            throw new JdbcMeException(e.toString(), null);
        }
    }


    /**
     * Create or overwrite an offline device specific
     * database containing the results of the query
     * specified. This offline database can be accessed
     * without a connection to the host database server.
     * <p>
     * If the SQL statement does not generate query results,
     * then the target offline database is not changed.
     * <p>
     * If the target offline database exists, it is destroyed
     * and its contents are filled with the results of the query.
     * <p>
     * Use JdbcMeOfflineResultSet to access the data from the
     * offline data at a later time (for example after disconnecting
     * from the DB server.
     * <p>
     * <pre>
     * For example.
     *  MIDP - The 'name' is a unique string of up to 32
     *         unique characters identifying a
     *         javax.microedition.rms.RecordStore object
     *         the offline data object returned, then
     *         encapsulates the RecordStore object.
     *
     *  PALM - The 'name' is effectively just a visual
     *         key, while the offline data is uniquely
     *         identified by the 'dbCreator' and the
     *         'dbType' parameters.
     *  </pre>
     *  @param sql  The SQL statement.
     *  @param dbName  The name of the offline database.
     *  @param dbCreator The unique offline database creator identifier.
     *  @param dbType  The unique offline database type identifier.
     *
     *  @return         true if a result set was returned to the offline database, false if an update count was returned or nothing was returned.
     *
     *  @exception      JdbcMeException    If the statement is not open,
     *                                                      the SQL statement contains a syntax
     *                                                      error, the query timeout limit is
     *                                                      exceeded, or an error occurs.
     **/
    public boolean executeToOfflineData(String sql, String dbName, int dbCreator, int dbType) throws JdbcMeException 
    {
        boolean results = execute(sql);

        // No result set. Return immediately after executing the statement.
        if (!results)
            return false;

        JdbcMeOfflineData DB = null;

        try
        {
            int i;

            DB = JdbcMeOfflineData.create(dbName, dbCreator, dbType);

            if (DB == null)
                throw new JdbcMeException("Couldn't open/create DB " + dbName, null);

            byte data[] = new byte[4];
            ResultSet rs = getResultSet();

            // EyeCatcher.
            data[0]  = (byte)((0xFF000000 & JdbcMeOfflineResultSet.dbEyeCatcher_) >>> 24);
            data[1]  = (byte)((0x00FF0000 & JdbcMeOfflineResultSet.dbEyeCatcher_) >>> 16);
            data[2]  = (byte)((0x0000FF00 & JdbcMeOfflineResultSet.dbEyeCatcher_) >>>  8);
            data[3]  = (byte)((0x000000FF & JdbcMeOfflineResultSet.dbEyeCatcher_) >>>  0);
            DB.addRecord(data, 0, data.length);

            // Add a number indicating the version to the offline DB
            int         version = 1;
            data[0]  = (byte)((0xFF000000 & version) >>> 24);
            data[1]  = (byte)((0x00FF0000 & version) >>> 16);
            data[2]  = (byte)((0x0000FF00 & version) >>>  8);
            data[3]  = (byte)((0x000000FF & version) >>>  0);
            DB.addRecord(data, 0, data.length);

            // Write a number representing the number of columns
            // to the palm database.
            data[0]  = (byte)((0xFF000000 & numColumns_) >>> 24);
            data[1]  = (byte)((0x00FF0000 & numColumns_) >>> 16);
            data[2]  = (byte)((0x0000FF00 & numColumns_) >>>  8);
            data[3]  = (byte)((0x000000FF & numColumns_) >>>  0);
            DB.addRecord(data, 0, data.length);

            // Write the SQL type of each column to the palm database
            // All of these are in a single record.
            data = new byte[4*numColumns_];
            for (i=0; i<numColumns_; ++i)
            {
                data[i*4+0]  = (byte)((0xFF000000 & columnTypes_[i]) >>> 24);
                data[i*4+1]  = (byte)((0x00FF0000 & columnTypes_[i]) >>> 16);
                data[i*4+2]  = (byte)((0x0000FF00 & columnTypes_[i]) >>>  8);
                data[i*4+3]  = (byte)((0x000000FF & columnTypes_[i]) >>>  0);
            }

            DB.addRecord(data, 0, data.length);
            data = null;

            // Read all of the fields in from the result set, and
            // write them to the DB.
            // At this time, all fields are written as strings
            // regardless of SQL type.
            String col = null;
            while (rs.next())
            {
                for (i=1; i<=numColumns_; ++i)
                {
                    col = rs.getString(i);
                    data = col.getBytes();
                    DB.addRecord(data, 0, data.length);
                }
            }
        }
        catch (SQLException e)
        {
            throw new JdbcMeException(e.getMessage(), e.getSQLState());
        }
        finally
        {
            if (DB != null)
            {
                DB.close();
                DB = null;
            }
            System.gc();
        }

        return true;
    }


    /**
     *  Returns the current result set.
     *
     *  @return     The current result set, or null if an update count was returned or there are no more result sets.
     *
     *  @exception  JdbcMeException    If the statement is not open.
     **/
    public ResultSet getResultSet() throws JdbcMeException 
    {
        return rs_;
    }


    /**
     *  Returns the result set concurrency to be used for this statement.
     *
     *  @return The result set concurrency.
     *
     *  @exception  JdbcMeException    If the statement is not open.
     **/
    public int getResultSetConcurrency() throws JdbcMeException 
    {
        return concurrency_;
    }


    /**
     *  Returns the result set type to be used for this statement.
     *  
     *  @return The result set type.
     *
     *  @exception  JdbcMeException    If the statement is not open.
     **/
    public int getResultSetType()  throws JdbcMeException 
    {
        return type_;
    }


    /**
     *  Returns the current update count.
     *
     *  @return  The update count, or -1 if a result set was returned or there are no more result sets.
     *
     *  @exception  JdbcMeException    If the statement is not open.
     **/
    public int getUpdateCount() throws JdbcMeException 
    {
        return updateCount_;
    }


    /**
     *  Returns the connection for this statement.
     *
     *  @return The connection for this statement.
     *
     *  @exception JdbcMeException If an error occurs.
     **/
    public java.sql.Connection getConnection() throws JdbcMeException 
    {
        return connection_;
    }

	public void addBatch(String sql) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void cancel() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void clearBatch() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void clearWarnings() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int[] executeBatch() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getFetchDirection() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getFetchSize() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getMaxFieldSize() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getMaxRows() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public boolean getMoreResults() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public boolean getMoreResults(int current) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getQueryTimeout() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public int getResultSetHoldability() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setCursorName(String name) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setFetchDirection(int direction) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setMaxFieldSize(int max) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setMaxRows(int max) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		throw new java.sql.SQLException("NOT AVAILABLE IN MICROEDITION"); 
	}
}

