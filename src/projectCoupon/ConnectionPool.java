package projectCoupon;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import projectCoupon.Coupons.Utile;
import projectCoupon.Exception.CouponException;



	public class ConnectionPool {

		private static ConnectionPool instance ;
		private static int maxConnections = 10;
		private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);

		private ConnectionPool()  {
			try {
				Class.forName(Utile.getDBUrl());
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		
		/**
		 * @return ConnectionPool
		 *  method - SINGLETON instance 
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
		public synchronized Connection getConnection() throws CouponException{

			while (connections.size()==0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					System.out.println("Interrupted");
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
		 * @throws CouponException
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
