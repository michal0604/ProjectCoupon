package projectCoupon;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import projectCoupon.Coupons.Utile;
import projectCoupon.Exception.ConnectionException;
import projectCoupon.Exception.CouponException;



	public class ConnectionPool {

		private static ConnectionPool instance ;
		private static int maxConnections = 10;
		private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);

		private ConnectionPool() throws CouponException  {
			try {
				Class.forName(Utile.getDBUrl());
			} catch (ClassNotFoundException e) {
				throw new CouponException(e.getMessage());
			}
		}
		
		/**
		 * @return ConnectionPool
		 *  method - SINGLETON instance 
		 * @throws CouponException 
		 */
		public static ConnectionPool getInstance() throws CouponException{
			if (instance==null)instance = new ConnectionPool();
			return instance;
		}

		/**
		 * Methods get from Connection pool
		 * @return Connection
		 * @throws CouponException
		 */
		public synchronized Connection getConnection() throws ConnectionException{

			while (connections.size()==0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					throw new ConnectionException("connection failed");
					
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
		public synchronized void returnConnection(Connection con)throws ConnectionException{ //throws Exception {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ConnectionException("ERROR! Return Connection Properly Failed!");
			}
			connections.add(con);
			this.notify();
		}

		/**
		 * Close all Connections
		 * @throws ConnectionException 
		 */
		public synchronized void closeAllConnections(Connection connection) throws CouponException, ConnectionException{
			
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
					throw new ConnectionException("Connections: Close All Connection: Error!");
				}
			}
		}
}
