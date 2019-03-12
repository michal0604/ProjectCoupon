package projectCoupon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import projectCoupon.Clients.Utile;
import projectCoupon.Exception.CouponException;



	public class ConnectionPool {

		private static ConnectionPool instance ;
		private static int maxConnections = 10;
		private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);

		private ConnectionPool() throws CouponException  {
			try {
				Class.forName(Utile.getDriverData());
			} catch (Exception e) {
				throw new CouponException(e.getMessage());
			}
			Connection con;
			try {
				con = DriverManager.getConnection(Database.getDBUrl());
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			try {
				con.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			while (this.connections.size() < maxConnections) {
				try {
					con = DriverManager.getConnection(Database.getDBUrl());
				} catch (SQLException e) {
					throw new CouponException("connection failed");
				}
				this.connections.offer(con);
			}

		}
			

		/**
		 * @return ConnectionPool
		 *  method - SINGLETON instance 
		 * @throws CouponException 
		 * @throws SQLException 
		 */
		public static ConnectionPool getInstance() throws CouponException, SQLException{
			if (instance==null)instance = new ConnectionPool();
			return instance;
		}

		/**
		 * Methods get from Connection pool
		 * @return Connection
		 * @throws CouponException
		 */
		public synchronized Connection getConnection() throws CouponException{

			while (connections.size()==0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					throw new CouponException("connection failed");	
				}
			}
			
			Iterator<Connection> iterator = connections.iterator();
			Connection con = iterator.next();
			iterator.remove();
			return con; 
		}			
		
		/**
		 * Methods return connection to Connection pool
		 * @throws CouponException
		 */
		public synchronized void returnConnection(Connection con)throws CouponException{ //throws Exception {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new CouponException("ERROR! Return Connection Properly Failed!");
			}
			connections.add(con);
			this.notify();
		}

		/**
		 * Close all Connections
		 * @throws ConnectionException 
		 */
		public synchronized void closeAllConnections(Connection connection) throws CouponException{
			
			while (connections.size()==0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					System.out.println("Interrupted");
				}
			}
			
			Iterator<Connection> iterator = connections.iterator();
			while(iterator.hasNext()) {
				try {
					iterator.next().close();
				} catch (SQLException e) {
					throw new CouponException("Connections: Close All Connection: Error!");
				}
			}
		}
}
