package projectCoupon.Exception;

import projectCoupon.Company.Company;

public class CompanyCreationException extends Exception {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Company company;
		
		public CompanyCreationException() {
			// TODO do we need this assignment: this.company = company;
		}
		
		@Override
		public String getMessage() {
			
			return "Creating company "+this.company.getCompName()+" failed !";
		}
	}
